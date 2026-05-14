package tictactoe;

import tictactoe.player.MarkType;
import tictactoe.player.Player;

public class TicTacToeGame {
    private Row[] rows;
    private Player player1;
    private Player player2;
    private Player currPlayer;
    private boolean gameOver;
    private int movesPlayed;

    public TicTacToeGame(Player player1, Player player2) {
        this.rows = new Row[3];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new Row();
        }
        this.player1 = player1;
        this.player2 = player2;
        this.currPlayer = player1;
        this.gameOver = false;
        this.movesPlayed = 0;
    }

    public void markCell(Player player, int row, int col) {
        if (gameOver) {
            throw new RuntimeException("Match already over");
        }
        if (player != currPlayer) {
            throw new RuntimeException("Wrong Player to mark");
        }
        MarkType mark = player.getMarkType();
        boolean marked = rows[row].markCell(col, mark);
        if (marked) {
            movesPlayed++;
            if (checkWinner(row, col, mark)) {
                gameOver = true;
                System.out.println(player.getName() + " is Winner. Match over.");
                return;
            }
            if (movesPlayed == 9) {
                gameOver = true;
                System.out.println("Match drawn.");
                return;
            }
            currPlayer = currPlayer == player1 ? player2 : player1;
            return;
        }
        throw new RuntimeException("Cell not vacant");
    }

    private boolean checkWinner(int row, int col, MarkType mark) {
        boolean rowCheck = true, colCheck = true, leftDiagonal = true, rightDiagonal = true;
        for (int i = 0; i < 3; i++) {
            rowCheck = rowCheck && rows[row].getCell(i).getMark() == mark;
            colCheck = colCheck && rows[i].getCell(col).getMark() == mark;
            leftDiagonal = leftDiagonal && rows[i].getCell(i).getMark() == mark;
            rightDiagonal = rightDiagonal && rows[i].getCell(2 - i).getMark() == mark;
        }
        return rowCheck || colCheck || leftDiagonal || rightDiagonal;
    }

}
