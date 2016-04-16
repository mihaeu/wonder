package wonder.core;

import java.util.Map;

public abstract class Card {
    private int minPlayers;
    private String name;
    private Type type;
    private Age age;
    private Map<Resources.Type, Integer> resourceCost;
    private int coinCost = 0;
    private Class freeConstruction;

    public Card(String name, Type type, Age age, int minPlayers) {
        this.minPlayers = minPlayers;
        this.name = name;
        this.type = type;
        this.age = age;
    }

    public Card(String name, Type type, Age age, int minPlayers, Map<Resources.Type, Integer> resourceCost) {
        this(name, type, age, minPlayers);
        this.resourceCost = resourceCost;
    }

    public Card(String name, Type type, Age age, int minPlayers, Map<Resources.Type, Integer> resourceCost, Class freeConstruction) {
        this(name, type, age, minPlayers, resourceCost);
        this.freeConstruction = freeConstruction;
    }

    public Card(String name, Type type, Age age, int minPlayers, int coinCost) {
        this(name, type, age, minPlayers);
        this.coinCost = coinCost;
    }

    public Card(String name, Type type, Age age, int minPlayers, int coinCost, Class freeConstruction) {
        this(name, type, age, minPlayers, coinCost);
        this.freeConstruction = freeConstruction;
    }

    int minPlayers() {
        return minPlayers;
    }

    String name() {
        return name;
    }

    Type type() {
        return type;
    }

    Age age() {
        return age;
    }

    int coinCost() {
        return coinCost;
    }

    Map<Resources.Type, Integer> resourceCost() {
        return resourceCost;
    }

    Class freeConstruction() {
        return freeConstruction;
    }

    public Event process(final Player player, final Game game) {
        return () -> 0;
    }

    public enum Age {
        One,
        Two,
        Three
    }

    public enum Type {
        Brown,
        Blue,
        Green,
        Yellow,
        Red,
        Purple,
        Gray
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (minPlayers != card.minPlayers) return false;
        if (name != null ? !name.equals(card.name) : card.name != null) return false;
        if (type != card.type) return false;
        return age == card.age;

    }

    @Override
    public int hashCode() {
        int result = minPlayers;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "minPlayers=" + minPlayers +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", age=" + age +
                '}';
    }
}
