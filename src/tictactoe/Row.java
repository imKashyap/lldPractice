package tictactoe;

import tictactoe.cell.Cell;
import tictactoe.player.MarkType;

public class Row {
    Cell[] cells;

    public Row() {
        cells = new Cell[3];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
        }
    }

    public boolean markCell(int columnNo, MarkType mark) {
        Cell currCell = cells[columnNo];
        return currCell.markCell(mark);
    }

    public Cell getCell(int col) {
        return cells[col];
    }
}
