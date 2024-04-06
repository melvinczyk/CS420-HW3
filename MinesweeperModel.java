import java.util.Random;

public class MinesweeperModel 
{
    private final int ROWS;
    private final int COLS;
    private final int MINES;

    private boolean[][] mines;
    private boolean[][] revealed;
    private boolean gameOver;

    public MinesweeperModel(int rows, int cols, int mines) 
    {
        this.ROWS = rows;
        this.COLS = cols;
        this.MINES = mines;

        initializeBoard();
        placeMines();
    }

    private void initializeBoard() 
    {
        mines = new boolean[ROWS][COLS];
        revealed = new boolean[ROWS][COLS];
        gameOver = false;
    }

    private void placeMines() 
    {
        int minesPlaced = 0;
        while (minesPlaced < MINES) 
        {
            int row = (int) (Math.random() * ROWS);
            int col = (int) (Math.random() * COLS);
            if (!mines[row][col]) 
            {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

    public boolean isMine(int row, int col) 
    {
        return mines[row][col];
    }

    public boolean isRevealed(int row, int col) 
    {
        return revealed[row][col];
    }

    public boolean isGameOver() 
    {
        return gameOver;
    }

    public void revealSquare(int row, int col) 
    {
        if (gameOver || revealed[row][col]) 
        {
            return;
        }

        revealed[row][col] = true;
        if (mines[row][col]) 
        {
            gameOver = true;
            revealAllMines();
            return;
        }

        if (countSurroundingMines(row, col) == 0)
        {
            revealNeighbors(row, col);
        }

        if (checkWinCondition())
        {
            gameOver = true;
        }
    }

    private void revealNeighbors(int row, int col) 
    {
        for (int i = row - 1; i <= row + 1; i++) 
        {
            for (int j = col - 1; j <= col + 1; j++) 
            {
                if (isValidPosition(i, j) && !revealed[i][j]) 
                {
                    revealed[i][j] = true;
                    if (countSurroundingMines(i, j) == 0) 
                    {
                        revealNeighbors(i, j);
                    }
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col) 
    {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    public int countSurroundingMines(int row, int col) 
    {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) 
        {
            for (int j = col - 1; j <= col + 1; j++) 
            {
                if (isValidPosition(i, j) && mines[i][j]) 
                {
                    count++;
                }
            }
        }
        return count;
    }

    private void revealAllMines() 
    {
        for (int i = 0; i < ROWS; i++) 
        {
            for (int j = 0; j < COLS; j++) 
            {
                if (mines[i][j]) {
                    revealed[i][j] = true;
                }
            }
        }
    }

    private boolean checkWinCondition() 
    {
        for (int i = 0; i < ROWS; i++) 
        {
            for (int j = 0; j < COLS; j++) 
            {
                if (!revealed[i][j] && !mines[i][j]) 
                {
                    return false;
                }
            }
        }
        return true;
    }

    public int getROWS()
    {
        return ROWS;
    }

    public int getCOLS()
    {
        return COLS;
    }

}
