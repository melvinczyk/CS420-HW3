import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperController implements ActionListener {
    private MinesweeperModel model;
    private MinesweeperView view;

    public MinesweeperController(int rows, int cols, int mines) {
        this.model = new MinesweeperModel(rows, cols, mines);
        this.view = new MinesweeperView(rows, cols, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String[] position = e.getActionCommand().split(",");
        int row = Integer.parseInt(position[0]);
        int col = Integer.parseInt(position[1]);

        model.revealSquare(row, col);
        updateView();
        
        // Check game over condition
        if (model.isGameOver()) 
        {
            if (!model.isMine(row, col)) 
            {
                view.showMessage("Congratulations! You have won the game!");
            } else 
            {
                view.showMessage("Game Over! You hit a mine.");
            }
        }
    }

    private void updateView() 
    {
        for (int i = 0; i < model.getROWS(); i++) 
        {
            for (int j = 0; j < model.getCOLS(); j++) 
            {
                if (model.isRevealed(i, j)) 
                {
                    if (model.isMine(i, j)) 
                    {
                        view.updateButton(i, j, "M");
                    } else 
                    {
                        int surroundingMines = model.countSurroundingMines(i, j);
                        view.updateButton(i, j, Integer.toString(surroundingMines));
                    }
                }
            }
        }
    }
}
