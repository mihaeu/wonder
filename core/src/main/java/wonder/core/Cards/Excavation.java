package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalClay;
import static wonder.core.Resources.Type.OptionalStone;

public class Excavation extends Card {
    public Excavation() {
        super("Excavation", Type.Brown, Age.One, 4, 1);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(OptionalStone, OptionalClay), player, game);
    }
}
