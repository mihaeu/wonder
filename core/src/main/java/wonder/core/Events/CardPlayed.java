package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Card.Age;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

public class CardPlayed extends Event {
    private final Card card;

    public CardPlayed(final Card card, final Player player, final Game game, final Age age) {
        super(player, game, age);
        this.card = card;
    }

    public Card card() {
        return card;
    }
}
