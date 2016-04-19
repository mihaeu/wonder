package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Game;
import wonder.core.Player;

public class PayedCoins extends GotCoins {
    public PayedCoins(int amount, Player player, Game game, Card.Age age) {
        super(amount, player, game, age);
    }
}
