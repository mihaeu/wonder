package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.Stone;

public class Baths extends Card {
    public Baths(int minPlayers) {
        super("Baths", Type.Blue, Age.One, minPlayers, ResourcePool.cost(Stone));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(3, player, game, age);
    }
}
