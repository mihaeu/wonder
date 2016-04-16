package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Wood;

public class Gardens extends Card {
    public Gardens(int minPlayers) {
        super("Gardens", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Wood, Clay, Clay), Statue.class);
    }
}
