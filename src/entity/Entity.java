package entity;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class Entity {
    public int x, y; // pixel coordinates in world
    public int dx, dy;
    public int width, height;
    public boolean vis = true;
    public boolean passable;
    public Image image;

    public Entity(String imageName, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        image = (new ImageIcon(imageName)).getImage().getScaledInstance(this.width, this.height, Image.SCALE_FAST);
    }

    public Entity(String imageName, int x, int y) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
        this.x = x;
        this.y = y;
        this.width = ii.getIconWidth();
        this.height = ii.getIconHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isVis() {
        return vis;
    }

    public void setVis(boolean vis) {
        this.vis = vis;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public boolean shouldRender(int viewportX, int viewportY, int viewportWidth, int viewportHeight) {
        return vis && this.x + this.width >= viewportX && this.x < viewportX + viewportWidth && this.y + this.height >= viewportY && this.y < viewportHeight;
    }
}
