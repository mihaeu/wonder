package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Player;

import java.util.List;

public class GotCards implements Event {
    private final Player player;
    private final List<Card> cards;
    private final int gameId;

    public GotCards(final List<Card> cards, final Player player, final int gameId) {
        this.player = player;
        this.cards = cards;
        this.gameId = gameId;
    }

    public Player player() {
        return player;
    }

    public List<Card> cards() {
        return cards;
    }

    @Override
    public int gameId() {
        return gameId;
    }
}
