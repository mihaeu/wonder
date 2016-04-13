package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Player;

import java.util.List;
import java.util.Map;

public class GameCreated implements Event {
    private final int id;
    private final Map<Integer, Player> players;
    private final List<Card> cards;

    public GameCreated(final int id, final Map<Integer, Player> players, final List<Card> cards) {
        this.id = id;
        this.players = players;
        this.cards = cards;
    }

    public Map<Integer, Player> players() {
        return players;
    }

    public List<Card> cards() {
        return cards;
    }

    @Override
    public int gameId() {
        return id;
    }
}
