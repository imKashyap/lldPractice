package tictactoe.player;

public class Player {
    private String name;
    private MarkType mark;

    public Player(String name, MarkType mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return this.name;
    }

    public String getMark() {
        return this.mark.getMark();
    }

    public MarkType getMarkType() {
        return this.mark;
    }
}
