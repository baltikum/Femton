import javax.swing.*;
import java.awt.*;

public class RutPanel extends JPanel {
    public RutPanel() {
        super();
        setPreferredSize(new Dimension(400,400));
        GridLayout grid = new GridLayout(4,4);
        this.setLayout(grid);
    }
}
