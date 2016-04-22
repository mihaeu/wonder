package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class Glassworks extends Card {
    public Glassworks(int minPlayers, Age age) {
        super("Glassworks", Type.Gray, age, minPlayers);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Resources.Type.Glass), player, game, age);
    }
}
