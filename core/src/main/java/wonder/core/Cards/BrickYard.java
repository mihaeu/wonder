package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Clay;

public class BrickYard extends Card {
    public BrickYard(int minPlayers) {
        super("Brick Yard", Type.Brown, Age.Two, minPlayers, 1);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Clay, Clay), player, game, age);
    }
}
