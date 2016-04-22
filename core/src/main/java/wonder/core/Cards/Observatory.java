package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Textile;

public class Observatory extends Card {
    public Observatory(int minPlayers) {
        super("Observatory", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Ore, Ore, Glass, Textile), Laboratory.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbol.Cogs, player, game, age);
    }
}
