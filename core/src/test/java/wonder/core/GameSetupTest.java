package wonder.core;

import org.junit.Test;
import wonder.core.Cards.LumberYard;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GameSetupTest {
    @Test
    public void forThreePlayersUsesOnlyThreePlayerCards() {
        List<Card> expected = Arrays.asList(new LumberYard(3));
        GameSetup setup = new GameSetup();
        assertEquals(expected, setup.setupGame(3));
    }

    @Test
    public void forFourPlayersUsesOnlyFourPlayerCards() {
        List<Card> expected = Arrays.asList(new LumberYard(3), new LumberYard(4));
        GameSetup setup = new GameSetup();
        assertEquals(expected, setup.setupGame(4));
    }
}
