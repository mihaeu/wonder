package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;
import static wonder.core.Resources.Type.Textile;

public class StrategyGuild extends Card {
    public StrategyGuild() {
        super("StrategyGuild", Type.Purple, Age.Three, 3, ResourcePool.cost(Ore, Ore, Stone, Textile));
    }
}
