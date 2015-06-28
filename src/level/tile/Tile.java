package level.tile;

import javax.swing.*;
import java.awt.*;

public class Tile {
    public Image image;
    public boolean passable;

    public Tile(String imageName) {
        this(imageName, true);
    }

    public Tile (String imageName, boolean passable) {
        image = (new ImageIcon(imageName)).getImage().getScaledInstance(32, 32, Image.SCALE_FAST);
        this.passable = passable;
    }

    public Image getImage() {
        return image;
    }
}
