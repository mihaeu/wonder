package wonder.core;

import wonder.core.Cards.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GameSetup {

    private List<Card> allCards = Arrays.asList(
            new LumberYard(3),
            new LumberYard(4),
            new OreVein(3),
            new OreVein(4),
            new ClayPit(3),
            new ClayPit(5),
            new StonePit(3),
            new StonePit(5),
            new Loom(3, Card.Age.One),
            new Loom(6, Card.Age.One),
            new Loom(3, Card.Age.Two),
            new Loom(5, Card.Age.Two),
            new GlassWorks(3, Card.Age.One),
            new GlassWorks(6, Card.Age.One),
            new GlassWorks(3, Card.Age.Two),
            new GlassWorks(5, Card.Age.Two),
            new Press(3, Card.Age.One),
            new Press(6, Card.Age.One),
            new Press(3, Card.Age.Two),
            new Press(5, Card.Age.Two)

    );

    List<Card> setupGame(final int numberOfPlayers) {
        return allCards.stream()
                .filter(card -> card.minPlayers() <= numberOfPlayers)
                .collect(Collectors.toList());
    }
}
