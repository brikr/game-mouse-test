import javax.swing.*;
import java.awt.*;

public class Tile {
    public Image image;
    public boolean passable;

    public Tile(String imageName) {
        this(imageName, true);
    }

    public Tile (String imageName, boolean passable) {
        image = (new ImageIcon(imageName)).getImage();
        this.passable = passable;
    }
}
