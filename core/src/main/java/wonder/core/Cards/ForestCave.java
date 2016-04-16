package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class ForestCave extends Card {
    public ForestCave() {
        super("Forest Cave", Type.Brown, Age.One, 5);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(Resources.Type.Wood), player, game);
    }
}
