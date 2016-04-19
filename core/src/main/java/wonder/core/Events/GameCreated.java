package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

import java.util.List;
import java.util.Map;

public class GameCreated implements Event {
    private final int id;
    private final Map<Integer, Player> players;
    private final List<Card> cards;
    private Game game;

    public GameCreated(final int id, final Map<Integer, Player> players, final List<Card> cards) {
        this.id = id;
        this.players = players;
        this.cards = cards;
    }

    public GameCreated(final Game game, final Map<Integer, Player> players, final List<Card> cards) {
        this(game.id(), players, cards);
        this.game = game;
    }

    public Map<Integer, Player> players() {
        return players;
    }

    public List<Card> cards() {
        return cards;
    }

    public Game game() {
        return game;
    }

    @Override
    public int gameId() {
        return id;
    }
}
