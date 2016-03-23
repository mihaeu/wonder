package wonder.core;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameSetupTest {
    @Test
    public void forThreePlayersUsesOnlyThreePlayerCards() {
        GameSetup setup = new GameSetup();
        assertTrue(setup.setupGame(3).stream().allMatch(card -> card.minPlayers() <= 3));
    }

    @Test
    public void forFourPlayersUsesOnlyFourPlayerCards() {
        GameSetup setup = new GameSetup();
        assertTrue(setup.setupGame(4).stream().allMatch(card -> card.minPlayers() <= 4));
    }

    @Test
    public void forFivePlayersUsesOnlyFivePlayerCards() {
        GameSetup setup = new GameSetup();
        assertTrue(setup.setupGame(5).stream().allMatch(card -> card.minPlayers() <= 5));
    }

    @Test
    public void forSixPlayersUsesOnlySixPlayerCards() {
        GameSetup setup = new GameSetup();
        assertTrue(setup.setupGame(6).stream().allMatch(card -> card.minPlayers() <= 6));
    }

    @Test
    public void forSevenPlayersUsesOnlySevenPlayerCards() {
        GameSetup setup = new GameSetup();
        assertTrue(setup.setupGame(7).stream().allMatch(card -> card.minPlayers() <= 7));
    }
}
