package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;

public class CraftmensGuild extends Card {
    public CraftmensGuild() {
        super("CraftmensGuild", Type.Purple, Age.Three, 3, ResourcePool.cost(Ore, Ore, Stone, Stone));
    }
}
