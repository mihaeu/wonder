package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.*;

public class ScientistsGuild extends Card {
    public ScientistsGuild() {
        super("Scientists Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Wood, Wood, Ore, Ore, Papyrus));
    }
}
