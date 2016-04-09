package wonder.core;

import org.junit.Test;
import wonder.core.Events.GameCreated;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

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
        assertEquals(1, ((GameCreated) master.log().get(0)).id());
        assertEquals(2, ((GameCreated) master.log().get(7)).id());
    }

    public Map<Integer, Player> mockPlayers(int number) {
        Map<Integer, Player> players = new HashMap<>();
        for (int i = 0; i < number; i += 1) {
            players.put(i, mock(Player.class));
        }
        return players;
    }
}
