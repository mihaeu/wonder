package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalGlass;
import static wonder.core.Resources.Type.OptionalPapyrus;
import static wonder.core.Resources.Type.OptionalTextile;

public class Forum extends Card {
    public Forum(int minPlayers) {
        super("Forum", Type.Yellow, Age.Two, minPlayers);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(
                OptionalTextile,
                OptionalGlass,
                OptionalPapyrus
        ), player, game);
    }
}
