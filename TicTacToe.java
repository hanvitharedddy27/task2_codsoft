import java.util.Scanner;

public class TicTacToe{
    private static char[] board = new char[9];
    private static final int[][] winningCombinations = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };

    public static void main(String[] args) {
        initializeBoard();
        playGame();
    }

    private static void initializeBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }
    }

    private static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.println("| " + board[i * 3] + " | " + board[i * 3 + 1] + " | " + board[i * 3 + 2] + " |");
            System.out.println("-------------");
        }
    }

    private static boolean isFull() {
        for (char cell : board) {
            if (cell == ' ') {
                return false;
            }
        }
        return true;
    }

    private static boolean isGameOver() {
        for (int[] combination : winningCombinations) {
            if (board[combination[0]] == board[combination[1]] &&
                board[combination[1]] == board[combination[2]] &&
                board[combination[0]] != ' ') {
                return true;
            }
        }
        return isFull();
    }

    private static int evaluate() {
        for (int[] combination : winningCombinations) {
            if (board[combination[0]] == board[combination[1]] &&
                board[combination[1]] == board[combination[2]] &&
                board[combination[0]] == 'O') {
                return 1;
            } else if (board[combination[0]] == board[combination[1]] &&
                board[combination[1]] == board[combination[2]] &&
                board[combination[0]] == 'X') {
                return -1;
            }
        }
        return 0;
    }

    private static int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        if (isGameOver()) {
            return evaluate();
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'O';
                    int eval = minimax(depth + 1, false, alpha, beta);
                    board[i] = ' ';
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == ' ') {
                    board[i] = 'X';
                    int eval = minimax(depth + 1, true, alpha, beta);
                    board[i] = ' ';
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }

    private static int bestMove() {
        int bestEval = Integer.MIN_VALUE;
        int bestMove = -1;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                int eval = minimax(0, false, alpha, beta);
                board[i] = ' ';
                if (eval > bestEval) {
                    bestEval = eval;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    private static void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            printBoard();

            System.out.print("Enter your move (0-8): ");
            int playerMove = scanner.nextInt();

            if (board[playerMove] == ' ') {
                board[playerMove] = 'X';
            } else {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            if (isGameOver()) {
                break;
            }

            int aiMove = bestMove();
            board[aiMove] = 'O';
        }

        printBoard();

        int gameResult = evaluate();
        if (gameResult == 1) {
            System.out.println("AI wins!");
        } else if (gameResult == -1) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }
}
