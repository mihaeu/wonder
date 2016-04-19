package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.Ore;
import static wonder.core.Resources.Type.Wood;

public class Statue extends Card {
    public Statue(int minPlayers) {
        super("Statue", Type.Blue, Age.Two, minPlayers, ResourcePool.cost(Wood, Ore, Ore), Statue.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(4, player, game, age);
    }
}
