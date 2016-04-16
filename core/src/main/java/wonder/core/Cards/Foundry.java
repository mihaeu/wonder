package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Ore;

public class Foundry extends Card {
    public Foundry(int minPlayers) {
        super("Foundry", Type.Brown, Age.Two, minPlayers, 1);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(Ore, Ore), player, game);
    }
}
