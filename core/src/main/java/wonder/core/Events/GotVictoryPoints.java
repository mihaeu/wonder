package wonder.core.Events;

import wonder.core.Card.Age;
import wonder.core.Game;
import wonder.core.Player;

public class GotVictoryPoints extends GotCoins {
    public GotVictoryPoints(int amount, Player player, Game game, Age age) {
        super(amount, player, game, age);
    }
}
