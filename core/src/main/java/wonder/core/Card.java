package wonder.core;

public abstract class Card {
    private int minPlayers;
    private String name;
    private Type type;
    private Age age;

    public Card(String name, Type type, Age age, int minPlayers) {
        this.minPlayers = minPlayers;
        this.name = name;
        this.type = type;
        this.age = age;
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
