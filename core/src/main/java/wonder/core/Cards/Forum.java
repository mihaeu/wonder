package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.*;

public class Forum extends Card {
    public Forum(int minPlayers) {
        super("Forum", Type.Yellow, Age.Two, minPlayers, ResourcePool.cost(Clay, Clay), TradingPost.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(
                OptionalTextile,
                OptionalGlass,
                OptionalPapyrus
        ), player, game, age);
    }
}
