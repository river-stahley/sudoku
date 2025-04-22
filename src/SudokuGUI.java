import java.awt.*;
import javax.swing.*;

public class SudokuGUI extends JFrame {
    private static final int SIZE = 9;
    private final JTextField[][] cells = new JTextField[SIZE][SIZE];
    private final int[][] board;

    public SudokuGUI(int[][] board) {
        this.board = board;
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 400));

        // Create Sudoku grid
        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j] = new JTextField();
                cells[i][j].setHorizontalAlignment(JTextField.CENTER);
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                cells[i][j].setEditable(false);
                cells[i][j].setBackground(Color.WHITE);
                

                // Add bold black borders for 3x3 subgrids
                int top = (i % 3 == 0) ? 3 : 1;
                int left = (j % 3 == 0) ? 3 : 1;
                int bottom = (i == SIZE - 1) ? 3 : 1;
                int right = (j == SIZE - 1) ? 3 : 1;
                cells[i][j].setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                gridPanel.add(cells[i][j]);
            }
        }

        // Add buttons
        JPanel buttonPanel = new JPanel();
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton checkButton = new JButton("Check Solution");
        JButton resetButton = new JButton("Reset");
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(resetButton);

        // add action listeners to difficulty buttons
        easyButton.addActionListener(e -> setDifficulty(20));
        mediumButton.addActionListener(e -> setDifficulty(40));
        hardButton.addActionListener(e -> setDifficulty(60));

        // Add action listeners
        checkButton.addActionListener(e -> {
            if (isSolutionValid()) {
                JOptionPane.showMessageDialog(SudokuGUI.this, "Congratulations! You solved the puzzle!");
            } else {
                JOptionPane.showMessageDialog(SudokuGUI.this, "The solution is incorrect. Try again.");
            }
        });

        resetButton.addActionListener(e -> resetBoard());

        // Add components to the frame
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to set difficulty by removing numbers
    private void setDifficulty(int count) {
        Sudoku.removeNumbers(board, count); // Use your existing removeNumbers method
        updateBoard(); // Reset the board to reflect the new difficulty
    }

    private boolean isSolutionValid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                String text = cells[i][j].getText();
                if (text.isEmpty() || !text.matches("\\d") || Integer.parseInt(text) != board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Method to update the board display
    private void updateBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(Color.WHITE);
                } else {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

    private void resetBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    cells[i][j].setText("");
                }
            }
        }
    }
}
