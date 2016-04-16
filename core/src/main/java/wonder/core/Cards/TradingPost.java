package wonder.core.Cards;

import wonder.core.Card;

abstract public class TradingPost extends Card {
    public TradingPost(String name, Type type, Age age, int minPlayers) {
        super(name, type, age, minPlayers);
    }
}
