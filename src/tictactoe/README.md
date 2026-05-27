# Tic Tac Toe

## Prep / Design Notes

`TicTacToeGame`
- Coordinates the game.
- Tracks rows, players, and current player.
- Operation: `markCell(rowNo, colNo, player)`.

`Player`
- Fields: `name`, `markType`.

`MarkType`
- Represents player marks.

`Row`
- Owns cells for a board row.
- Operations: `markCell(cellNo)`, `getCell(cellNo)`.

`Cell`
- Fields: `cellState`, `markType`.
- Operations: `isAvailable()`, `markCell(MarkType)`.

`CellState`
- Tracks whether a cell is available or already occupied.

## Patterns Used

- State-like validation: `CellState` controls whether a cell can be marked.
- Encapsulation: row and cell classes own board-level mutation details.
