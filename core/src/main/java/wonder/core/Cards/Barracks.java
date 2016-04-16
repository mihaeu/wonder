package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;

public class Barracks extends Card {
    public Barracks(int minPlayers) {
        super("Barracks", Type.Red, Age.One, minPlayers, ResourcePool.cost(Ore));
    }
}
