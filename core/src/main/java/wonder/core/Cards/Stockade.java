package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Wood;

public class Stockade extends Card {
    public Stockade(int minPlayers) {
        super("Stockade", Type.Red, Age.One, minPlayers, ResourcePool.cost(Wood));
    }
}
