package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Player;

import java.util.List;

public class GotCards implements Event {
    private final Player player;
    private final List<Card> cards;

    public GotCards(final Player player, final List<Card> cards) {
        this.player = player;
        this.cards = cards;
    }

    public Player player() {
        return player;
    }

    public List<Card> cards() {
        return cards;
    }
}
