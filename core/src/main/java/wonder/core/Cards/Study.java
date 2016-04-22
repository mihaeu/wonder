package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Textile;
import static wonder.core.Resources.Type.Wood;

public class Study extends Card {
    public Study(int minPlayers) {
        super("Study", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Wood, Papyrus, Textile), School.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbol.Cogs, player, game, age);
    }
}
