package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Papyrus;

public class ChamberOfCommerce extends Card {
    public ChamberOfCommerce(int minPlayers) {
        super("Chamber Of Commerce", Type.Yellow, Age.Three, minPlayers, ResourcePool.cost(Clay, Clay, Papyrus));
    }
}
