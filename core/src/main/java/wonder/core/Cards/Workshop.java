package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Glass;

public class Workshop extends Card {
    public Workshop(int minPlayers) {
        super("Workshop", Type.Green, Age.One, minPlayers, ResourcePool.cost(Glass));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbol.Cogs, player, game, age);
    }
}
