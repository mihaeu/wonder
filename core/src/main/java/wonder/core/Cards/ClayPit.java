package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalClay;
import static wonder.core.Resources.Type.OptionalOre;

public class ClayPit extends Card {
    public ClayPit() {
        super("Clay Pit", Type.Brown, Age.One, 3, 1);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(OptionalClay, OptionalOre), player, game);
    }
}
