package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;

public class Workshop extends Card {
    public Workshop(int minPlayers) {
        super("Workshop", Type.Green, Age.One, minPlayers, ResourcePool.cost(Glass));
    }
}
