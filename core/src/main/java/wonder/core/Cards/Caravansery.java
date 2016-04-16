package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.*;

public class Caravansery extends Card {
    public Caravansery(int minPlayers) {
        super("Caravansery", Type.Yellow, Age.Two, minPlayers);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(
                OptionalClay,
                OptionalStone,
                OptionalOre,
                OptionalWood
        ), player, game);
    }
}
