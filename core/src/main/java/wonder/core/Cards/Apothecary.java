package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Textile;

public class Apothecary extends Card {
    public Apothecary(int minPlayers) {
        super("Apothecary", Type.Green, Age.One, minPlayers, ResourcePool.cost(Textile));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbols.Compass, player, game, age);
    }
}
