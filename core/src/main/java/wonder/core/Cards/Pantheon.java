package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.*;

public class Pantheon extends Card {
    public Pantheon(int minPlayers) {
        super("Pantheon", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Clay, Clay, Ore, Papyrus, Textile, Glass), Temple.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(7, player, game, age);
    }
}
