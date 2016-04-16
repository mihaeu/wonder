package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Stone;
import static wonder.core.Resources.Type.Textile;

public class Library extends Card {
    public Library(int minPlayers) {
        super("Library", Type.Green, Age.Two, minPlayers, ResourcePool.cost(Stone, Stone, Textile), Scriptorium.class);
    }
}
