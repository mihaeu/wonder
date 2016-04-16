package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Stone;

public class Baths extends Card {
    public Baths(int minPlayers) {
        super("Baths", Type.Blue, Age.One, minPlayers, ResourcePool.cost(Stone));
    }
}
