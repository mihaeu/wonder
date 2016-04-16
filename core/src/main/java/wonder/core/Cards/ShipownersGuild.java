package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Stone;
import static wonder.core.Resources.Type.Wood;

public class ShipownersGuild extends Card {
    public ShipownersGuild() {
        super("Shipowners Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Wood, Wood, Papyrus, Stone));
    }
}
