package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.*;

public class Pantheon extends Card {
    public Pantheon(int minPlayers) {
        super("Pantheon", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Clay, Clay, Ore, Papyrus, Textile, Glass), Temple.class);
    }
}
