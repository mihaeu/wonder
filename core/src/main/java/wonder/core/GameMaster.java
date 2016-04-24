package wonder.core;

import wonder.core.Card.Age;
import wonder.core.Card.ScienceSymbol;
import wonder.core.Cards.EastTradingPost;
import wonder.core.Cards.Forum;
import wonder.core.Cards.WestTradingPost;
import wonder.core.Events.*;
import wonder.core.Exceptions.*;

import java.util.*;
import java.util.stream.Collectors;

import static wonder.core.Card.Age.*;
import static wonder.core.Card.ScienceSymbol.*;

public class GameMaster {
    private static final int INITIAL_CARDS_PER_PLAYER = 7;
    private static final int STARTING_COINS = 3;
    private static final int ROUNDS_PER_AGE = 6;
    private static final int AGES_PER_GAME = 3;
    private static final int COINS_FOR_DISCARDING_A_CARD = 3;

    private EventLog log;

    public GameMaster(EventLog log) {
        this.log = log;
    }

    public Game initiateGame(List<Card> cards, Map<Integer, Player> players, Integer gameId) {
        final Game game = new Game(gameId, players, cards);
        log.add(new GameCreated(cards, Player.EVERY, game, One));

        handOutAgeCards(game, One);

        players.forEach((integer, player) -> log.add(new GotCoins(STARTING_COINS, player, game, One)));

        return game;
    }

    Age activeAge(Game game) {
        final List<Age> ages = log.byGame(game)
                .filter(event -> event instanceof AgeCompleted)
                .map(event -> event.age())
                .collect(Collectors.toList());
        if (ages.isEmpty()) return One;
        if (ages.contains(Three)) return Three;
        if (ages.contains(Two)) return Three;

        return Two;
    }

    public List<Card> cardsAvailable(Player player, Game game) {
        List<Card> availableCards = new ArrayList<>();
        log.byGame(game).forEach(event -> {
            if (event instanceof GotCards && event.player().equals(player)) {
                availableCards.clear();
                availableCards.addAll(((GotCards) event).cards());
            }
            if (event instanceof CardPlayed && event.player().equals(player)) {
                availableCards.remove(((CardPlayed) event).card());
            }
        });
        return availableCards;
    }

    public Map<Player, List<Card>> playedCards(Game game) {
        Map<Player, List<Card>> playedCards = new HashMap<>();
        Map<Player, List<Card>> cardsPerRound = new HashMap<>();
        for (Event event : log.log()) {
            if (event.game() != game) continue;
            if (event instanceof CardPlayed) {
                final Player player = event.player();
                cardsPerRound.putIfAbsent(player, new ArrayList<>());
                cardsPerRound.get(player).add(((CardPlayed) event).card());
            } else if (event instanceof RoundCompleted) {
                cardsPerRound.forEach((player, cards) -> {
                    playedCards.putIfAbsent(player, new ArrayList<>());
                    playedCards.get(player).addAll(cards);
                });
                cardsPerRound.clear();
            }
        }
        return playedCards;
    }

    public void cardPlayed(int cardPlayedIndex, Player player, Game game)
            throws NotAllowedToPlayException,
            CardNotAffordableException,
            CardNotAvailableException,
            CardAlreadyPlayedException {
        cardPlayed(cardsAvailable(player, game).get(cardPlayedIndex), player, game);
    }

    void cardPlayed(Card card, Player player, Game game)
            throws NotAllowedToPlayException,
                CardNotAvailableException,
                CardNotAffordableException,
                CardAlreadyPlayedException {
        if (!isPlayerAllowedToPlay(player, game)) {
            throw new NotAllowedToPlayException();
        }
        if (!cardsAvailable(player, game).contains(card)) {
            throw new CardNotAvailableException();
        }
        final boolean free = isFree(card, player, game);
        final boolean affordable = isAffordable(card, player, game);
        if (!free
                && !affordable) {
            throw new CardNotAffordableException();
        }
        if (!cardNotPlayedBefore(card, player, game)) {
            throw new CardAlreadyPlayedException();
        }

        log.add(new CardPlayed(card, player, game, activeAge(game)));
        if (card.coinCost() > 0) {
            log.add(new PayedCoins(card.coinCost(), player, game, activeAge(game)));
        }
        log.add(card.process(player, game, activeAge(game)));

        if (isRoundCompleted(game)) {
            roundCompleted(game);
        }
    }

