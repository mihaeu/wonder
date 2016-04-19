package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalOre;
import static wonder.core.Resources.Type.OptionalWood;

public class ForestCave extends Card {
    public ForestCave() {
        super("Forest Cave", Type.Brown, Age.One, 5, 1);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(OptionalWood, OptionalOre), player, game, age);
    }
}
