package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Stone;

public class Circus extends Card {
    public Circus(int minPlayers) {
        super("Circus", Type.Red, Age.Three, minPlayers, ResourcePool.cost(Stone, Stone, Stone, Ore), TrainingGround.class);
    }
}
