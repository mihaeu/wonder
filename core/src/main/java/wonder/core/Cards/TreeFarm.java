package wonder.core.Cards;

import wonder.core.*;
import wonder.core.Events.GotResources;

import static wonder.core.Resources.Type.OptionalClay;
import static wonder.core.Resources.Type.OptionalWood;

public class TreeFarm extends Card {
    public TreeFarm() {
        super("Tree Farm", Type.Brown, Age.One, 6, 1);
    }

    @Override
    public Event process(Player player, Game game) {
        return new GotResources(new Resources(OptionalWood, OptionalClay), player, game);
    }
}
