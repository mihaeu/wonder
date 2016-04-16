package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class Press extends Card {
    public Press(int minPlayers, Age age) {
        super("Press", Type.Gray, age, minPlayers);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(Resources.Type.Papyrus), player, game);
    }
}
