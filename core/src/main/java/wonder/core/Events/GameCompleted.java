package wonder.core.Events;

import wonder.core.Event;
import wonder.core.Game;

public class GameCompleted implements Event {
    private final Game game;

    public GameCompleted(final Game game) {
        this.game = game;
    }

    @Override
    public int gameId() {
        return game.id();
    }
}
