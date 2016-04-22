package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import static wonder.core.Resources.Type.Stone;
import static wonder.core.Resources.Type.Textile;

public class Library extends Card {
    public Library(int minPlayers) {
        super("Library", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Stone, Stone, Textile), Scriptorium.class);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(ScienceSymbol.StoneTablet, player, game, age);
    }
}
