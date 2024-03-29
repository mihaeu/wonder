package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.Stone;

public class Aqueduct extends Card {
    public Aqueduct(int minPlayers) {
        super("Aqueduct", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Stone, Stone, Stone), Baths.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(5, player, game, age);
    }
}
