package tictactoe;

import tictactoe.player.MarkType;
import tictactoe.player.Player;

public class TicTacToeDemo {
    public static void main(String[] args) {
        Player player1 = new Player("Player1", MarkType.X);
        Player player2 = new Player("Player2", MarkType.O);

        TicTacToeGame game = new TicTacToeGame(player1, player2);
        game.markCell(player1, 0, 0);
        game.markCell(player2, 1, 1);
        game.markCell(player1, 0, 2);
        game.markCell(player2, 1, 2);
        game.markCell(player1, 0, 1);
    }
}
