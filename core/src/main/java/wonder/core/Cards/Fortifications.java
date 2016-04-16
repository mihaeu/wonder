package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;

public class Fortifications extends Card {
    public Fortifications(int minPlayers) {
        super("Fortifications", Type.Red, Age.Three, minPlayers, ResourcePool.cost(Stone, Ore, Ore, Ore), Walls.class);
    }
}
