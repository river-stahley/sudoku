import java.util.Random;
import javax.swing.SwingUtilities;

public class Sudoku {

    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    public static void main(String[] args) throws Exception {
        int[][] board = generateSolvedBoard();
        shuffleBoard(board);
        
        // Launch GUI
        SwingUtilities.invokeLater(() -> new SudokuGUI(board));

    }
    // Generate a solved Sudoku board
    private static int[][] generateSolvedBoard() {
        int[][] board = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = (i * SUBGRID_SIZE + i / SUBGRID_SIZE + j) % SIZE + 1;
            }
        }
        return board;
    }

    // Shuffle the board to randomize it
    private static void shuffleBoard(int[][] board) {
        Random random = new Random();

        // Shuffle rows within each subgrid
        for (int subgrid = 0; subgrid < SIZE; subgrid += SUBGRID_SIZE) {
            for (int i = 0; i < SUBGRID_SIZE; i++) {
                int row1 = subgrid + i;
                int row2 = subgrid + random.nextInt(SUBGRID_SIZE);
                swapRows(board, row1, row2);
            }
        }

        // Shuffle columns within each subgrid
        for (int subgrid = 0; subgrid < SIZE; subgrid += SUBGRID_SIZE) {
            for (int i = 0; i < SUBGRID_SIZE; i++) {
                int col1 = subgrid + i;
                int col2 = subgrid + random.nextInt(SUBGRID_SIZE);
                swapColumns(board, col1, col2);
            }
        }

        // Optionally, swap entire subgrids (rows and columns)
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            int subgrid1 = i * SUBGRID_SIZE;
            int subgrid2 = random.nextInt(SUBGRID_SIZE) * SUBGRID_SIZE;
            swapSubgrids(board, subgrid1, subgrid2);
        }
    }

    // Remove numbers from the board to create a puzzle
    public static void removeNumbers(int[][] board, int count) {
        Random random = new Random();
        int removed = 0;

        while (removed < count) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);

            if (board[row][col] != 0) {
                board[row][col] = 0; // Remove the number
                removed++;
            }
        }
    }

    // Swap two rows in the board
    private static void swapRows(int[][] board, int row1, int row2) {
        int[] temp = board[row1];
        board[row1] = board[row2];
        board[row2] = temp;
    }

    // Swap two columns in the board
    private static void swapColumns(int[][] board, int col1, int col2) {
        for (int i = 0; i < SIZE; i++) {
            int temp = board[i][col1];
            board[i][col1] = board[i][col2];
            board[i][col2] = temp;
        }
    }

    // Swap two subgrids (rows or columns)
    private static void swapSubgrids(int[][] board, int subgrid1, int subgrid2) {
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            swapRows(board, subgrid1 + i, subgrid2 + i);
        }
    }      
}

