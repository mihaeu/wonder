package wonder.core;

import wonder.core.Cards.LumberYard;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GameSetup {

    private List<Card> allCards = Arrays.asList(
            new LumberYard(3),
            new LumberYard(4)
    );

    List<Card> setupGame(final int numberOfPlayers) {
        return allCards.stream()
                .filter(card -> card.minPlayers() <= numberOfPlayers)
                .collect(Collectors.toList());
    }
}
