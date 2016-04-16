package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;
import wonder.core.Resources;

public class Scriptorium extends Card {
    public Scriptorium(int minPlayers) {
        super("Scriptorium", Type.Green, Age.One, minPlayers, ResourcePool.cost(Resources.Type.Papyrus));
    }
}
