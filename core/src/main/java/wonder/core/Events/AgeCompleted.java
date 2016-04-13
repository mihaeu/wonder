package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Game;

public class AgeCompleted implements Event {
    private final Game game;
    private final Card.Age age;

    public AgeCompleted(final Game game, final Card.Age age) {
        this.game = game;
        this.age = age;
    }

    @Override
    public int gameId() {
        return game.id();
    }
}