    void cardDiscarded(Card card, Player player, Game game)
            throws CardNotAvailableException,
                NotAllowedToPlayException {
        if (!isPlayerAllowedToPlay(player, game)) {
            throw new NotAllowedToPlayException();
        }
        if (!cardsAvailable(player, game).contains(card)) {
            throw new CardNotAvailableException();
        }

        final Age age = activeAge(game);
        log.add(new CardDiscarded(card, player, game, age));
        log.add(new GotCoins(COINS_FOR_DISCARDING_A_CARD, player, game, age));

        if (isRoundCompleted(game)) {
            roundCompleted(game);
        }
    }

    boolean cardNotPlayedBefore(Card card, Player player, Game game) {
        final long count = log.byCardByPlayer(player, game)
                .filter(cardPlayed -> cardPlayed.card() == card)
                .count();
        return count == 0;
    }

    private void roundCompleted(Game game) {
        final Age activeAge = activeAge(game);
        log.add(new RoundCompleted(Player.EVERY, game, activeAge));

        // at the end of the game we don't need to hand cards to other players
        // last card is discarded
        if (isAgeCompleted(game)) {
            ageCompleted(game);
        } else {
            handCardsToNextPlayers(game, activeAge);
        }
    }

    private void handCardsToNextPlayers(Game game, Age activeAge) {
        Integer[] keys = game.players().keySet().toArray(new Integer[game.players().size()]);
        List<Card> currentCards = cardsAvailable(game.players().get(keys[0]), game);
        currentCards.remove(lastPlayed(game.players().get(keys[0]), game));

        Player nextPlayer;
        List<Card> nextCards;

        // 2nd age counter-clockwise
        if (activeAge == Two) {
            for (int i = game.players().size() - 1; i >= 0; i -= 1) {
                nextPlayer = game.players().get(keys[i]);
                nextCards = cardsAvailable(nextPlayer, game);
                nextCards.remove(lastPlayed(nextPlayer, game));

                log.add(new GotCards(currentCards, nextPlayer, game, activeAge));

                currentCards = nextCards;
            }
        // 1st and 3rd age clockwise
        } else {
            for (int i = 0; i < keys.length; i += 1) {
                int nextIndex = i + 1 < keys.length ? i + 1 : 0;
                nextPlayer = game.players().get(keys[nextIndex]);
                nextCards = cardsAvailable(nextPlayer, game);
                nextCards.remove(lastPlayed(nextPlayer, game));

                log.add(new GotCards(currentCards, nextPlayer, game, activeAge));

                currentCards = nextCards;
            }
        }
    }

    private Card lastPlayed(Player player, Game game) {
        for (int i = log.log().size() - 1; i >= 0; i -= 1) {
            if (log.log().get(i) instanceof CardPlayed
                    && log.log().get(i).game() == game
                    && log.log().get(i).player() == player) {
                return ((CardPlayed) log.log().get(i)).card();
            }
        }
        return null;
    }

    boolean isRoundCompleted(Game game) {
        return 0 == log.byEvent(CardPlayed.class, game).count() % game.players().size();
    }

    void ageCompleted(Game game) {
        game.players().forEach((integer, player) -> handleMilitary(player, game));

        final Age activeAge = activeAge(game);
        log.add(new AgeCompleted(Player.EVERY, game, activeAge));

        handOutAgeCards(game, activeAge);

        if (isGameCompleted(game)) {
            completeGame(game);
        }
    }

