package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Textile;

public class Apothecary extends Card {
    public Apothecary(int minPlayers) {
        super("Apothecary", Type.Green, Age.One, minPlayers, ResourcePool.cost(Textile));
    }
}
