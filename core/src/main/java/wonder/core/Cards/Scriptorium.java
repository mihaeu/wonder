package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

public class Scriptorium extends Card {
    public Scriptorium(int minPlayers) {
        super("Scriptorium", Type.Green, Age.One, minPlayers, ResourcePool.cost(Resources.Type.Papyrus));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbols.StoneTablet, player, game, age);
    }
}
