package wonder.core;

import org.junit.Test;
import wonder.core.Cards.Loom;
import wonder.core.Events.*;
import wonder.core.Exceptions.CardNotAvailableException;
import wonder.core.Exceptions.NotAllowedToPlayException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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
    public void playerCannotPlayTwiceInOneRound() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(mockPlayers(3));
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), players.get(0), master.games().get(1)));
        assertFalse(master.isPlayerAllowedToPlay(players.get(0), master.games().get(1)));
    }

    @Test
    public void detectsWhenRoundIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), players.get(0), master.games().get(1)));
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), players.get(1), master.games().get(1)));
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), players.get(2), master.games().get(1)));
        assertTrue(master.isRoundCompleted(master.games().get(1)));
    }

    @Test
    public void detectsWhenAgeIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        for (int i = 0; i < 6; i += 1) {
            master.log().add(new RoundCompleted(master.games().get(1)));
        }
        assertTrue(master.isAgeCompleted(game));
    }

    @Test
    public void detectsWhenGameIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.One));
        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.Two));
        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.Three));
        assertTrue(master.isGameCompleted(master.games().get(1)));
    }

    @Test
    public void findAvailableCardsOfPlayer() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        assertEquals(7, master.cardsAvailable(players.get(0), game).size());
        master.cardPlayed(players.get(0).cardsAvailable().get(0), players.get(0), game);
        assertEquals(6, master.cardsAvailable(players.get(0), game).size());
    }

    @Test
    public void findsActiveAge() {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        assertEquals(Card.Age.One, master.activeAge(game));

        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.One));
        assertEquals(Card.Age.Two, master.activeAge(game));

        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.Two));
        assertEquals(Card.Age.Three, master.activeAge(game));

        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.Three));
        assertEquals(Card.Age.Three, master.activeAge(game));
    }

    @Test
    public void listPlayedCards() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        for (int i = 0; i < 17; i += 1) {
            master.cardPlayed(
                    master.cardsAvailable(players.get(i % 3), game)
                            .get(0),
                    players.get(i % 3),
                    game
            );
        }
        Map<Player, List<Card>> playedCards = master.playedCards(game);
        assertEquals(6, playedCards.get(players.get(0)).size());
        assertEquals(6, playedCards.get(players.get(1)).size());
        assertEquals(5, playedCards.get(players.get(2)).size());
    }

    @Test
    public void handsCardsToNextPlayer() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        List<Card> firstHandOfFirstPlayer = master.cardsAvailable(players.get(0), game);
        master.cardPlayed(firstHandOfFirstPlayer.get(0), players.get(0), game);
        firstHandOfFirstPlayer.remove(0);

        master.cardPlayed(master.cardsAvailable(players.get(1), game).get(0), players.get(1), game);
        master.cardPlayed(master.cardsAvailable(players.get(2), game).get(0), players.get(2), game);
        assertEquals("Cards should be passed: 0 -> 1 -> 2 -> 0", firstHandOfFirstPlayer, master.cardsAvailable(players.get(1), game));
    }

    @Test
    public void secondAgeCardsArePassedCounterClockwise() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        final Game game = master.games().get(1);
        List<Card> firstHandOfFirstPlayer = master.cardsAvailable(players.get(0), game);
        // simulate 2nd age
        master.log().add(new AgeCompleted(game, Card.Age.One));

        master.cardPlayed(firstHandOfFirstPlayer.get(0), players.get(0), game);
        firstHandOfFirstPlayer.remove(0);

        master.cardPlayed(master.cardsAvailable(players.get(1), game).get(0), players.get(1), game);
        master.cardPlayed(master.cardsAvailable(players.get(2), game).get(0), players.get(2), game);
        assertEquals("Cards should be passed: 2 -> 1 -> 0 -> 2", firstHandOfFirstPlayer, master.cardsAvailable(players.get(2), game));
    }

    public Map<Integer, Player> mockPlayers(int number) {
        Map<Integer, Player> players = new HashMap<>();
        for (int i = 0; i < number; i += 1) {
            players.put(i, new Player(i, "Test", new Wonder("Test")));
        }
        return players;
    }
}
