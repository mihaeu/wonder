package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.*;

public class TownHall extends Card {
    public TownHall(int minPlayers) {
        super("Town Hall", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Glass, Ore, Stone, Stone));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(6, player, game, age);
    }
}
