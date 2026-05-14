package snakenladder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private final ArrayDeque<Player> players = new ArrayDeque<>();
    private final List<Cell> cells = new ArrayList<>();
    private boolean gameEnded = false;
    private boolean gameStarted = false;

    GameBoard() {
        for (int cellId = 0; cellId < 100; cellId++) {
            cells.add(new Cell());
        }
    }

    public synchronized void addPlayer(Player player) {
        ensureGameNotStarted();
        players.offerLast(player);
    }

    public synchronized void placeSnake(int row, int col, int destination) {
        ensureGameNotStarted();
        int cellId = row * 10 + col;
        Cell cell = cells.get(cellId);
        cell.updateCell(CellType.SNAKE, destination);
    }

    public synchronized void placeLadder(int row, int col, int destination) {
        ensureGameNotStarted();
        int cellId = row * 10 + col;
        Cell cell = cells.get(cellId);
        cell.updateCell(CellType.LADDER, destination);
    }

    public synchronized Player getCurrentPlayer() {
        return players.peekFirst();
    }

    public synchronized boolean isGameEnded() {
        return gameEnded;
    }

    private void ensureGameNotStarted() {
        if (gameStarted) {
            throw new IllegalStateException("Cannot modify board after game has started.");
        }
    }

    public synchronized void startGame() {
        if (gameStarted)
            throw new IllegalStateException("Game already started.");

        if (players.size() < 2) {
            throw new IllegalStateException("At least two players are required.");
        }
        System.out.println("Game has started!!");
        gameStarted = true;
    }

    public synchronized void playMove(Player player) {
        if (!gameStarted) {
            throw new IllegalStateException("Game has not started.");
        }
        if (gameEnded) {
            throw new IllegalArgumentException("Game has ended.");
        }
        Player currPlayer = players.peekFirst();
        if (currPlayer != player) {
            throw new IllegalArgumentException("Not the right player");
        }
        players.pollFirst();
        int val = Dice.rollDice();
        System.out.println(currPlayer.getName() + " rolled: " + val);
        int newPosition = getNewPosition(currPlayer, val);
        int row = newPosition / 10;
        int col = newPosition % 10;
        System.out.println(
                currPlayer.getName() + "'s new Position: [" + row + " , " + col + "]");
        if (newPosition == 99) {
            System.out.println("Game Ended");
            gameEnded = true;
            System.out.println(currPlayer.getName() + " is the winner.");
        } else {
            players.offerLast(currPlayer);
        }
        currPlayer.setPosition(newPosition);
    }

    private int getNewPosition(Player currPlayer, int val) {
        int cellId = currPlayer.getPosition();
        int newPosition = cellId + val;
        if (newPosition >= cells.size()) {
            System.out.println(currPlayer.getName() + " needs exact roll to finish.");
            return currPlayer.getPosition();
        }

        Cell currCell = cells.get(newPosition);
        String name = currPlayer.getName();
        if (currCell.getCellType() == CellType.LADDER) {
            System.out.println("Yay, " + name + " found a ladder.");
            newPosition += currCell.getMoves();
        } else if (currCell.getCellType() == CellType.SNAKE) {
            System.out.println("Oops, " + name + " found a snake.");
            newPosition -= currCell.getMoves();
        }
        newPosition = Math.max(0, Math.min(cells.size() - 1, newPosition));
        return newPosition;
    }
}
