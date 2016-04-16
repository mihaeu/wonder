package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalOre;
import static wonder.core.Resources.Type.OptionalStone;

public class Mine extends Card {
    public Mine() {
        super("Mine", Type.Brown, Age.One, 6, 1);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(OptionalOre, OptionalStone), player, game);
    }
}
