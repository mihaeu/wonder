package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Stone;

public class Lighthouse extends Card {
    public Lighthouse(int minPlayers) {
        super("Lighthouse", Type.Yellow, Age.Three, minPlayers, ResourcePool.cost(Glass, Stone));
    }
}
