package wonder.core;

import wonder.core.Events.*;
import wonder.core.Exceptions.CardNotAffordableException;
import wonder.core.Exceptions.CardNotAvailableException;
import wonder.core.Exceptions.NotAllowedToPlayException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static wonder.core.Card.Age.One;
import static wonder.core.Card.Age.Three;
import static wonder.core.Card.Age.Two;

public class GameMaster {
    private static final int INITIAL_CARDS_PER_PLAYER = 7;
    private static final int STARTING_COINS = 3;
    private static final int ROUNDS_PER_AGE = 6;
    private static final int AGES_PER_GAME = 3;

    private EventLog log;

    public GameMaster(EventLog log) {
        this.log = log;
    }

    public void initiateGame(List<Card> cards, Map<Integer, Player> players, Integer gameId) {
        final Game game = new Game(gameId, players, cards);
        log.log().add(new GameCreated(cards, Player.EVERY, game, One));

        List<Card> ageOneCards = cards.stream()
                .filter(card -> card.age() == One)
                .collect(Collectors.toList());
        int offset = 0;
        for (Integer key : players.keySet()) {
            final List<Card> handCards = ageOneCards.subList(offset, offset + INITIAL_CARDS_PER_PLAYER);
            log.log().add(new GotCards(handCards, players.get(key), game, One));
            game.players().get(key).cardsAvailable().addAll(handCards);

            log.log().add(new GotCoins(STARTING_COINS, players.get(key), game, One));
            game.players().get(key).addCoins(STARTING_COINS);
            offset += INITIAL_CARDS_PER_PLAYER;
        }
    }

    Card.Age activeAge(Game game) {
        final List<Card.Age> ages = log.byGame(game)
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
            throws NotAllowedToPlayException, CardNotAffordableException, CardNotAvailableException {
        cardPlayed(cardsAvailable(player, game).get(cardPlayedIndex), player, game);
    }

    public void cardPlayed(Card card, Player player, Game game)
            throws NotAllowedToPlayException, CardNotAvailableException, CardNotAffordableException {
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

        log.log().add(new CardPlayed(card, player, game, activeAge(game)));
        log.log().add(card.process(player, game, activeAge(game)));
        if (isRoundCompleted(game)) {
            roundCompleted(game);
        }
    }

    private void roundCompleted(Game game) {
        log.log().add(new RoundCompleted(Player.EVERY, game, activeAge(game)));

        // at the end of the game we don't need to hand cards to other players
        // last card is discarded
        if (isAgeCompleted(game)) {
            ageCompleted(game);
        // second age counter clockwise
        } else if (activeAge(game) == Two) {
            List<Card> current = game.players().get(0).cardsAvailable();
            current.remove(lastPlayed(game.players().get(0), game));
            List<Card> next;
            for (int i = game.players().size() - 1; i >= 0; i -= 1) {
                next = game.players().get(i).cardsAvailable();
                next.remove(lastPlayed(game.players().get(i), game));

                // assign current to next
                log.log().add(new GotCards(current, game.players().get(i), game, activeAge(game)));

                current = next;
            }
        // 1st and 3rd age clockwise
        } else {
            List<Card> current = game.players().get(0).cardsAvailable();
            current.remove(lastPlayed(game.players().get(0), game));
            List<Card> next;
            for (int i = 0, count = game.players().size(); i < count; i += 1) {
                final int nextIndex = i + 1 < count ? i + 1 : 0;
                next = game.players().get(nextIndex).cardsAvailable();
                next.remove(lastPlayed(game.players().get(nextIndex), game));

                // assign current to next
                log.log().add(new GotCards(current, game.players().get(nextIndex), game, activeAge(game)));

                current = next;
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
        return 0 == log.byGame(game)
                .filter(event -> event instanceof CardPlayed)
                .count() % game.players().size();
    }

    private void ageCompleted(Game game) {
        final Card.Age activeAge = activeAge(game);
        final Card.Age nextAge = activeAge == One ? Two : Three;
        log.log().add(new AgeCompleted(Player.EVERY, game, activeAge));

        List<Card> ageCards = ((GameCreated) log.log().get(0)).cards().stream()
                .filter(card -> card.age() == nextAge)
                .collect(Collectors.toList());
        int offset = 0;
        for (Integer key : game.players().keySet()) {
            final List<Card> handCards = ageCards.subList(offset, offset + INITIAL_CARDS_PER_PLAYER);
            log.log().add(new GotCards(handCards, game.players().get(key), game, activeAge));
            offset += INITIAL_CARDS_PER_PLAYER;
        }

        if (isGameCompleted(game)) {
            completeGame(game);
        }
    }

    public boolean isAgeCompleted(Game game) {
        final long count = log.byGame(game)
                .filter(event -> event instanceof RoundCompleted)
                .count();
        return count != 0 && 0 == count % ROUNDS_PER_AGE;
    }

    public void completeGame(Game game) {
        log.log().add(new GameCompleted(Player.EVERY, game, Three));
    }

    public boolean isGameCompleted(Game game) {
        return AGES_PER_GAME == log.byGame(game)
                .filter(event -> event instanceof AgeCompleted)
                .count();
    }

    public boolean isPlayerAllowedToPlay(Player player, Game game) {
        final long numberOfRoundsCompleted = log.byGame(game)
                .filter(event -> event instanceof RoundCompleted)
                .count();
        final long cardsPlayedByPlayer = log.byCardByPlayer(player, game).count();
        return numberOfRoundsCompleted >= cardsPlayedByPlayer;
    }

    public boolean isAffordable(Card card, Player player, Game game) {
        if (card.coinCost() == 0 && card.resourceCost().isEmpty()) return true;
        return card.coinCost() <= coinsAvailable(player, game)
                && resourcesAvailable(player, game).contains(card.resourceCost());
    }

    public int coinsAvailable(Player player, Game game) {
        return log.byGame(game)
                .filter(event -> event instanceof GotCoins)
                .filter(event -> ((GotCoins) event).player() == player)
                .mapToInt(event -> ((GotCoins)event).amount())
                .sum();
    }

    private ResourcePool resourcesAvailable(Player player, Game game) {
        ResourcePool pool = new ResourcePool();
        log.byGame(game)
                .filter(event -> event instanceof GotResources)
                .filter(event -> ((GotResources) event).player() == player)
                .map(event -> (GotResources) event)
                .forEach(gotResources -> pool.add(gotResources.resources()));
        return pool;
    }

    public boolean isFree(Card card, Player player, Game game) {
        // we have to check like this because of the two trading posts
        return log.byCardByPlayer(player, game)
                .anyMatch(cardPlayed -> card.freeConstruction().isAssignableFrom(cardPlayed.card().getClass()));
    }
}