    private void handleMilitary(Player player, Game game) {
        List<Integer> keys = game.players().keySet().stream().collect(Collectors.toList());
        Integer indexOfLeftPlayer = 0;
        Integer indexOfRightPlayer = 0;
        for (int i = 0; i < keys.size(); i += 1) {
            if (game.players().get(keys.get(i)) == player) {
                indexOfLeftPlayer = i - 1 >= 0 ? i - 1 : keys.size() - 1;
                indexOfRightPlayer = i + 1 <= keys.size() - 1 ? i + 1 : 0;
            }
        }
        long currentPlayerMilitaryCount = countMilitary(player, game);
        long leftPlayerMilitaryCount = countMilitary(game.players().get(keys.get(indexOfLeftPlayer)), game);
        long rightPlayerMilitaryCount = countMilitary(game.players().get(keys.get(indexOfRightPlayer)), game);
        if (leftPlayerMilitaryCount > currentPlayerMilitaryCount) {
            log.add(new GotMilitaryLoss(player, game, activeAge(game)));
        } else if (leftPlayerMilitaryCount < currentPlayerMilitaryCount) {
            log.add(new GotMilitaryWin(player, game, activeAge(game)));
        }

        if (rightPlayerMilitaryCount > currentPlayerMilitaryCount) {
            log.add(new GotMilitaryLoss(player, game, activeAge(game)));
        } else if (rightPlayerMilitaryCount < currentPlayerMilitaryCount) {
            log.add(new GotMilitaryWin(player, game, activeAge(game)));
        }
    }

    private int countMilitary(Player player, Game game) {
        return log.byCardByPlayer(player, game)
                .filter(cardPlayed -> cardPlayed.card().type() == Card.Type.Red)
                .mapToInt(cardPlayed -> {
                    if (cardPlayed.card().age() == One) return 1;
                    if (cardPlayed.card().age() == Two) return 2;
                    return 3;
                })
                .sum();
    }

    private int scoreFromMilitary(Player player, Game game) {
        return log.byEvent(GotMilitaryWin.class, player, game)
                .mapToInt(value -> {
                    if (value.age() == One) return 1;
                    else if (value.age() == Two) return 3;
                    else return 5;
                }).sum() - (int) log.byEvent(GotMilitaryLoss.class, player, game)
                .count();
    }

    private void handOutAgeCards(Game game, Age activeAge) {
        final GameCreated gameCreated = (GameCreated) log.byGame(game).findFirst().get();
        List<Card> ageCards = gameCreated.cards().stream()
                .filter(card -> card.age() == activeAge)
                .collect(Collectors.toList());
        int offset = 0;
        for (Integer key : game.players().keySet()) {
            final List<Card> handCards = ageCards.subList(offset, offset + INITIAL_CARDS_PER_PLAYER);
            log.add(new GotCards(handCards, game.players().get(key), game, activeAge));
            offset += INITIAL_CARDS_PER_PLAYER;
        }
    }

    public Map<Player, Integer> finalScore(Game game) throws Exception {
        if (!isGameCompleted(game)) {
            throw new GameNotCompletedException();
        }

        Map<Player, Integer> finalScore = new HashMap<>();
        game.players().forEach((playerId, player) -> {
            int score = scoreFromVictoryPoints(game, player);
            score += scoreFromScienceCards(player, game);
            score += scoreFromMilitary(player, game);
            finalScore.put(player, score);
        });
        return finalScore;
    }

    private int scoreFromVictoryPoints(Game game, Player player) {
        return log.byEvent(GotVictoryPoints.class, player, game)
                .mapToInt(event -> ((GotVictoryPoints) event).amount())
                .sum();
    }

    private int scoreFromScienceCards(Player player, Game game) {
        List<ScienceSymbol> symbols = new ArrayList<>();
        log.byEvent(GotScienceSymbol.class, player, game)
                .map(event -> (GotScienceSymbol) event)
                .forEach(gotScienceSymbol -> symbols.add(gotScienceSymbol.symbol()));
        List<Map<ScienceSymbol, Integer>> combinations = generateSymbolCombinations(symbols);
        OptionalInt bestCombination = combinations.stream()
                .mapToInt(this::calculateScienceCardCombination)
                .max();
        return bestCombination.isPresent() ? bestCombination.getAsInt() : 0;
    }

