package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Events.GotVictoryPoints;
import wonder.core.Game;
import wonder.core.Player;

public class PawnShop extends Card {
    public PawnShop(int minPlayers) {
        super("Pawn Shop", Type.Blue, Age.One, minPlayers);
    }

    @Override
    public Event process(Player player, Game game, Age age) {
        return new GotVictoryPoints(3, player, game, age);
    }
}
