package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Wood;

public class TrainingGround extends Card {
    public TrainingGround(int minPlayers) {
        super("Training Ground", Type.Red, Age.Two, minPlayers, ResourcePool.cost(Wood, Ore, Ore));
    }
}
