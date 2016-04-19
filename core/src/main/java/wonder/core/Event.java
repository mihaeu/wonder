package wonder.core;

import wonder.core.Card.Age;
import wonder.core.Events.NullEvent;

public abstract class Event {
    private final Game game;
    private final Age age;
    private final Player player;

    public static final Event NULL = new NullEvent();

    public Event(final Player player, final Game game, final Age age) {
        this.player = player;
        this.game = game;
        this.age = age;
    }

    public Game game() {
        return game;
    }

    public Player player() {
        return player;
    }

    public Age age() {
        return age;
    }
}
