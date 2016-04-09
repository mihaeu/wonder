package wonder.core.Events;

import wonder.core.Event;
import wonder.core.Player;

public class GotCoins implements Event {
    private final int amount;
    private final Player player;

    public GotCoins(final Player player, final int amount) {
        this.player = player;
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }

    public Player player() {
        return player;
    }
}
