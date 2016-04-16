package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.*;

public class TownHall extends Card {
    public TownHall(int minPlayers) {
        super("Town Hall", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Glass, Ore, Stone, Stone));
    }
}
