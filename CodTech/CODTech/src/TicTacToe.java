import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

	static char[][] board = new char[3][3];
    static Scanner scanner = new Scanner(System.in);
    static boolean playAgainstComputer;
    static int player1Wins = 0, player2Wins = 0, draws = 0;

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to Tic-Tac-Toe!");
            System.out.println("Do you want to play against another player or a computer? (P/C)");
            char choice = scanner.next().charAt(0);
            playAgainstComputer = (choice == 'C' || choice == 'c');
            initializeBoard();
            playGame();
            printScore();
            System.out.println("Do you want to play again? (Y/N)");
            char playAgain = scanner.next().charAt(0);
            if (playAgain != 'Y' && playAgain != 'y') {
                System.out.println("Thanks for playing!");
                break;
            }
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    private static void playGame() {
        boolean playing = true;
        boolean player1Turn = true;
        while (playing) {
            drawBoard();
            playerMove(player1Turn ? 'X' : 'O');
            if (checkWin()) {
                playing = false;
                drawBoard();
                System.out.println("Player " + (player1Turn ? "1" : "2") + " wins!");
                if (player1Turn) {
                    player1Wins++;
                } else {
                    player2Wins++;
                }
            } else if (checkDraw()) {
                playing = false;
                drawBoard();
                System.out.println("It's a draw!");
                draws++;
            }
            player1Turn = !player1Turn;
        }
    }

    private static void printScore() {
        System.out.println("Score: Player 1 - " + player1Wins + ", Player 2 - " + (playAgainstComputer ? "Computer" : "Player 2") + " - " + player2Wins + ", Draws - " + draws);
    }

    private static void playerMove(char symbol) {
        if (!playAgainstComputer || symbol == 'X') {
            humanMove(symbol);
        } else {
            computerMove(symbol);
        }
    }

    private static void humanMove(char symbol) {
        while (true) {
            System.out.println("Player " + (symbol == 'X' ? "1" : "2") + "'s turn. Enter row and column (1-3):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
                board[row][col] = symbol;
                break;
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
    }

    private static void computerMove(char symbol) {
        Random random = new Random();
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (board[row][col] != '-');
        board[row][col] = symbol;
        System.out.println("Computer placed " + symbol + " in position (" + (row + 1) + ", " + (col + 1) + ")");
    }

    private static boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            // Check rows and columns
            if (board[i][0] != '-' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) return true;
            if (board[0][i] != '-' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) return true;
        }
        // Check diagonals
        if (board[0][0] != '-' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) return true;
        if (board[0][2] != '-' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) return true;
        return false;
    }

    private static boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private static void drawBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }
}
