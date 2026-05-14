package tictactoe.cell;

import tictactoe.player.MarkType;

public class Cell {
    private CellState state;
    private MarkType mark;

    public Cell() {
        this.state = CellState.VACANT;
    }

    public boolean markCell(MarkType mark) {
        if (!isAvailable()) {
            return false;
        }
        this.mark = mark;
        this.state = CellState.OCCUPIED;
        return true;
    }

    public boolean isAvailable() {
        return state == CellState.VACANT;
    }

    public MarkType getMark() {
        return mark;
    }
}
