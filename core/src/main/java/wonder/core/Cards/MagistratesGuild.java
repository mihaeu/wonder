package wonder.core.Cards;

import wonder.core.Card;
import wonder.core.ResourcePool;

import static wonder.core.Resources.Type.*;

public class MagistratesGuild extends Card {
    public MagistratesGuild() {
        super("Magistrates Guild", Type.Purple, Age.Three, 3, ResourcePool.cost(Wood, Wood, Wood, Stone, Textile));
    }
}
