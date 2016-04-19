package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

public class RoundCompleted extends Event {
    public RoundCompleted(Player player, Game game, Card.Age age) {
        super(player, game, age);
    }
}
