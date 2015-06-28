import javax.swing.*;
import java.awt.*;

public class GameMouseTest extends JFrame {
    public GameMouseTest() {
        initUI();
    }

    private void initUI() {
        add(new Screen());

        setResizable(false);
        pack();

        setTitle("Mouse Test");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> (new GameMouseTest()).setVisible(true));
    }
}
