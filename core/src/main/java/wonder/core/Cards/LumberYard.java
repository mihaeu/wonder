package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.Wood;

public class LumberYard extends Card {
    public LumberYard(int minPlayers) {
        super("Lumber Yard", Type.Brown, Age.One, minPlayers);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(Wood), player, game);
    }
}
