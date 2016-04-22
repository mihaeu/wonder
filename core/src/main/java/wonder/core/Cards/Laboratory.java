package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Papyrus;

public class Laboratory extends Card {
    public Laboratory(int minPlayers) {
        super("Laboratory", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Clay, Clay, Papyrus));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbol.Cogs, player, game, age);
    }
}
