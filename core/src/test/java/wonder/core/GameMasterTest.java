package wonder.core;

import org.junit.Before;
import org.junit.Test;
import wonder.core.Cards.*;
import wonder.core.Events.*;
import wonder.core.Exceptions.CardNotAffordableException;
import wonder.core.Exceptions.CardNotAvailableException;
import wonder.core.Exceptions.NotAllowedToPlayException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static wonder.core.Resources.Type.Stone;

public class GameMasterTest {
    private Game game;
    private GameMaster master;
    private Player firstPlayer;
    private Map<Integer, Player> players;

    @Before
    public void setUp() {
        master = new GameMaster(new GameSetup());
        players = mockPlayers(3);
        master.initiateGame(players);
        game = master.games().get(1);
        firstPlayer = players.get(0);
    }

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
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), firstPlayer, master.games().get(1)));
        assertFalse(master.isPlayerAllowedToPlay(firstPlayer, master.games().get(1)));
    }

    @Test
    public void detectsWhenRoundIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        GameMaster master = new GameMaster(new GameSetup());
        final Map<Integer, Player> players = mockPlayers(3);
        master.initiateGame(players);
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), firstPlayer, master.games().get(1)));
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), players.get(1), master.games().get(1)));
        master.log().add(new CardPlayed(new Loom(3, Card.Age.One), players.get(2), master.games().get(1)));
        assertTrue(master.isRoundCompleted(master.games().get(1)));
    }

    @Test
    public void detectsWhenAgeIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        for (int i = 0; i < 6; i += 1) {
            master.log().add(new RoundCompleted(master.games().get(1)));
        }
        assertTrue(master.isAgeCompleted(game));
    }

    @Test
    public void detectsWhenGameIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        master.log().add(new AgeCompleted(game, Card.Age.One));
        master.log().add(new AgeCompleted(game, Card.Age.Two));
        master.log().add(new AgeCompleted(game, Card.Age.Three));
        assertTrue(master.isGameCompleted(game));
    }

    @Test
    public void findAvailableCardsOfPlayer() {
        assertEquals(7, master.cardsAvailable(firstPlayer, game).size());
        playAffordableCard(master, game, firstPlayer);
        assertEquals(6, master.cardsAvailable(firstPlayer, game).size());
    }

    @Test
    public void findsActiveAge() {
        assertEquals(Card.Age.One, master.activeAge(game));

        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.One));
        assertEquals(Card.Age.Two, master.activeAge(game));

        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.Two));
        assertEquals(Card.Age.Three, master.activeAge(game));

        master.log().add(new AgeCompleted(master.games().get(1), Card.Age.Three));
        assertEquals(Card.Age.Three, master.activeAge(game));
    }

    @Test
    public void listPlayedCards() {
        for (int i = 0; i < 17; i += 1) {
            playAffordableCard(master, game, players.get(i % 3));
        }
        Map<Player, List<Card>> playedCards = master.playedCards(game);
        assertEquals(6, playedCards.get(firstPlayer).size());
        assertEquals(6, playedCards.get(players.get(1)).size());
        assertEquals(5, playedCards.get(players.get(2)).size());
    }

    @Test
    public void handsCardsToNextPlayer() {

        List<Card> firstHandOfFirstPlayer = master.cardsAvailable(firstPlayer, game);
        firstHandOfFirstPlayer.remove(findAffordableCard(master, game, firstPlayer));
        playAffordableCard(master, game, firstPlayer);

        playAffordableCard(master, game, players.get(1));
        playAffordableCard(master, game, players.get(2));
        assertEquals("Cards should be passed: 0 -> 1 -> 2 -> 0", firstHandOfFirstPlayer, master.cardsAvailable(players.get(1), game));
    }

    @Test
    public void secondAgeCardsArePassedCounterClockwise() {
        List<Card> firstHandOfFirstPlayer = master.cardsAvailable(firstPlayer, game);
        // simulate 2nd age
        master.log().add(new AgeCompleted(game, Card.Age.One));

        firstHandOfFirstPlayer.remove(findAffordableCard(master, game, firstPlayer));
        playAffordableCard(master, game, firstPlayer);

        playAffordableCard(master, game, players.get(1));
        playAffordableCard(master, game, players.get(2));
        assertEquals("Cards should be passed: 2 -> 1 -> 0 -> 2", firstHandOfFirstPlayer, master.cardsAvailable(players.get(2), game));
    }

    @Test
    public void canPlayCardForFreeIfConditionIsMet() {
        // this one is special, because we're using a subclass
        master.log().add(new CardPlayed(new EastTradingPost(3), firstPlayer, game));
        assertTrue(master.isFree(new Forum(3), firstPlayer, game));

        master.log().add(new CardPlayed(new Scriptorium(3), firstPlayer, game));
        assertTrue(master.isFree(new Library(3), firstPlayer, game));
    }

    @Test
    public void checksIfCardResourceCostsCanBePayed() {
        assertTrue("Card costs no resources", master.isAffordable(new OreVein(3), firstPlayer, game));

        Event gotResources = new GotResources(new Resources(Stone, Stone, Stone), firstPlayer, game);
        master.log().addAll(Arrays.asList(gotResources, gotResources, gotResources));
        assertTrue(master.isAffordable(new Aqeduct(3), firstPlayer, game));
    }

    @Test
    public void checksIfCardsCoinCostsCanBePayed() {

        assertTrue(master.isAffordable(new OreVein(3), firstPlayer, game));

        master.log().add(new GotCoins(firstPlayer, 1, game.id()));
        assertTrue(master.isAffordable(new SawMill(3), firstPlayer, game));
    }

    private Map<Integer, Player> mockPlayers(int number) {
        Map<Integer, Player> players = new HashMap<>();
        for (int i = 0; i < number; i += 1) {
            players.put(i, new Player(i, "Test", new Wonder("Test")));
        }
        return players;
    }

    private void playAffordableCard(GameMaster master, Game game, Player player) {
        Card affordableCard = findAffordableCard(master, game, player);
        try {
            master.cardPlayed(affordableCard, player, game);
        } catch (NotAllowedToPlayException | CardNotAvailableException | CardNotAffordableException e) {
            e.printStackTrace();
        }
    }

    private Card findAffordableCard(GameMaster master, Game game, Player player) {
        return master.cardsAvailable(player, game).stream()
                .filter(card -> master.isAffordable(card, player, game))
                .findFirst()
                .get();
    }
}
