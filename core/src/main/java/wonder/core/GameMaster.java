package wonder.core;

import wonder.core.Events.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameMaster {
    public static final int INITIAL_CARDS_PER_PLAYER = 7;
    public static final int STARTING_COINS = 3;
    public static final int ROUNDS_PER_AGE = 6;
    public static final int AGES_PER_GAME = 3;
    private GameSetup setup;
    private List<Event> log;
    private Map<Integer, Game> games;

    public GameMaster(GameSetup setup) {
        this.log = new ArrayList<>();
        this.games = new HashMap<>();
        this.setup = setup;
    }

    public GameMaster(GameSetup setup, List<Event> log) {
        this(setup);
        this.log = log;
    }

    public List<Event> log() {
        return log;
    }

    public Map<Integer, Game> games() {
        return games;
    }

    public void initiateGame(Map<Integer, Player> players, Integer id) {
        List<Card> cards = setup.setupGame(players.size());

        log.add(new GameCreated(id, players, cards));
        games.put(id, new Game(id, players, cards));

        List<Card> ageOneCards = cards.stream()
                .filter(card -> card.age() == Card.Age.One)
                .collect(Collectors.toList());
        int offset = 0;
        for (Integer key : players.keySet()) {
            final List<Card> handCards = ageOneCards.subList(offset, offset + INITIAL_CARDS_PER_PLAYER);
            log.add(new GotCards(handCards, players.get(key), id));
            games.get(id).players().get(key).cardsAvailable().addAll(handCards);

            log.add(new GotCoins(players.get(key), STARTING_COINS, id));
            games.get(id).players().get(key).addCoins(STARTING_COINS);
            offset += INITIAL_CARDS_PER_PLAYER;
        }
    }

    public void initiateGame(Map<Integer, Player> players) {
        int lastId = 0;
        final Optional<Integer> max = games.keySet().stream().max(Integer::compare);
        if (!games.isEmpty() && max.isPresent()) {
            lastId = max.get();
        }
        initiateGame(players, lastId + 1);
    }

    public void cardPlayed(Card card, Player player, Game game) {
        if (!isPlayerAllowedToPlay(player, game)) {
            throw new IllegalArgumentException("Player is not allowed to play");
        }
        log.add(new CardPlayed(card, player, game));
        if (isRoundCompleted(game)) {
            roundCompleted(game);
        }
    }

    private void roundCompleted(Game game) {
        log.add(new RoundCompleted(game));
        if (isAgeCompleted(game)) {
            ageCompleted(game);
        }
    }

    public boolean isRoundCompleted(Game game) {
        return 0 == currentGameStream(game)
                .filter(event -> event instanceof CardPlayed)
                .count() % game.players().size();
    }

    private void ageCompleted(Game game) {
        log.add(new AgeCompleted(game, Card.Age.One));

        if (isGameCompleted(game)) {
            completeGame(game);
        }
    }

    public boolean isAgeCompleted(Game game) {
        final long count = currentGameStream(game)
                .filter(event -> event instanceof RoundCompleted)
                .count();
        return count != 0 && 0 == count % ROUNDS_PER_AGE;
    }

    public void completeGame(Game game) {
        log.add(new GameCompleted(game));
    }

    public boolean isGameCompleted(Game game) {
        return AGES_PER_GAME == currentGameStream(game)
                .filter(event -> event instanceof AgeCompleted)
                .count();
    }

    public boolean isPlayerAllowedToPlay(Player player, Game game) {
        final long numberOfRoundsCompleted = currentGameStream(game)
                .filter(event -> event instanceof RoundCompleted)
                .count();
        final long cardsPlayedByPlayer = currentGameStream(game)
                .filter(event -> event instanceof CardPlayed)
                .map(event -> (CardPlayed) event)
                .filter(event -> event.player().id() == player.id())
                .count();
        return numberOfRoundsCompleted >= cardsPlayedByPlayer;
    }

    private Stream<Event> currentGameStream(final Game game) {
        return log.stream().filter(event -> game.id() == event.gameId());
    }
}
