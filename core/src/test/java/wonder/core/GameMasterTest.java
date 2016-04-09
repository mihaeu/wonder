package wonder.core;

import org.junit.Test;
import wonder.core.Events.GameCreated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

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

    public Map<Integer, Player> mockPlayers(int number) {
        Map<Integer, Player> players = new HashMap<>();
        for (int i = 0; i < number; i += 1) {
            players.put(i, new Player("Test", new Wonder("Test")) {
                @Override
                public Card selectCard(List<Card> cards) {
                    return null;
                }
            });
        }
        return players;
    }
}
