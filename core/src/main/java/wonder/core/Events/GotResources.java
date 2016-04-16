package wonder.core.Events;

import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;
import wonder.core.Resources;

public class GotResources implements Event {
    private final Game game;
    private final Player player;
    private final Resources resources;

    public GotResources(Resources resources, Player player, Game game) {
        this.resources = resources;
        this.player = player;
        this.game = game;
    }

    public Player player() {
        return player;
    }

    public Resources resources() {
        return resources;
    }

    @Override
    public int gameId() {
        return game.id();
    }
}
