package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Stone;

public class Academy extends Card {
    public Academy(int minPlayers) {
        super("Academy", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Stone, Stone, Stone, Glass), School.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbols.Compass, player, game, age);
    }
}
