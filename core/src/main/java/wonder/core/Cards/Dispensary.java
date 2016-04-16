package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Ore;

public class Dispensary extends Card {
    public Dispensary(int minPlayers) {
        super("Dispensary", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Ore, Ore, Glass), Apothecary.class);
    }
}
