import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Cell extends JButton implements MouseListener{
    private final int x;
    private final int y;
    private int value;
    private boolean clicked;

    public Cell(int value,int i, int j) {

        setValue(value);
        setPreferredSize(new Dimension(100,100));
        addMouseListener(this);
        setFont(new Font("Arial", Font.PLAIN, 50));

        this.x = i;
        this.y = j;
        this.value = value;
        this.clicked = false;

    }

    public void unclick() { this.clicked = false; }
    public boolean isClicked(){ return clicked; }
    public int getXpos(){
        return x;
    }
    public int getYpos(){
        return y;
    }
    public int getValue(){ return value; }

    public void setValue( int value ) {
        this.value = value;
            if (value != 0) {
                setText(Integer.toString(value));
            } else {
                setText(null);
            }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        this.clicked = true;
        //System.out.println("clicked: "+ value + "\n" + "X: " + x + "\n" + "Y: " + y + "\n" + "Clickedstatus: " + clicked );
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
