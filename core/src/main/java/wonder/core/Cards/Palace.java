package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.*;

public class Palace extends Card {
    public Palace(int minPlayers) {
        super("Palace", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Glass, Papyrus, Textile, Clay, Wood, Ore, Stone));
    }
}
