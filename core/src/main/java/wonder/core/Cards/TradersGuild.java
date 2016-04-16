package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Textile;

public class TradersGuild extends Card {
    public TradersGuild() {
        super("Traders Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Textile, Papyrus, Glass));
    }
}
