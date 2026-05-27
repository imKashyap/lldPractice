# Snake and Ladder

## Prep / Design Notes

`Dice`
- Rolls the dice.
- Operation: `roll(): int`.

`CellType`
- Values: `EMPTY`, `SNAKE`, `LADDER`.

`CellFactory`
- Creates cells based on `CellType` and move value.
- Operation: `createCell(CellType, int moveValue): Cell`.

`Cell`
- Stores board position behavior.
- Operation: `executeMove(int currPos): destinationPos`.
- Empty cells return the same position.
- Snake cells move the player backward.
- Ladder cells move the player forward.

`PlayerColor`
- Values: `RED`, `BLUE`, `GREEN`, `YELLOW`, `PINK`.

`Player`
- Fields: `playerColor`, `currPos`.

`GameBoard`
- Singleton board coordinator.
- Fields: players queue and board cells.
- Operation: `rollDice(Player): boolean`.

## Patterns Used

- Singleton: `GameBoard`.
- Factory: cell creation based on cell type.
- Queue-based turn management: players rotate after each move.
