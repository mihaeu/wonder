package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Textile;
import static wonder.core.Resources.Type.Wood;

public class Arsenal extends Card {
    public Arsenal(int minPlayers) {
        super("Arsenal", Type.Red, Age.Three, minPlayers, ResourcePool.cost(Ore, Wood, Wood, Textile));
    }
}
