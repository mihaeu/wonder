package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;

public class Arena extends Card {
    public Arena(int minPlayers) {
        super("Arena", Type.Yellow, Age.Three, minPlayers, ResourcePool.cost(Ore, Stone, Stone), Dispensary.class);
    }
}
