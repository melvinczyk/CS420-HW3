import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperGUI extends JFrame {
    private final int ROWS = 10; // Number of rows
    private final int COLS = 10; // Number of columns
    private final int MINES = 10; // Number of mines

    private JButton[][] buttons; // Array to hold buttons representing game grid
    private boolean[][] mines; // Array to hold mine locations
    private boolean[][] revealed; // Array to hold revealed squares
    private boolean gameOver;

    public MinesweeperGUI() {
        setTitle("Minesweeper");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttons = new JButton[ROWS][COLS];
        mines = new boolean[ROWS][COLS];
        revealed = new boolean[ROWS][COLS];
        gameOver = false;

        initializeBoard();
        placeMines();

        setLayout(new GridLayout(ROWS, COLS));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton button = new JButton();
                button.addActionListener(new ButtonListener(i, j));
                buttons[i][j] = button;
                add(button);
            }
        }

        setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                mines[i][j] = false;
                revealed[i][j] = false;
            }
        }
    }

    private void placeMines() {
        // Randomly place mines on the board
        int minesPlaced = 0;
        while (minesPlaced < MINES) {
            int row = (int) (Math.random() * ROWS);
            int col = (int) (Math.random() * COLS);
            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

    private void revealSquare(int row, int col) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS || revealed[row][col] || gameOver) {
            return;
        }

        revealed[row][col] = true;
        if (mines[row][col]) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, "Game Over! You hit a mine.", "Game Over", JOptionPane.ERROR_MESSAGE);
            revealAllMines();
        } else {
            int surroundingMines = countSurroundingMines(row, col);
            if (surroundingMines == 0) {
                // Auto-reveal adjacent squares if there are no surrounding mines
                for (int i = row - 1; i <= row + 1; i++) {
                    for (int j = col - 1; j <= col + 1; j++) {
                        revealSquare(i, j);
                    }
                }
            }
            updateButton(row, col, surroundingMines);
        }

        checkGameWon();
    }

    private int countSurroundingMines(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < ROWS && j >= 0 && j < COLS && mines[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private void updateButton(int row, int col, int surroundingMines) {
        JButton button = buttons[row][col];
        if (surroundingMines > 0) {
            button.setText(Integer.toString(surroundingMines));
        } else {
            button.setText("");
        }
        button.setEnabled(false);
    }

    private void revealAllMines() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (mines[i][j]) {
                    updateButton(i, j, 0);
                    buttons[i][j].setBackground(Color.RED);
                }
            }
        }
    }

    private void checkGameWon() {
        boolean allNonMinesRevealed = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (!mines[i][j] && !revealed[i][j]) {
                    allNonMinesRevealed = false;
                    break;
                }
            }
        }
        if (allNonMinesRevealed) {
            JOptionPane.showMessageDialog(this, "Congratulations! You won the game.", "You Won!", JOptionPane.INFORMATION_MESSAGE);
            gameOver = true;
        }
    }

    private class ButtonListener implements ActionListener {
        private final int row;
        private final int col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            revealSquare(row, col);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MinesweeperGUI::new);
    }
}
