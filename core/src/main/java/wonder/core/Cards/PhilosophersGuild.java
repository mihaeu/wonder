package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.Clay;
import static wonder.core.Resources.Type.Papyrus;
import static wonder.core.Resources.Type.Textile;

public class PhilosophersGuild extends Card {
    public PhilosophersGuild() {
        super("Philosophers Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Clay, Clay, Clay, Textile, Papyrus));
    }
}
