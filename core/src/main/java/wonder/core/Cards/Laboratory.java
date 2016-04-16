package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Papyrus;

public class Laboratory extends Card {
    public Laboratory(int minPlayers) {
        super("Laboratory", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Clay, Clay, Papyrus));
    }
}
