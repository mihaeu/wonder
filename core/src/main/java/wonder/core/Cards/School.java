package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Wood;

public class School extends Card {
    public School(int minPlayers) {
        super("School", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Wood, Papyrus), School.class);
    }
}
