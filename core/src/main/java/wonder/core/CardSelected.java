package wonder.core;

public class CardSelected implements Event {
    private final Player player;
    private final Card selectedCard;

    public CardSelected(Player player, Card selectedCard) {
        this.player = player;
        this.selectedCard = selectedCard;
    }

    public Player player() {
        return player;
    }

    public Card selectedCard() {
        return selectedCard;
    }
}
