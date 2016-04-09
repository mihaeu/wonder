package wonder.core;

import java.util.List;
import java.util.Map;

public class Game {
    private int id;
    private Map<Integer, Player> players;
    private List<Card> cards;
    private List<Card> trash;

    public Game(int id, Map<Integer, Player> players, List<Card> cards) {
        this.id = id;
        this.players = players;
        this.cards = cards;
    }

    public int id() {
        return id;
    }

    public Map<Integer, Player> players() {
        return players;
    }

    public List<Card> cards() {
        return cards;
    }

    public List<Card> trash() {
        return trash;
    }
}
