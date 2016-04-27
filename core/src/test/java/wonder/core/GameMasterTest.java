package wonder.core;


import org.junit.Before;
import org.junit.Test;
import wonder.core.Cards.*;
import wonder.core.Events.*;
import wonder.core.Exceptions.CardAlreadyPlayedException;
import wonder.core.Exceptions.CardNotAffordableException;
import wonder.core.Exceptions.CardNotAvailableException;
import wonder.core.Exceptions.NotAllowedToPlayException;

import java.util.*;

import static org.junit.Assert.*;
import static wonder.core.Card.Age.*;
import static wonder.core.Card.ScienceSymbol.*;
import static wonder.core.Resources.Type.Stone;

public class GameMasterTest {
    private Game game;
    private GameMaster master;
    private GameSetup setup;
    private Player firstPlayer;
    private Map<Integer, Player> players;
    private EventLog log;

    @Before
    public void setUp() {
        log = new EventLog();
        master = new GameMaster(log);

        setup = new GameSetup();
        players = mockPlayers(3);
        game = master.initiateGame(setup.setupGame(3), players, 1);

        firstPlayer = players.get(0);
    }

    @Test
    public void givesEveryPlayerSevenCardsAndThreeCoins() {
        final EventLog log = new EventLog();
        master = new GameMaster(log);
        master.initiateGame(setup.setupGame(4), mockPlayers(4), 2);
        assertEquals(9, log.log().size());
    }

    @Test
    public void idsForGames() {
        master.initiateGame(setup.setupGame(3), mockPlayers(3), 2);
        assertEquals(1, log.log().get(0).game().id());
        assertEquals(2, log.log().get(7).game().id());
    }

    @Test
    public void afterStartPlayersHaveThreeCoins() {
        assertEquals(3, master.coinsAvailable(game.players().get(0), game));
        assertEquals(3, master.coinsAvailable(game.players().get(1), game));
        assertEquals(3, master.coinsAvailable(game.players().get(2), game));
    }

    @Test
    public void afterStartPlayersHaveSevenCards() {
        assertEquals(7, master.cardsAvailable(players.get(0), game).size());
        assertEquals(7, master.cardsAvailable(players.get(1), game).size());
        assertEquals(7, master.cardsAvailable(players.get(2), game).size());
    }

    @Test
    public void playerCannotPlayTwiceInOneRound() throws NotAllowedToPlayException, CardNotAvailableException {
        log.log().add(new CardPlayed(new Loom(3, One), firstPlayer, game, One));
        assertFalse(master.isPlayerAllowedToPlay(firstPlayer, game));
    }

