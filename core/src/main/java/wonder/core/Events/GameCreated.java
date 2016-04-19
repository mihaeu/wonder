package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

import java.util.List;

import static wonder.core.Card.Age;

public class GameCreated extends Event {
    private final List<Card> cards;

    public GameCreated(final List<Card> cards, final Player player, final Game game, final Age age) {
        super(player, game, age);
        this.cards = cards;
    }

    public List<Card> cards() {
        return cards;
    }
}
