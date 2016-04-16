package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Wood;

public class SawMill extends Card {
    public SawMill(int minPlayers) {
        super("Saw Mill", Type.Brown, Age.Two, minPlayers);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(Wood, Wood), player, game);
    }
}
