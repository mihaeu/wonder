package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Textile;

public class CourtHouse extends Card {
    public CourtHouse(int minPlayers) {
        super("CourtHouse", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Clay, Clay, Textile), CourtHouse.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(4, player, game, age);
    }
}
