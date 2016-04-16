package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Wood;

public class Stables extends Card {
    public Stables(int minPlayers) {
        super("Stables", Type.Red, Age.Two, minPlayers, ResourcePool.cost(Ore, Clay, Wood), Apothecary.class);
    }
}
