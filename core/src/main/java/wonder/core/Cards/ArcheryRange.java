package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Wood;

public class ArcheryRange extends Card {
    public ArcheryRange(int minPlayers) {
        super("Archery Range", Type.Red, Age.Two, minPlayers, ResourcePool.cost(Wood, Wood, Ore), Workshop.class);
    }
}
