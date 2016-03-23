package wonder.core;

import org.junit.Before;
import org.junit.Ignore;
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
    @Ignore
    public void gives7CardsToEveryPlayer() {
        assertEquals(7, setup.setupGame(3).size() / 3);
        assertEquals(7, setup.setupGame(4).size() / 4);
        assertEquals(7, setup.setupGame(5).size() / 5);
        assertEquals(7, setup.setupGame(6).size() / 6);
        assertEquals(7, setup.setupGame(7).size() / 7);
    }
}
