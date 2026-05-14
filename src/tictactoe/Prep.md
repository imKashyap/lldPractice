Game
    - Row
    - Player1, Player2
    - currPlayer
    - markCell(RowNo, ColNo, Player)

Player
    - name
    - MarkType

Row
    - cells
    - markCell(cellNo): boolean
    - getCell(cellNo): Cell

Cell
    - CellState
    - MarkType
    - isAvailable(): boolean
    - markCell(MarkType): boolean
