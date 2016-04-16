package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Textile;

public class CourtHouse extends Card {
    public CourtHouse(int minPlayers) {
        super("CourtHouse", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Clay, Clay, Textile), CourtHouse.class);
    }
}
