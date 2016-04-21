package wonder.core;

import wonder.core.Card.Age;
import wonder.core.Events.*;
import wonder.core.Exceptions.CardAlreadyPlayedException;
import wonder.core.Exceptions.CardNotAffordableException;
import wonder.core.Exceptions.CardNotAvailableException;
import wonder.core.Exceptions.NotAllowedToPlayException;

import java.util.*;
import java.util.stream.Collectors;

import static wonder.core.Card.Age.*;

public class GameMaster {
    private static final int INITIAL_CARDS_PER_PLAYER = 7;
    private static final int STARTING_COINS = 3;
    private static final int ROUNDS_PER_AGE = 6;
    private static final int AGES_PER_GAME = 3;
    public static final int COINS_FOR_DISCARDING_A_CARD = 3;

    private EventLog log;

    public GameMaster(EventLog log) {
        this.log = log;
    }

    public Game initiateGame(List<Card> cards, Map<Integer, Player> players, Integer gameId) {
        final Game game = new Game(gameId, players, cards);
        log.add(new GameCreated(cards, Player.EVERY, game, One));

        List<Card> ageOneCards = cards.stream()
                .filter(card -> card.age() == One)
                .collect(Collectors.toList());
        int offset = 0;
        for (Integer key : players.keySet()) {
            final List<Card> handCards = ageOneCards.subList(offset, offset + INITIAL_CARDS_PER_PLAYER);
            log.add(new GotCards(handCards, players.get(key), game, One));
            game.players().get(key).cardsAvailable().addAll(handCards);

            log.add(new GotCoins(STARTING_COINS, players.get(key), game, One));
            game.players().get(key).addCoins(STARTING_COINS);
            offset += INITIAL_CARDS_PER_PLAYER;
        }
        return game;
    }

    Age activeAge(Game game) {
        final List<Age> ages = log.byGame(game)
                .filter(event -> event instanceof AgeCompleted)
                .map(event -> ((AgeCompleted) event).age())
                .collect(Collectors.toList());
        if (ages.isEmpty()) return One;
        if (ages.contains(Three)) return Three;
        if (ages.contains(Two)) return Three;

        return Two;
    }

    public List<Card> cardsAvailable(Player player, Game game) {
        List<Card> availableCards = new ArrayList<>();
        log.byGame(game).forEach(event -> {
            if (event instanceof GotCards && ((GotCards) event).player().equals(player)) {
                availableCards.clear();
                availableCards.addAll(((GotCards) event).cards());
            }
            if (event instanceof CardPlayed && ((CardPlayed) event).player().equals(player)) {
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
                final Player player = ((CardPlayed) event).player();
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

    public void cardPlayed(Card card, Player player, Game game)
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
        if (!isFree(card, player, game)
                && !isAffordable(card, player, game)) {
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

    public void cardDiscarded(Card card, Player player, Game game)
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

    public boolean cardNotPlayedBefore(Card card, Player player, Game game) {
//        return true;
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
                nextCards = nextPlayer.cardsAvailable();
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

    public Card lastPlayed(Player player, Game game) {
        for (int i = log.log().size() - 1; i >= 0; i -= 1) {
            if (log.log().get(i) instanceof CardPlayed
                    && log.log().get(i).game() == game
                    && ((CardPlayed) log.log().get(i)).player() == player) {
                return ((CardPlayed) log.log().get(i)).card();
            }
        }
        return null;
    }

    public boolean isRoundCompleted(Game game) {
        return 0 == log.byEvent(CardPlayed.class, game).count() % game.players().size();
    }

    private void ageCompleted(Game game) {
        final Age activeAge = activeAge(game);
        final Age nextAge = activeAge == One ? Two : Three;
        log.add(new AgeCompleted(Player.EVERY, game, activeAge));

        final GameCreated gameCreated = (GameCreated) log.byGame(game).findFirst().get();
        List<Card> ageCards = gameCreated.cards().stream()
                .filter(card -> card.age() == nextAge)
                .collect(Collectors.toList());
        int offset = 0;
        for (Integer key : game.players().keySet()) {
            final List<Card> handCards = ageCards.subList(offset, offset + INITIAL_CARDS_PER_PLAYER);
            log.add(new GotCards(handCards, game.players().get(key), game, activeAge));
            offset += INITIAL_CARDS_PER_PLAYER;
        }

        if (isGameCompleted(game)) {
            completeGame(game);
        }
    }

    public Map<Player, Integer> finalScore(Game game) throws Exception {
        if (!isGameCompleted(game)) {
            throw new Exception("Game is not yet completed");
        }

        Map<Player, Integer> finalScore = new HashMap<>();
        game.players().forEach((playerId, player) -> {
            int score = log.byEvent(GotVictoryPoints.class, player, game)
                    .mapToInt(event -> ((GotVictoryPoints) event).amount())
                    .sum();
            score += scoreFromScienceCards(player, game);
            finalScore.put(player, score);
        });
        return finalScore;
    }

    private int scoreFromScienceCards(Player player, Game game) {
        return (int) log.byEvent(GotScienceSymbol.class, player, game).count();
    }

    public boolean isAgeCompleted(Game game) {
        final long count = log.byEvent(RoundCompleted.class, game).count();
        return count != 0 && 0 == count % ROUNDS_PER_AGE;
    }

    public void completeGame(Game game) {
        log.add(new GameCompleted(Player.EVERY, game, Three));
    }

    public boolean isGameCompleted(Game game) {
        return log.byEvent(GameCompleted.class, game).count() == 1 ||
                AGES_PER_GAME == log.byEvent(AgeCompleted.class, Player.EVERY, game).count();
    }

    public boolean isPlayerAllowedToPlay(Player player, Game game) {
        return log.byEvent(RoundCompleted.class, Player.EVERY, game).count()
                >= log.byCardByPlayer(player, game).count();
    }

    public boolean isAffordable(Card card, Player player, Game game) {
        if (card.coinCost() == 0 && card.resourceCost().isEmpty()) return true;
        return card.coinCost() <= coinsAvailable(player, game)
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
    public boolean isFree(Card card, Player player, Game game) {
        // we have to check like this because of the two trading posts
        return log.byCardByPlayer(player, game)
                .anyMatch(cardPlayed -> card.freeConstruction().isAssignableFrom(cardPlayed.card().getClass()));
    }
}