    private List<Map<ScienceSymbol, Integer>> generateSymbolCombinations(List<ScienceSymbol> symbols) {
        Map<ScienceSymbol, Integer> normalSymbols = new HashMap<>();
        normalSymbols.put(Cogs, 0);
        normalSymbols.put(Compass, 0);
        normalSymbols.put(StoneTablet, 0);
        symbols.stream()
                .filter(scienceSymbol -> scienceSymbol != OptionalSymbol)
                .forEach(scienceSymbol -> {
                    normalSymbols.put(scienceSymbol, normalSymbols.get(scienceSymbol) + 1);
                });

        List<Map<ScienceSymbol, Integer>> combinations = new ArrayList<>();
        combinations.add(normalSymbols);
        symbols.stream()
                .filter(scienceSymbol -> scienceSymbol == OptionalSymbol)
                .forEach(scienceSymbol -> {
                    List<Map<ScienceSymbol, Integer>> tempCombinations = new ArrayList<>();
                    combinations.forEach(scienceSymbolIntegerMap -> {
                        Map<ScienceSymbol, Integer> tempCogs = new HashMap<>(scienceSymbolIntegerMap);
                        tempCogs.put(Cogs, tempCogs.get(Cogs) + 1);
                        tempCombinations.add(tempCogs);

                        Map<ScienceSymbol, Integer> tempCompass = new HashMap<>(scienceSymbolIntegerMap);
                        tempCompass.put(Compass, tempCompass.get(Compass) + 1);
                        tempCombinations.add(tempCompass);

                        Map<ScienceSymbol, Integer> tempStoneTablet = new HashMap<>(scienceSymbolIntegerMap);
                        tempStoneTablet.put(StoneTablet, tempStoneTablet.get(StoneTablet) + 1);
                        tempCombinations.add(tempStoneTablet);
                    });
                    combinations.addAll(tempCombinations);
                });
        return combinations;
    }

    private int calculateScienceCardCombination(Map<ScienceSymbol, Integer> symbols) {
        return Math.min(Math.min(symbols.get(Compass), symbols.get(StoneTablet)), symbols.get(Cogs)) * 7
                + symbols.get(Compass) * symbols.get(Compass)
                + symbols.get(StoneTablet) * symbols.get(StoneTablet)
                + symbols.get(Cogs) * symbols.get(Cogs);
    }

    public boolean isAgeCompleted(Game game) {
        final long count = log.byEvent(RoundCompleted.class, game).count();
        return count != 0 && 0 == count % ROUNDS_PER_AGE;
    }

    public void completeGame(Game game) {
        log.add(new GameCompleted(Player.EVERY, game, Three));
    }

    boolean isGameCompleted(Game game) {
        return log.byEvent(GameCompleted.class, game).count() == 1 ||
                AGES_PER_GAME == log.byEvent(AgeCompleted.class, Player.EVERY, game).count();
    }

    boolean isPlayerAllowedToPlay(Player player, Game game) {
        return log.byEvent(RoundCompleted.class, Player.EVERY, game).count()
                >= log.byCardByPlayer(player, game).count();
    }

    boolean isAffordable(Card card, Player player, Game game) {
        return card.coinCost() == 0
                && card.resourceCost().isEmpty()
                || card.coinCost() <= coinsAvailable(player, game)
                && resourcesAvailable(player, game).contains(card.resourceCost());
    }

    public int coinsAvailable(Player player, Game game) {
        return log.byEvent(GotCoins.class, player, game)
                .mapToInt(event -> ((GotCoins)event).amount())
                .sum() - log.byEvent(PayedCoins.class, player, game)
                .mapToInt(event -> ((GotCoins)event).amount())
                .sum();
    }

    private ResourcePool resourcesAvailable(Player player, Game game) {
        ResourcePool pool = new ResourcePool();
        log.byEvent(GotResources.class, player, game)
                .map(GotResources.class::cast)
                .forEach(gotResources -> pool.add(gotResources.resources()));
        return pool;
    }

    @SuppressWarnings("unchecked")
    boolean isFree(Card card, Player player, Game game) {
        if (card instanceof Forum) {
            return log.byCardByPlayer(player, game).anyMatch(cardPlayed -> {
                return cardPlayed.card() instanceof EastTradingPost
                        || cardPlayed.card() instanceof WestTradingPost;
            });
        }

        return log.byCardByPlayer(player, game)
                .anyMatch(cardPlayed -> card.freeConstruction() == cardPlayed.card().getClass());
    }
}
