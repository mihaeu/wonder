package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class Loom extends Card {
    public Loom(int minPlayers, Age age) {
        super("Loom", Type.Gray, age, minPlayers);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Resources.Type.Textile), player, game, age);
    }
}
