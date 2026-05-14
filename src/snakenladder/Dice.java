package snakenladder;

public class Dice {
    private static final int MIN = 1;
    private static final int MAX = 6;

    public static int rollDice() {
        return (int) (Math.random() * (MAX - MIN + 1)) + 1;
    }
}
