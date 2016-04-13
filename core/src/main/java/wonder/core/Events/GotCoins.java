package wonder.core.Events;

import wonder.core.Event;
import wonder.core.Player;

public class GotCoins implements Event {
    private final int amount;
    private final Player player;
    private final int gameId;

    public GotCoins(final Player player, final int amount, final int gameId) {
        this.player = player;
        this.amount = amount;
        this.gameId = gameId;
    }

    public int amount() {
        return amount;
    }

    public Player player() {
        return player;
    }

    @Override
    public int gameId() {
        return gameId;
    }
}
