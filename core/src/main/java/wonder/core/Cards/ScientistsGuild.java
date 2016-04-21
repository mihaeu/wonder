package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotScienceSymbol;

import java.util.Arrays;

import static wonder.core.Card.ScienceSymbols.OptionalCogs;
import static wonder.core.Card.ScienceSymbols.OptionalCompass;
import static wonder.core.Card.ScienceSymbols.OptionalStoneTablet;
import static wonder.core.Resources.Type.*;

public class ScientistsGuild extends Card {
    public ScientistsGuild() {
        super("Scientists Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Wood, Wood, Ore, Ore, Papyrus));
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotScienceSymbol(
                Arrays.asList(OptionalCompass, OptionalCogs, OptionalStoneTablet),
                player,
                game,
                age
        );
    }
}
