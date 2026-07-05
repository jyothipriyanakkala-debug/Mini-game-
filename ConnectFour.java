import java.util.Scanner;

public class ConnectFour {

    static final int ROWS = 6;
    static final int COLS = 7;
    static char[][] board = new char[ROWS][COLS];

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        char playAgain;

        do {
            initializeBoard();

            char currentPlayer = 'X';
            boolean gameOver = false;

            while (!gameOver) {

                displayBoard();

                System.out.println("Player " + currentPlayer + ", choose a column (1-7): ");
                int column = sc.nextInt() - 1;

                if (column < 0 || column >= COLS) {
                    System.out.println("Invalid column. Try again.");
                    continue;
                }

                int row = dropPiece(column, currentPlayer);

                if (row == -1) {
                    System.out.println("Column is full! Choose another.");
                    continue;
                }

                if (checkWinner(row, column, currentPlayer)) {
                    displayBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    gameOver = true;
                } else if (isBoardFull()) {
                    displayBoard();
                    System.out.println("It's a draw!");
                    gameOver = true;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }

            System.out.print("Play again? (Y/N): ");
            playAgain = sc.next().toUpperCase().charAt(0);

        } while (playAgain == 'Y');

        System.out.println("Thanks for playing!");
        sc.close();
    }

    static void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = '.';
            }
        }
    }

    static void displayBoard() {

        System.out.println();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("1 2 3 4 5 6 7");
        System.out.println();
    }

    static int dropPiece(int column, char player) {

        for (int row = ROWS - 1; row >= 0; row--) {

            if (board[row][column] == '.') {
                board[row][column] = player;
                return row;
            }
        }

        return -1;
    }

    static boolean isBoardFull() {

        for (int j = 0; j < COLS; j++) {
            if (board[0][j] == '.') {
                return false;
            }
        }

        return true;
    }

    static boolean checkWinner(int row, int col, char player) {

        return count(row, col, 0, 1, player) >= 4 ||      // Horizontal
               count(row, col, 1, 0, player) >= 4 ||      // Vertical
               count(row, col, 1, 1, player) >= 4 ||      // Diagonal \
               count(row, col, 1, -1, player) >= 4;       // Diagonal /
    }

    static int count(int row, int col, int dr, int dc, char player) {

        int total = 1;

        int r = row + dr;
        int c = col + dc;

        while (r >= 0 && r < ROWS && c >= 0 && c < COLS
                && board[r][c] == player) {
            total++;
            r += dr;
            c += dc;
        }

        r = row - dr;
        c = col - dc;

        while (r >= 0 && r < ROWS && c >= 0 && c < COLS
                && board[r][c] == player) {
            total++;
            r -= dr;
            c -= dc;
        }

        return total;
    }
}