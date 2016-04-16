package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Textile;

public class Observatory extends Card {
    public Observatory(int minPlayers) {
        super("Observatory", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Ore, Ore, Glass, Textile), Laboratory.class);
    }
}
