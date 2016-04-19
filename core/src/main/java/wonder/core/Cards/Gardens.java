package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Wood;

public class Gardens extends Card {
    public Gardens(int minPlayers) {
        super("Gardens", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Wood, Clay, Clay), Statue.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(5, player, game, age);
    }
}
