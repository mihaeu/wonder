package wonder.core.Events;

import wonder.core.*;

public class TradedResource extends Event {
    private Player from;
    private Resources.Type type;

    public TradedResource(Player from, Player to, Resources.Type type, Game game, Card.Age age) {
        super(to, game, age);
        this.from = from;
        this.type = type;
    }

    public Player from() {
        return from;
    }

    public Resources.Type type() {
        return type;
    }
}
