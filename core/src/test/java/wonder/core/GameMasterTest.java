package wonder.core;

import org.junit.Test;
import wonder.core.Cards.GlassWorks;
import wonder.core.Cards.Loom;
import wonder.core.Cards.Press;
import wonder.core.Events.GameCompleted;
import wonder.core.Events.GameCreated;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameMasterTest {
    @Test
    public void givesEveryPlayerSevenCardsAndThreeCoins() {
        GameMaster master = new GameMaster(new GameSetup());
        master.initiateGame(mockPlayers(4));
        assertEquals(9, master.log().size());
    }

    @Test
    public void createsAutoIdForGames() {
        GameMaster master = new GameMaster(new GameSetup());
        master.initiateGame(mockPlayers(3));
        master.initiateGame(mockPlayers(3));
        assertEquals(1, ((GameCreated) master.log().get(0)).gameId());
        assertEquals(2, ((GameCreated) master.log().get(7)).gameId());
    }

    @Test
    public void afterStartPlayersHaveThreeCoins() {
        GameMaster master = new GameMaster(new GameSetup());
        master.initiateGame(mockPlayers(3));
        assertEquals(3, master.games().get(1).players().get(0).coins());
        assertEquals(3, master.games().get(1).players().get(1).coins());
        assertEquals(3, master.games().get(1).players().get(2).coins());
    }

    @Test
    public void afterStartPlayersHaveSevenCards() {
        GameMaster master = new GameMaster(new GameSetup());
        master.initiateGame(mockPlayers(3));
        assertEquals(7, master.games().get(1).players().get(0).cardsAvailable().size());
        assertEquals(7, master.games().get(1).players().get(1).cardsAvailable().size());
        assertEquals(7, master.games().get(1).players().get(2).cardsAvailable().size());
    }

    @Test
    public void playerCannotPlayTwiceInOneRound() {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(mockPlayers(3));
        master.cardPlayed(new Loom(3, Card.Age.One), players.get(0), master.games().get(1));
        assertFalse(master.isPlayerAllowedToPlay(players.get(0), master.games().get(1)));
    }

    @Test
    public void detectsWhenRoundIsCompleted() {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        master.cardPlayed(new Loom(3, Card.Age.One), players.get(0), master.games().get(1));
        master.cardPlayed(new GlassWorks(3, Card.Age.One), players.get(1), master.games().get(1));
        master.cardPlayed(new Press(3, Card.Age.One), players.get(2), master.games().get(1));
        assertTrue(master.isRoundCompleted(master.games().get(1)));
    }

    @Test
    public void detectsWhenAgeIsCompleted() {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        for (int i = 0; i < 6; i += 1) {
            master.cardPlayed(new Loom(3, Card.Age.One), players.get(0), game);
            master.cardPlayed(new GlassWorks(3, Card.Age.One), players.get(1), game);
            master.cardPlayed(new Press(3, Card.Age.One), players.get(2), game);
        }
        assertTrue(master.isAgeCompleted(game));
    }


    public void detectsWhenGameIsCompleted() {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        for (int i = 0; i < 3 * 6; i += 1) {
            master.cardPlayed(new Loom(3, Card.Age.One), players.get(0), game);
            master.cardPlayed(new GlassWorks(3, Card.Age.One), players.get(1), game);
            master.cardPlayed(new Press(3, Card.Age.One), players.get(2), game);
        }
        assertTrue(master.log().get(master.log().size() - 1) instanceof GameCompleted);
    }

    public Map<Integer, Player> mockPlayers(int number) {
        Map<Integer, Player> players = new HashMap<>();
        for (int i = 0; i < number; i += 1) {
            players.put(i, new Player(i, "Test", new Wonder("Test")));
        }
        return players;
    }
}
