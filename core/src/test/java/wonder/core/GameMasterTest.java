package wonder.core;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class GameMasterTest {
    @Test
    public void givesEveryPlayerSevenCardsAndThreeCoins() {
        GameMaster master = new GameMaster(new GameSetup());
        Map<Integer, Player> players = new HashMap<>();
        players.put(1, mock(Player.class));
        players.put(2, mock(Player.class));
        players.put(3, mock(Player.class));
        players.put(4, mock(Player.class));
        master.initiateGame(players);
        assertEquals(9, master.log().size());
    }

    @Test
    public void createsAutoIdForGames() {
        GameMaster master = new GameMaster(new GameSetup());
        Map<Integer, Player> players = new HashMap<>();
        players.put(1, mock(Player.class));
        players.put(2, mock(Player.class));
        players.put(3, mock(Player.class));
        master.initiateGame(players);
        assertEquals(7, master.log().size());
    }
}
