package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Textile;
import static wonder.core.Resources.Type.Wood;

public class Haven extends Card {
    public Haven(int minPlayers) {
        super("Haven", Type.Yellow, Age.Three, minPlayers, ResourcePool.cost(Textile, Ore, Wood), Forum.class);
    }
}