    @Test
    public void detectsWhenRoundIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        log.log().add(new CardPlayed(new Loom(3, One), firstPlayer, game, One));
        log.log().add(new CardPlayed(new Loom(3, One), players.get(1), game, One));
        log.log().add(new CardPlayed(new Loom(3, One), players.get(2), game, One));
        assertTrue(master.isRoundCompleted(game));
    }

    @Test
    public void detectsWhenAgeIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        for (int i = 0; i < 6; i += 1) {
            log.log().add(new RoundCompleted(Player.EVERY, game, One));
        }
        assertTrue(master.isAgeCompleted(game));
    }

    @Test
    public void detectsWhenGameIsCompleted() throws NotAllowedToPlayException, CardNotAvailableException {
        log.log().add(new AgeCompleted(Player.EVERY, game, One));
        log.log().add(new AgeCompleted(Player.EVERY, game, Two));
        log.log().add(new AgeCompleted(Player.EVERY, game, Three));
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
        assertEquals(One, master.activeAge(game));

        log.log().add(new AgeCompleted(Player.EVERY, game, One));
        assertEquals(Two, master.activeAge(game));

        log.log().add(new AgeCompleted(Player.EVERY, game, Two));
        assertEquals(Card.Age.Three, master.activeAge(game));

        log.log().add(new AgeCompleted(Player.EVERY, game, Card.Age.Three));
        assertEquals(Card.Age.Three, master.activeAge(game));
    }

    @Test
    public void listPlayedCards() throws CardNotAvailableException, NotAllowedToPlayException {
        for (int i = 0; i < 7; i += 1) {
            master.cardDiscarded(master.cardsAvailable(players.get(i % 3), game).get(0), players.get(i % 3), game);
        }
        Map<Player, List<Card>> playedCards = master.playedCards(game);
        assertEquals("Played 3 cards, but last round not finished", 2, playedCards.get(firstPlayer).size());
        assertEquals("Played 3 cards, but last round not finished", 2, playedCards.get(players.get(1)).size());
        assertEquals(2, playedCards.get(players.get(2)).size());
    }

    @Test
    public void handsCardsToNextPlayer() throws CardNotAvailableException, NotAllowedToPlayException {
        List<Card> firstHandOfFirstPlayer = master.cardsAvailable(firstPlayer, game);
        Card card = firstHandOfFirstPlayer.remove(0);
        master.cardDiscarded(card, firstPlayer, game);

        playAffordableCard(master, game, players.get(1));
        playAffordableCard(master, game, players.get(2));
        assertEquals("Cards should be passed: 0 -> 1 -> 2 -> 0", firstHandOfFirstPlayer, master.cardsAvailable(players.get(1), game));
    }

    @Test
    public void secondAgeCardsArePassedCounterClockwise() {
        List<Card> firstHandOfFirstPlayer = master.cardsAvailable(firstPlayer, game);
        // simulate 2nd age
        log.log().add(new AgeCompleted(Player.EVERY, game, One));

        firstHandOfFirstPlayer.remove(findAffordableCard(master, game, firstPlayer).get());
        playAffordableCard(master, game, firstPlayer);

        playAffordableCard(master, game, players.get(1));
        playAffordableCard(master, game, players.get(2));
        assertEquals("Cards should be passed: 2 -> 1 -> 0 -> 2", firstHandOfFirstPlayer, master.cardsAvailable(players.get(2), game));
    }

    @Test
    public void canPlayCardForFreeIfConditionIsMet() {
        // this one is special, because we're using a subclass
        log.log().add(new CardPlayed(new EastTradingPost(3), firstPlayer, game, One));
        assertTrue(master.isFree(new Forum(3), firstPlayer, game));

        log.log().add(new CardPlayed(new Scriptorium(3), firstPlayer, game, One));
        assertTrue(master.isFree(new Library(3), firstPlayer, game));

        assertFalse(master.isFree(new Observatory(3), firstPlayer, game));
    }

    @Test
    public void checksIfCardResourceCostsCanBePayed() {
        assertTrue("Card costs no resources", master.isAffordable(new OreVein(3), firstPlayer, game));

        Event gotResources = new GotResources(new Resources(Stone, Stone, Stone), firstPlayer, game, One);
        log.log().addAll(Arrays.asList(gotResources, gotResources, gotResources));
        assertTrue(master.isAffordable(new Aqueduct(3), firstPlayer, game));
    }

    @Test
    public void checksIfCardsCoinCostsCanBePayed() {
        assertTrue(master.isAffordable(new OreVein(3), firstPlayer, game));

        log.log().add(new GotCoins(1, firstPlayer, game, One));
        assertTrue(master.isAffordable(new Sawmill(3), firstPlayer, game));
    }

    @Test
    public void paysCoinsForCardWithCoinCost() throws Exception {
        assertEquals(3, master.coinsAvailable(firstPlayer, game));
        log.add(new GotCards(Arrays.asList(new Sawmill(3)), firstPlayer, game, One));
        master.cardPlayed(new Sawmill(3), firstPlayer, game);
        assertEquals(2, master.coinsAvailable(firstPlayer, game));
    }

    @Test(expected=CardAlreadyPlayedException.class)
    public void cannotPlayTheSameCardTwice()
            throws CardNotAvailableException,
                CardNotAffordableException,
                CardAlreadyPlayedException,
                NotAllowedToPlayException {
        Card card = findAffordableCard(master, game, firstPlayer).get();
        master.cardPlayed(card, firstPlayer, game);
        master.cardDiscarded(master.cardsAvailable(game.players().get(1), game).get(0), game.players().get(1), game);
        master.cardDiscarded(master.cardsAvailable(game.players().get(2), game).get(0), game.players().get(2), game);

        assertFalse("Card has been played before, so player is not allowed to play it again",
                master.cardNotPlayedBefore(card, firstPlayer, game));

        // give the card to the player again to simulate this situation
        log.add(new GotCards(Arrays.asList(card), firstPlayer, game, One));

        // this throws an exception
        master.cardPlayed(card, firstPlayer, game);
    }

    @Test
    public void countsVictoryPoints() throws Exception {
        final PawnShop pawnShop = new PawnShop(3);
        final Altar altar = new Altar(3);
        final Theater theater = new Theater(3);
        log.add(new GotCards(Arrays.asList(pawnShop, altar, theater), firstPlayer, game, One));
        log.add(new GameCompleted(Player.EVERY, game, Three));

        master.cardPlayed(pawnShop, firstPlayer, game);
        log.add(new RoundCompleted(Player.EVERY, game, One));
        assertTrue(3 == master.finalScore(game).get(firstPlayer));

        master.cardPlayed(altar, firstPlayer, game);
        log.add(new RoundCompleted(Player.EVERY, game, One));
        assertTrue(5 == master.finalScore(game).get(firstPlayer));

        master.cardPlayed(theater, firstPlayer, game);
        assertTrue(7 ==  master.finalScore(game).get(firstPlayer));
    }

    @Test
    public void computesVictoryPointsFromScienceSymbols() throws Exception {
        log.add(new GotScienceSymbol(Cogs, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(StoneTablet, firstPlayer, game, One));
        log.add(new GameCompleted(Player.EVERY, game, Three));
        assertEquals("1 set -> 10 points",
                10, master.finalScore(game).get(firstPlayer).intValue());

        log.log().clear();
        log.add(new GotScienceSymbol(Cogs, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Cogs, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Cogs, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(StoneTablet, firstPlayer, game, One));
        log.add(new GotScienceSymbol(StoneTablet, firstPlayer, game, One));
        log.add(new GotScienceSymbol(StoneTablet, firstPlayer, game, One));
        log.add(new GameCompleted(Player.EVERY, game, Three));
        assertEquals("3 sets -> 48 points",
                48, master.finalScore(game).get(firstPlayer).intValue());

        log.log().clear();
        log.add(new GotScienceSymbol(OptionalSymbol, firstPlayer, game, One));
        log.add(new GotScienceSymbol(OptionalSymbol, firstPlayer, game, One));
        log.add(new GotScienceSymbol(OptionalSymbol, firstPlayer, game, One));
        log.add(new GameCompleted(Player.EVERY, game, Three));
        assertEquals("3 optionals -> best combo one set -> 10 points",
                10, master.finalScore(game).get(firstPlayer).intValue());

        log.log().clear();
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Compass, firstPlayer, game, One));
        log.add(new GotScienceSymbol(StoneTablet, firstPlayer, game, One));
        log.add(new GotScienceSymbol(Cogs, firstPlayer, game, One));
        log.add(new GotScienceSymbol(OptionalSymbol, firstPlayer, game, One));
        log.add(new GotScienceSymbol(OptionalSymbol, firstPlayer, game, One));
        log.add(new GameCompleted(Player.EVERY, game, Three));
        assertEquals("3/1/1 with 2 optionals -> should use optionals as compasses",
                34, master.finalScore(game).get(firstPlayer).intValue());
    }

    @Test
    public void computeVictoryPointsFromMilitary() throws Exception {
        log.add(new CardPlayed(new Barracks(3), firstPlayer, game, One));
        master.ageCompleted(game);
        log.add(new CardPlayed(new Walls(3), firstPlayer, game, Two));
        master.ageCompleted(game);
        log.add(new CardPlayed(new Circus(3), players.get(2), game, Three));
        master.ageCompleted(game);

        assertEquals("First player wins two battles each in age one and two",
                13, master.finalScore(game).get(firstPlayer).intValue());
        assertEquals("Third player wins one battle in the third age",
                3, master.finalScore(game).get(players.get(2)).intValue());
    }

    @Test
    public void handsOutCardsForNextAge() {
        master.ageCompleted(game);
        assertEquals(Two, master.cardsAvailable(firstPlayer, game).get(0).age());

        master.ageCompleted(game);
        assertEquals(Three, master.cardsAvailable(firstPlayer, game).get(0).age());
    }

    private Map<Integer, Player> mockPlayers(int number) {
        Map<Integer, Player> players = new HashMap<>();
        for (int i = 0; i < number; i += 1) {
            players.put(i, new Player(i, "Test", new Wonder("Test")));
        }
        return players;
    }

    private void playAffordableCard(GameMaster master, Game game, Player player) {
        Optional<Card> affordableCard = findAffordableCard(master, game, player);
        try {
            if (!affordableCard.isPresent()) {
                master.cardDiscarded(master.cardsAvailable(player, game).get(0), player, game);
            }
            master.cardPlayed(affordableCard.get(), player, game);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Optional<Card> findAffordableCard(GameMaster master, Game game, Player player) {
        return master.cardsAvailable(player, game).stream()
                .filter(card -> master.isAffordable(card, player, game))
                .filter(card -> master.cardNotPlayedBefore(card, player, game))
                .findFirst();
    }
}
