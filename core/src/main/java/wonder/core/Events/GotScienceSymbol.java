package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Card.ScienceSymbol;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

public class GotScienceSymbol extends Event {
    private final ScienceSymbol symbol;

    public GotScienceSymbol(ScienceSymbol symbol, Player player, Game game, Card.Age age) {
        super(player, game, age);

        this.symbol = symbol;
    }

    public ScienceSymbol symbol() {
        return symbol;
    }
}
