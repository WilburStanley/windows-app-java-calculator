import javax.swing.*;
import java.awt.*;

public class GraphicsFrame extends JFrame {
    private final int SCREEN_WIDTH = 400;
    private final int SCREEN_HEIGHT = 650;

    GraphicsFrame() {
        this.setTitle("Calculator Swing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
    }
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
