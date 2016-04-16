package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Textile;
import static wonder.core.Resources.Type.Wood;

public class Study extends Card {
    public Study(int minPlayers) {
        super("Study", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Wood, Papyrus, Textile), School.class);
    }
}
