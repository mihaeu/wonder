package wonder.core;

import wonder.core.Cards.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
class GameSetup {

    List<Card> setupGame(final int numberOfPlayers) {
        if (numberOfPlayers < 3 || numberOfPlayers > 7) {
            throw new IllegalArgumentException("Number of players allowed 3 - 7");
        }

        final List<Card> purpleCards = allCards.stream()
                .filter(card -> card.type() == Card.Type.Purple)
                .limit(numberOfPurpleCardsForGame(numberOfPlayers))
                .collect(Collectors.toList());

        final List<Card> otherCards = allCards.stream()
                .filter(card -> card.type() != Card.Type.Purple)
                .filter(card -> card.minPlayers() <= numberOfPlayers)
                .collect(Collectors.toList());

        otherCards.addAll(purpleCards);
        Collections.shuffle(otherCards);
        return otherCards;
    }

    private int numberOfPurpleCardsForGame(final int numberOfPlayers) {
        return numberOfPlayers + 2;
    }

    private final List<Card> allCards = Arrays.asList(
            new LumberYard(3),
            new LumberYard(4),
            new OreVein(3),
            new OreVein(4),
            new ClayPool(3),
            new ClayPool(5),
            new StonePit(3),
            new StonePit(5),
            new Loom(3, Card.Age.One),
            new Loom(6, Card.Age.One),
            new Loom(3, Card.Age.Two),
            new Loom(5, Card.Age.Two),
            new GlassWorks(3, Card.Age.One),
            new GlassWorks(6, Card.Age.One),
            new GlassWorks(3, Card.Age.Two),
            new GlassWorks(5, Card.Age.Two),
            new Press(3, Card.Age.One),
            new Press(6, Card.Age.One),
            new Press(3, Card.Age.Two),
            new Press(5, Card.Age.Two),
            new ClayPit(),
            new Excavation(),
            new ForestCave(),
            new Mine(),
            new TimberYard(),
            new TreeFarm(),
            new PawnShop(7),
            new PawnShop(4),
            new Baths(7),
            new Baths(3),
            new Altar(3),
            new Altar(5),
            new Theater(3),
            new Theater(6),
            new Apothecary(3),
            new Apothecary(5),
            new Workshop(3),
            new Workshop(7),
            new Scriptorium(3),
            new Scriptorium(4),
            new Stockade(3),
            new Stockade(7),
            new Barracks(3),
            new Barracks(5),
            new GuardTower(3),
            new GuardTower(4),
            new Tavern(4),
            new Tavern(5),
            new Tavern(7),
            new EastTradingPost(3),
            new EastTradingPost(7),
            new WestTradingPost(3),
            new WestTradingPost(7),
            new Marketplace(3),
            new Marketplace(6),
            new SawMill(3),
            new SawMill(4),
            new Quarry(3),
            new Quarry(4),
            new BrickYard(3),
            new BrickYard(4),
            new Foundry(3),
            new Foundry(4),
            new Aqeduct(3),
            new Aqeduct(7),
            new Temple(3),
            new Temple(6),
            new Statue(3),
            new Statue(7),
            new CourtHouse(3),
            new CourtHouse(5),
            new Forum(3),
            new Forum(6),
            new Forum(7),
            new Caravansery(3),
            new Caravansery(5),
            new Caravansery(6),
            new Vineyard(3),
            new Vineyard(6),
            new Bazar(4),
            new Bazar(7),
            new Walls(3),
            new Walls(7),
            new TrainingGround(4),
            new TrainingGround(6),
            new TrainingGround(7),
            new Stables(3),
            new Stables(5),
            new ArcheryRange(3),
            new ArcheryRange(6),
            new Dispensary(3),
            new Dispensary(4),
            new Laboratory(3),
            new Laboratory(5),
            new Library(3),
            new Library(6),
            new School(3),
            new School(7),
            new Pantheon(3),
            new Pantheon(6),
            new Gardens(3),
            new Gardens(4),
            new TownHall(3),
            new TownHall(5),
            new TownHall(6),
            new Palace(3),
            new Palace(7),
            new Senate(3),
            new Senate(5),
            new Haven(3),
            new Haven(4),
            new Lighthouse(3),
            new Lighthouse(6),
            new ChamberOfCommerce(4),
            new ChamberOfCommerce(6),
            new Arena(3),
            new Arena(5),
            new Arena(7),
            new Fortifications(3),
            new Fortifications(7),
            new Circus(4),
            new Circus(5),
            new Circus(6),
            new Arsenal(3),
            new Arsenal(4),
            new Arsenal(7),
            new SiegeWorkshop(3),
            new SiegeWorkshop(5),
            new Lodge(3),
            new Lodge(6),
            new Observatory(3),
            new Observatory(7),
            new University(3),
            new University(4),
            new Academy(3),
            new Academy(7),
            new Study(3),
            new Study(5),
            new WorkersGuild(),
            new CraftmensGuild(),
            new TradersGuild(),
            new PhilosophersGuild(),
            new SpyGuild(),
            new StrategyGuild(),
            new ShipownersGuild(),
            new ScientistsGuild(),
            new MagistratesGuild(),
            new BuildersGuild()
    );
}
