package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Events.GotVictoryPoints;
import wonder.core.Game;
import wonder.core.Player;

public class Theater extends Card {
    public Theater(int minPlayers) {
        super("Theater", Type.Blue, Age.One, minPlayers);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(2, player, game, age);
    }
}
