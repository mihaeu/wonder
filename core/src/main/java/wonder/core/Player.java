package wonder.core;

public class Player {
    private int id;
    private String name;
    private Wonder wonder;

    static final Player EVERY = new Player(-1, "", null);

    public Player(int id, String name, Wonder wonder) {
        this.id = id;
        this.name = name;
        this.wonder = wonder;
    }

    public int id() {
        return id;
    }

    public Wonder wonder() {
        return wonder;
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
