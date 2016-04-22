package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Card.ScienceSymbol.OptionalSymbol;
import static wonder.core.Resources.Type.*;

public class ScientistsGuild extends Card {
    public ScientistsGuild() {
        super("Scientists Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Wood, Wood, Ore, Ore, Papyrus));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(OptionalSymbol, player, game, age);
    }
}
