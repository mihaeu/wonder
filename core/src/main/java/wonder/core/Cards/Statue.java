package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Wood;

public class Statue extends Card {
    public Statue(int minPlayers) {
        super("Statue", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Wood, Ore, Ore), Statue.class);
    }
}
