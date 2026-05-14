package snakenladder;

public class Player {
    private final String name;
    private final PlayerColor color;
    private int position;

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;
        this.position = 0;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return color;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
