package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Stone;

public class Walls extends Card {
    public Walls(int minPlayers) {
        super("Walls", Type.Red, Age.Two, minPlayers, ResourcePool.cost(Stone, Stone, Stone));
    }
}
