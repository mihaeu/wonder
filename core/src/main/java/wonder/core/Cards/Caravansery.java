package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.*;

public class Caravansery extends Card {
    public Caravansery(int minPlayers) {
        super("Caravansery", Type.Yellow, Age.Two, minPlayers, ResourcePool.cost(Wood, Wood), Marketplace.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(
                OptionalClay,
                OptionalStone,
                OptionalOre,
                OptionalWood
        ), player, game, age);
    }
}
