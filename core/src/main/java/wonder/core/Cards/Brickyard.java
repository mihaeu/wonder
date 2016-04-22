package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Clay;

public class Brickyard extends Card {
    public Brickyard(int minPlayers) {
        super("Brickyard", Type.Brown, Age.Two, minPlayers, 1);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Clay, Clay), player, game, age);
    }
}
