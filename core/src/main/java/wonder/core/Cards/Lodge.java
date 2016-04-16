package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Textile;

public class Lodge extends Card {
    public Lodge(int minPlayers) {
        super("Lodge", Type.Green, Age.Three, minPlayers, ResourcePool.cost(Clay, Clay, Textile, Papyrus), Dispensary.class);
    }
}
