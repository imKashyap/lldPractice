package snakenladder;

public class Cell {
    private CellType cellType;
    private int moves;

    public Cell() {
        this.cellType = CellType.EMPTY;
        this.moves = 0;
    }

    public synchronized void updateCell(CellType cellType, int moves) {
        this.cellType = cellType;
        this.moves = moves;
    }

    public CellType getCellType() {
        return cellType;
    }

    public int getMoves() {
        return moves;
    }

}
