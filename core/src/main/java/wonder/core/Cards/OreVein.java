package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

public class OreVein extends Card {
    public OreVein(int minPlayers) {
        super("Ore Vein", Type.Brown, Age.One, minPlayers);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Resources.Type.Ore), player, game, age);
    }
}
