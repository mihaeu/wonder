package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Stone;

public class Quarry extends Card {
    public Quarry(int minPlayers) {
        super("Quarry", Type.Brown, Age.Two, minPlayers, 1);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotResources(new Resources(Stone, Stone), player, game, age);
    }
}
