package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;
import static wonder.core.Resources.Type.Wood;

public class Senate extends Card {
    public Senate(int minPlayers) {
        super("Senate", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Ore, Stone, Wood, Wood), Library.class);
    }
}
