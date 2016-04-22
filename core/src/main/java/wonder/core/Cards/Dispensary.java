package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Ore;

public class Dispensary extends Card {
    public Dispensary(int minPlayers) {
        super("Dispensary", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Ore, Ore, Glass), Apothecary.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbol.Compass, player, game, age);
    }
}
