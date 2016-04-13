package wonder.core.Events;

import wonder.core.Card;
import wonder.core.Event;
import wonder.core.Game;
import wonder.core.Player;

public class CardPlayed implements Event {
    private final Player player;
    private final Card card;
    private final Game game;

    public CardPlayed(final Card card, final Player player, final Game game) {
        this.player = player;
        this.card = card;
        this.game = game;
    }

    public int gameId() {
        return game.id();
    }

    public Card selectedCard() {
        return card;
    }

    public Player player() {
        return player;
    }

    public Game game() {
        return game;
    }
}
