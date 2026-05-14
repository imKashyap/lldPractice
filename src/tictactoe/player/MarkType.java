package tictactoe.player;

public enum MarkType {

    X("X"),
    O("O");

    private String mark;

    MarkType(String mark) {
        this.mark = mark;
    }

    String getMark() {
        return this.mark;
    }
}
