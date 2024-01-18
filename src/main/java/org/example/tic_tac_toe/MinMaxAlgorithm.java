package org.example.tic_tac_toe;
public class MinMaxAlgorithm {
    static String player = "O";
    static String enemy = "X";
    private static final int MAXIMUM_DEPTH = 9;
    // _______________________________________________________________________________________________________________________________
    static Boolean existedEmptySquare(String[][] board) {   // Check if there are empty squares in the board or not.
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j].equals(" "))
                    return true;
        return false;
    }
    // _______________________________________________________________________________________________________________________________
    public static int miniMax(String[][] board, int depth, int alpha, int beta, boolean isMaximum) {
        int boardVal = evaluateBoard(board, depth);

        if (Math.abs(boardVal) > 0 || depth == 0 || !existedEmptySquare(board)) // Terminal node(win/lose/draw) or max depth reached.
            return boardVal;


        if (isMaximum) {                // Maximising player, find the maximum attainable value.
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col].equals(" ")) {
                        board[row][col] = player;
                        highestVal = Math.max(highestVal, miniMax(board, depth - 1, alpha, beta, false));
                        board[row][col] = " ";
                        alpha = Math.max(alpha, highestVal);
                        if (alpha >= beta)
                            return highestVal;
                    }
                }
            }
            return highestVal;
        }

        else {                                      // Minimising player, find the minimum attainable value.
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col].equals(" ")) {
                        board[row][col] = enemy;
                        lowestVal = Math.min(lowestVal, miniMax(board, depth - 1, alpha, beta, true));
                        board[row][col] = " ";
                        if (beta <= alpha)
                            return lowestVal;
                    }
                }
            }
            return lowestVal;
        }
    }
    // _______________________________________________________________________________________________________________________________
    public static int[] getBestMove(String[][] board) {     // Return the best available move in the board.
        int[] bestAvailableMove = new int[] { -1, -1 };
        int bestValue = Integer.MIN_VALUE;      // Initial value with the minimum.

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].equals(" ")) {  // loop on empty squares only.
                    board[row][col] = player;       // Set the board value to be the value of player.
                    int movement = miniMax(board, MAXIMUM_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                    board[row][col] = " ";  // Reset the board value.

                    if (movement > bestValue) {     // Get the best available move as an array of integers.
                        bestAvailableMove[0] = row;
                        bestAvailableMove[1] = col;
                        bestValue = movement;       // Set the best available move to be always the largest one.
                    }
                }
            }
        }

        return bestAvailableMove;   // Return the best available move as an array of integers.
    }
    // _______________________________________________________________________________________________________________________________
    // For a given board, return 10 if wins, 0 if x draws and -10 if x lose, then weight the values according how many moves that
    // would take to realize it by using the depth of the game tree.
    private static int evaluateBoard(String[][] board, int depth) {
        int rowSum = 0;
        int winsOfX = 'X' * 3;
        int winsOfO = 'O' * 3;

        for (int row = 0; row < 3; row++) {         // Check rows for winner.
            for (int col = 0; col < 3; col++)
                rowSum += ((board[row][col]).toCharArray()[0]);

            if (winsOfO == rowSum)
                return 10 + depth;
            else if (winsOfX == rowSum)
                return -10 - depth;
            rowSum = 0;
        }
        // *************************************************************************************************************
        for (int col = 0; col < 3; col++) {         // Check columns for winner.
            for (int row = 0; row < 3; row++)
                rowSum += ((board[row][col]).toCharArray()[0]);

            if (winsOfO == rowSum)
                return 10 + depth;
            else if (winsOfX == rowSum)
                return -10 - depth;
            rowSum = 0;
        }
        // *************************************************************************************************************
        for (int i = 0; i < 3; i++)           // Check diagonals for winner.
            rowSum += ((board[i][i]).toCharArray()[0]);

        if (winsOfO == rowSum)
            return 10 + depth;
        else if (winsOfX == rowSum)
            return -10 - depth;

        rowSum = 0;
        int indexMax = 2;

        for (int i = 0; i <= indexMax; i++)
            rowSum += ((board[i][indexMax - i]).toCharArray()[0]);

        if (winsOfO == rowSum)
            return 10 + depth;
        else if (winsOfX == rowSum)
            return -10 - depth;

        return 0;       // If draws.
    }
}
