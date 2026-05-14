class Dice:
    + roll() -> int (static)

CellType:
    EMPTY
    SNAKE
    LADDER

class CellFactory:
    createCell(CellType, int moveValue) -> Cell

class Cell:
    - int val
    + executeMove(int currPos) -> destinationPos

enum PlayerColor
    - RED
    - BLUE
    - GREEN
    - YELLOW
    - PINK

class Player:
    - PlayerColor
    - currPos

GameBoard: (singleton)
    - DEQ<Player> players
    - List<Cell> cells

    - rollDice(Player p) -> bool
