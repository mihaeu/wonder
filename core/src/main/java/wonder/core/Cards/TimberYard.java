package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalStone;
import static wonder.core.Resources.Type.OptionalWood;

public class TimberYard extends Card {
    public TimberYard() {
        super("Timber Yard", Type.Brown, Age.One, 3, 1);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(OptionalStone, OptionalWood), player, game, age);
    }
}
