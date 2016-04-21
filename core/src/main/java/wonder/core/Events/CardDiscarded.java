package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Game;
import wonder.core.Player;

public class CardDiscarded extends CardPlayed {
    public CardDiscarded(Card card, Player player, Game game, Card.Age age) {
        super(card, player, game, age);
    }
}
