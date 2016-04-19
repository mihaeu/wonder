package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotVictoryPoints;

import static wonder.core.Resources.Type.*;

public class Palace extends Card {
    public Palace(int minPlayers) {
        super("Palace", Type.Blue, Age.Three, minPlayers, ResourcePool.cost(Glass, Papyrus, Textile, Clay, Wood, Ore, Stone));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(8, player, game, age);
    }
}
