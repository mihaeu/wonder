package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Wood;

public class School extends Card {
    public School(int minPlayers) {
        super("School", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Wood, Papyrus), School.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbols.StoneTablet, player, game, age);
    }
}
