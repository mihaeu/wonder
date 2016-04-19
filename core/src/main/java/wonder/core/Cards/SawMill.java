package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Wood;

public class SawMill extends Card {
    public SawMill(int minPlayers) {
        super("Saw Mill", Type.Brown, Age.Two, minPlayers, 1);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Wood, Wood), player, game, age);
    }
}
