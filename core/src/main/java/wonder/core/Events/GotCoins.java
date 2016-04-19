package wonder.core.Events;

import wonder.core.Card.Age;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

public class GotCoins extends Event {
    private final int amount;

    public GotCoins(final int amount, final Player player, final Game game, final Age age) {
        super(player, game, age);
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }
}
