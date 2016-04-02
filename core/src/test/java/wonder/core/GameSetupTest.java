package wonder.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameSetupTest {

    private GameSetup setup;

    @Before
    public void setUp() {
        setup = new GameSetup();
    }

    @Test
    public void forThreePlayersUsesOnlyThreePlayerCards() {
        assertTrue(setup.setupGame(3).stream().allMatch(card -> card.minPlayers() <= 3));
    }

    @Test
    public void forFourPlayersUsesOnlyFourPlayerCards() {
        assertTrue(setup.setupGame(4).stream().allMatch(card -> card.minPlayers() <= 4));
    }

    @Test
    public void forFivePlayersUsesOnlyFivePlayerCards() {
        assertTrue(setup.setupGame(5).stream().allMatch(card -> card.minPlayers() <= 5));
    }

    @Test
    public void forSixPlayersUsesOnlySixPlayerCards() {
        assertTrue(setup.setupGame(6).stream().allMatch(card -> card.minPlayers() <= 6));
    }

    @Test
    public void forSevenPlayersUsesOnlySevenPlayerCards() {
        assertTrue(setup.setupGame(7).stream().allMatch(card -> card.minPlayers() <= 7));
    }

    @Test
    public void gives7CardsToEveryPlayerInThreePlayerGame() {
        assertEquals(7, setup.setupGame(3).size() / (3 * 3));
    }

    @Test
    public void gives7CardsToEveryPlayerInFourPlayerGame() {
        assertEquals(7, setup.setupGame(4).size() / (4 * 3));
    }

    @Test
    public void gives7CardsToEveryPlayerInFivePlayerGame() {
        assertEquals(7, setup.setupGame(5).size() / (5 * 3));
    }

    @Test
    public void gives7CardsToEveryPlayerInSixPlayerGame() {
        assertEquals(7, setup.setupGame(6).size() / (6 * 3));
    }

    @Test
    public void gives7CardsToEveryPlayerInSevenPlayerGame() {
        assertEquals(7, setup.setupGame(7).size() / (7 * 3));
    }
}
