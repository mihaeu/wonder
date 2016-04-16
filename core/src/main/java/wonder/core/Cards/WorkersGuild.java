package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.*;

public class WorkersGuild extends Card {
    public WorkersGuild() {
        super("Workers Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Ore, Ore, Clay, Stone, Wood));
    }
}
