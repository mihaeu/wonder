package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class StonePit extends Card {
    public StonePit(int minPlayers) {
        super("Stone Pit", Type.Brown, Age.One, minPlayers);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(Resources.Type.Stone), player, game);
    }
}
