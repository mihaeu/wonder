package wonder.core;

import wonder.core.Events.GameCreated;
import wonder.core.Events.GotCards;
import wonder.core.Events.GotCoins;

import java.util.*;
import java.util.stream.Collectors;

public class GameMaster {
    public static final int INITIAL_CARDS_PER_PLAYER = 7;
    public static final int STARTING_COINS = 3;
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
            log.add(new GotCards(players.get(key), handCards));
            games.get(id).players().get(key).cardsAvailable().addAll(handCards);

            log.add(new GotCoins(players.get(key), STARTING_COINS));
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

    public void startRounds(List<Player> players) {
        players.forEach(player -> {
            final Card selectedCard = player.selectCard(null);
            log.add(new CardSelected(player, selectedCard));
        });
    }
}
