package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;

public class GuardTower extends Card {
    public GuardTower(int minPlayers) {
        super("Guard Tower", Type.Red, Age.One, minPlayers, ResourcePool.cost(Clay));
    }
}
