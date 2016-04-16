package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Wood;

public class Temple extends Card {
    public Temple(int minPlayers) {
        super("Temple", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Wood, Clay, Glass), Altar.class);
    }
}
