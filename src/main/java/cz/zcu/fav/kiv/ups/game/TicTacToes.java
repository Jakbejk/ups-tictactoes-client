package cz.zcu.fav.kiv.ups.game;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Arrays;

public class TicTacToes {

    public final static char CROSS_PLAYER = 'X';
    public final static char ROUND_PLAYER = 'O';
    public final static char EMPTY_PLAYER = '\0';
    private final static int DEFAULT_PLAYGROUND_SIZE = 9;
    private final static int DEFAULT_PLAYGROUND_WINNER_LINE = 5;

    private final BooleanProperty winProperty = new SimpleBooleanProperty();
    private char[][] playGround;
    private int winnerLine;
    private char player;

    public TicTacToes(int size, int winnerLine, char player) {
        if (winnerLine > size) {
            System.err.println("Size must be greater than winner line. Default values will be set.");
            this.initAttrs(DEFAULT_PLAYGROUND_SIZE, DEFAULT_PLAYGROUND_WINNER_LINE, player);
        } else if (size < 1) {
            System.err.println("Size must be greater than 0. Default values will be set.");
            this.initAttrs(DEFAULT_PLAYGROUND_SIZE, DEFAULT_PLAYGROUND_WINNER_LINE, player);
        } else if (winnerLine < 1) {
            System.err.println("Winner line must be greater than 0. Default values will be set.");
            this.initAttrs(DEFAULT_PLAYGROUND_SIZE, DEFAULT_PLAYGROUND_WINNER_LINE, player);
        } else {
            this.initAttrs(size, winnerLine, player);
        }
    }


    public TicTacToes() {
        this(DEFAULT_PLAYGROUND_SIZE, DEFAULT_PLAYGROUND_WINNER_LINE, 'X');
    }


    private static boolean win(char[][] playGround, int x, int y, char player, int winnerLine, Direction direction) {
        int minX = roundToMin(x - winnerLine);
        int maxX = roundToMax(playGround, x + winnerLine);
        int minY = roundToMin(y - winnerLine);
        int maxY = roundToMax(playGround, y + winnerLine);
        int counter = 0;

        if (direction == Direction.HORIZONTAL) {
            for (int i = minX; i < maxX; i++) {
                if (playGround[i][y] == player) counter++;
                else counter = 0;
                if (counter == winnerLine) {
                    return true;
                }
            }
            return false;
        } else if (direction == Direction.VERTICAL) {
            for (int i = minY; i < maxY; i++) {
                if (playGround[x][i] == player) counter++;
                else counter = 0;
                if (counter == winnerLine) {
                    return true;
                }
            }
            return false;
        } else if (direction == Direction.RISING_DIAGONAL) {
            for (int i = minX; i < maxX; i++) {
                int j = x - i + y;
                if (j < 0 || j >= playGround.length) continue;
                if (playGround[i][j] == player) counter++;
                else counter = 0;
                if (counter == winnerLine) {
                    return true;
                }
            }
            return false;
        } else if (direction == Direction.ASCENDENT_DIAGONAL) {
            for (int i = minX; i < maxX; i++) {
                int j = -x + i + y;
                if (j < 0 || j >= playGround.length) continue;
                if (playGround[i][j] == player) counter++;
                else counter = 0;
                if (counter == winnerLine) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private static int roundToMin(int number) {
        return Math.max(number, 0);
    }

    private static int roundToMax(char[][] playGround, int number) {
        return playGround == null ? DEFAULT_PLAYGROUND_SIZE : Math.min(playGround.length, number);
    }

    private static boolean win(final char[][] playGround, final int x, final int y, final char player, final int winnerLine) {
        return Arrays.stream(Direction.values()).anyMatch(d -> win(playGround, x, y, player, winnerLine, d));
    }

    private void initAttrs(int size, int winnerLine, char player) {
        this.player = player;
        this.winnerLine = winnerLine;
        this.playGround = new char[size][size];
        winProperty.set(false);
    }

    /**
     * Put character on playground if position is empty/free. In case of win, change #{@link #winProperty} to true.
     *
     * @param x X position of new item.
     * @param y Y position of new item.
     * @return True if position is empty/free. Otherwise false.
     */
    public boolean put(int x, int y) {
        if (x < 0 || y < 0 || x >= playGround.length || y >= playGround.length) {
            return false;
        }
        if (playGround[x][y] == EMPTY_PLAYER) {
            playGround[x][y] = player;
            if (win(this.playGround, x, y, this.player, this.winnerLine)) {
                winProperty.set(true);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get shallow copy of playground.
     *
     * @return Shallow copy of playGround
     */
    public char[][] getPlayground() {
        char[][] playgroundCopy = new char[this.playGround.length][];
        for (int i = 0; i < this.playGround.length; i++) {
            playgroundCopy[i] = new char[this.playGround[i].length];
            System.arraycopy(this.playGround[i], 0, playgroundCopy[i], 0, this.playGround[i].length);
        }
        return playgroundCopy;
    }

    public char getPlayer(int x, int y) {
        if (x < 0 || y < 0 || x >= playGround.length || y >= playGround.length) {
            return '\0';
        }
        return this.playGround[x][y];
    }

    public BooleanProperty getWinProperty() {
        return winProperty;
    }

    private enum Direction {
        HORIZONTAL, VERTICAL, RISING_DIAGONAL, ASCENDENT_DIAGONAL
    }

}
