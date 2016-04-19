package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class ClayPool extends Card {
    public ClayPool(int minPlayers) {
        super("Clay Pit", Type.Brown, Age.One, minPlayers);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Resources.Type.Clay), player, game, age);
    }
}