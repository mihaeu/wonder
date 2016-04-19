package wonder.core.Events;

import wonder.core.*;
import wonder.core.Card.Age;

public class GotResources extends Event {
    private final Resources resources;

    public GotResources(Resources resources, Player player, Game game, Age age) {
        super(player, game, age);
        this.resources = resources;
    }

    public Resources resources() {
        return resources;
    }
}
