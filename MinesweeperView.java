import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MinesweeperView extends JFrame 
{
    private final int ROWS;
    private final int COLS;
    private JButton[][] buttons;

    public MinesweeperView(int rows, int cols, ActionListener listener) 
    {
        this.ROWS = rows;
        this.COLS = cols;

        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttons = new JButton[ROWS][COLS];
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(ROWS, COLS));
        for (int i = 0; i < ROWS; i++) 
        {
            for (int j = 0; j < COLS; j++) 
            {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(30, 30));
                button.addActionListener(listener);
                button.setActionCommand(i + "," + j); 
                buttons[i][j] = button;
                panel.add(button);
            }
        }

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateButton(int row, int col, String text) 
    {
        buttons[row][col].setText(text);
    }

    public void showMessage(String message) 
    {
        JOptionPane.showMessageDialog(this, message);
    }
}
