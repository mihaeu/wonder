package wonder.core;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String name;
    private Wonder wonder;
    private List<Card> cardsAvailable;
    private int coins = 0;

    public Player(int id, String name, Wonder wonder) {
        this.id = id;
        this.name = name;
        this.wonder = wonder;
        this.cardsAvailable = new ArrayList<>();
    }

    public List<Card> cardsAvailable() {
        return cardsAvailable;
    }

    public int id() {
        return id;
    }

    public int coins() {
        return coins;
    }

    public void addCoins(int coinsToAdd) {
        coins += coinsToAdd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return id == player.id;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
