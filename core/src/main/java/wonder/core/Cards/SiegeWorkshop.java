package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Wood;

public class SiegeWorkshop extends Card {
    public SiegeWorkshop(int minPlayers) {
        super("SiegeWorkshop", Type.Red, Age.Three, minPlayers, ResourcePool.cost(Wood, Clay, Clay, Clay), Laboratory.class);
    }
}
