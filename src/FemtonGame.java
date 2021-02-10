import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.Toolkit;


public class FemtonGame extends JFrame implements ActionListener{

    public static final String VERSION = "14.0";
    public boolean gameIsRunning;


    private RutPanel rutPanel;
    private FemtonModel gameModel;
    private boolean muted;

    public FemtonGame() {
        super("Femton");

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        makeMenuBar(this);

        makeLayout(this);

        startNewGame();

        pack();

        Timer t = new Timer( 100,this );
        t.start();

        this.muted = false;

    }

    public static void main(String args[]){
        FemtonGame rutan = new FemtonGame();

    }

    private void makeLayout(FemtonGame frame ) {

        this.rutPanel = new RutPanel();
        frame.add(this.rutPanel);
        setVisible(true);

    }

    private void makeMenuBar(FemtonGame frame) {

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        JMenu fileMenu = new JMenu("File");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu aboutMenu = new JMenu("About");

        menubar.add(fileMenu);
        menubar.add(settingsMenu);
        menubar.add(aboutMenu);


        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem quitItem = new JMenuItem("Quit");

        fileMenu.add(newGameItem);
        fileMenu.add(quitItem);


        newGameItem.addActionListener(    (ActionEvent e) -> { newGame(); });
        quitItem.addActionListener(    (ActionEvent e) -> { quitGame(); });


        JCheckBoxMenuItem muteOption = new JCheckBoxMenuItem("Mute Sound");

        settingsMenu.add(muteOption);

        muteOption.addActionListener( (ActionEvent e) -> { muteGame(); });


        JMenuItem aboutItem = new JMenuItem("About");

        aboutMenu.add(aboutItem);

        aboutItem.addActionListener(    (ActionEvent e) -> { displaySystemInfo(); });

    }





    private void muteGame() {
        if ( !muted ) {
            muted = true;
        } else {
            muted = false;
        }
    }

    private void newGame() {
        int input;
        if ( gameIsRunning ) {
            input = JOptionPane.showConfirmDialog(this,
                    "Do you want to restart the game?");

            switch ( input ){
                case 0:
                    startNewGame();
                    break;
                default:
                    break;
            }

        } else {
            startNewGame();
        }
    }

    private void quitGame() { System.exit(0);}

    private void displaySystemInfo() {
        System.out.println("About");
        JOptionPane.showMessageDialog(this,
                "Femton\nVersion: " + VERSION,"About Femton",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void startNewGame() {
        gameIsRunning = true;

        makeLayout(this);
        this.gameModel = new FemtonModel();

        for ( int i = 0; i<16; i++ ) {
            rutPanel.add(this.gameModel.getGameBoard().get(i));
        }

        getContentPane().removeAll();
        //getContentPane().invalidate();
        //getContentPane().validate();

        this.add(this.rutPanel);
        setVisible(true);
        //getContentPane().repaint();
    }

    private int checkClickedCell() {
        int i;
        for ( i = 0; i<16; i++) {
            if ( gameModel.getGameBoard().get(i).isClicked() ) {
                gameModel.getGameBoard().get(i).unclick();
                return i;
            }
        }
        return -1;
    }

    private boolean checkRange(int cell){
        int x;
        int y;
        int value;
        x = gameModel.getGameBoard().get(cell).getXpos();
        y = gameModel.getGameBoard().get(cell).getYpos();
        value = gameModel.getGameBoard().get(cell).getValue();
        int nullx = gameModel.getNullX();
        int nully = gameModel.getNullY();
        if ( x == nullx+1 || x == nullx-1 || x == nullx ) {
            if ( y == nully+1 || y == nully-1 || y == nully ) {
                if (!(y==nully & x==nullx)) {
                    gameModel.getGameBoard().get(gameModel.getNullID()).setValue(value);

                    gameModel.setNullX(x);
                    gameModel.setNullY(y);
                    gameModel.setNullId(cell);

                    gameModel.getGameBoard().get(cell).setValue(0);
                    gameModel.getGameBoard().get(cell).unclick();

                    return true;
                }
            }
        }
        return false;
    }


    public void checkStatus() {

        int cell = checkClickedCell();
        if ( cell != -1 ) {
            if ( checkRange(cell) ) {
                gameModel.incrementMoves();
                if ( gameModel.checkForWin() ) {
                    gameIsRunning = false;
                    openWinDialog();
                }
            } else {
                gameModel.incrementMoves();
                if ( !muted ) {
                    Toolkit.getDefaultToolkit().beep();
                }
            }

        }

    }
    private void openWinDialog() {
        int input = JOptionPane.showConfirmDialog(this,
                "Congratulations you won!\n" + "You made " + gameModel.getMoves() + " moves.\n" + "Do you want to play again?");
        if ( input == 0 ) {
            startNewGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        checkStatus();
    }
}
