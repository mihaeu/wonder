package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Glass;

public class SpyGuild extends Card {
    public SpyGuild() {
        super("Spy Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Clay, Clay, Clay, Glass));
    }
}
