package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Stone;

public class BuildersGuild extends Card {
    public BuildersGuild() {
        super("Builders Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Stone, Stone, Clay, Clay, Glass));
    }
}
