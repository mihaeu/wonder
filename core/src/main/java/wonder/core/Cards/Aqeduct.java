package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Stone;

public class Aqeduct extends Card {
    public Aqeduct(int minPlayers) {
        super("Aqeduct", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Stone, Stone, Stone), Baths.class);
    }
}
