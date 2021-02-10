
import java.util.ArrayList;

public class FemtonModel {

    private int moves;
    private boolean gameIsRunning = false;
    private ArrayList<Cell> gameBoard;
    private int nullx;
    private int nully;
    private int nullId;


    public FemtonModel() {
        this.moves = 0;
        this.gameIsRunning = true;
        this.gameBoard = new ArrayList(16);

        GameGenerator generator = new GameGenerator(4);

        int x = 0;
        int y = 0;
        int j = 0;

        for (int i : generator) {

            this.gameBoard.add(new Cell(i, x, y));

            if (i == 0) {
                this.nullx = x;
                this.nully = y;
                this.nullId = j;
            }
            x++;

            if (x == 4) {
                x = 0;
                y++;
            }

            j++;
        }
    }

    public ArrayList<Cell> getGameBoard() {
        return gameBoard;
    }

    public void incrementMoves() {
        this.moves++;
    }

    public int getNullX() { return nullx; }
    public int getNullY() { return nully; }
    public int getNullID() { return nullId; }
    public int getMoves() { return moves; }
    public void setNullX(int x) { this.nullx = x; }
    public void setNullY(int y) { this.nully = y; }
    public void setNullId(int cell) { this.nullId = cell; }

    public boolean checkForWin() {
        if (nullx == 3 & nully == 3) {
            int i;
            int k = 1;
            for (i = 0; i < 15; i++) {
                if ((gameBoard.get(i).getValue() != k)) {
                    return false;
                }
                k++;

            }
        } else {
            return false;
        }
        return true;
    }

    public String toString() {
        return "Number of moves made:" + moves + "\nIs game over = " + gameIsRunning;
    }
}

