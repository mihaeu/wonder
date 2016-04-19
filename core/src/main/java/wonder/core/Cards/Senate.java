package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;
import static wonder.core.Resources.Type.Wood;

public class Senate extends Card {
    public Senate(int minPlayers) {
        super("Senate", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Ore, Stone, Wood, Wood), Library.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(6, player, game, age);
    }
}
