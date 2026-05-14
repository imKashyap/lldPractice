package snakenladder;

public class SnakeNLadderDemo {
    public static void main(String[] args) {
        GameBoard game = new GameBoard();
        Player player1 = new Player("Rahul", PlayerColor.BLUE);
        Player player2 = new Player("Tanmay", PlayerColor.GREEN);

        game.addPlayer(player1);
        game.addPlayer(player2);

        game.placeSnake(2, 5, 10);
        game.placeSnake(5, 7, 20);
        game.placeSnake(8, 8, 30);

        game.placeLadder(4, 5, 24);
        game.placeLadder(1, 2, 18);
        game.placeLadder(6, 3, 22);

        game.startGame();

        while (!game.isGameEnded()) {
            Player currentPlayer = game.getCurrentPlayer();
            game.playMove(currentPlayer);
            System.out.println();
        }
    }
}
