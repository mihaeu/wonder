package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Glass;
import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Wood;

public class University extends Card {
    public University(int minPlayers) {
        super("University", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Wood, Wood, Papyrus, Glass), Library.class);
    }
}
