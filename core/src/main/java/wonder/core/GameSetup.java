package wonder.core;

import wonder.core.Cards.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GameSetup {

    private List<Card> allCards = Arrays.asList(
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
            new Marketplace(6)
    );

    List<Card> setupGame(final int numberOfPlayers) {
        return allCards.stream()
                .filter(card -> card.minPlayers() <= numberOfPlayers)
                .collect(Collectors.toList());
    }
}
