package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Stone;

public class Academy extends Card {
    public Academy(int minPlayers) {
        super("Academy", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Stone, Stone, Stone, Glass), School.class);
    }
}
