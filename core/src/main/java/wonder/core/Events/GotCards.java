package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Card.Age;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

import java.util.List;

public class GotCards extends Event {
    private final List<Card> cards;

    public GotCards(final List<Card> cards, final Player player, final Game game, final Age age) {
        super(player, game, age);
        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }
}
