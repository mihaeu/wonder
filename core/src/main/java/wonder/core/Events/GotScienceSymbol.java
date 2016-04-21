package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Card.ScienceSymbols;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

import java.util.Collections;
import java.util.List;

public class GotScienceSymbol extends Event {
    private final List<ScienceSymbols> symbols;

    public GotScienceSymbol(List<ScienceSymbols> symbols, Player player, Game game, Card.Age age) {
        super(player, game, age);

        this.symbols = symbols;
    }

    public GotScienceSymbol(ScienceSymbols symbol, Player player, Game game, Card.Age age) {
        super(player, game, age);

        this.symbols = Collections.singletonList(symbol);
    }

    public List<ScienceSymbols> symbols() {
        return symbols;
    }
}
