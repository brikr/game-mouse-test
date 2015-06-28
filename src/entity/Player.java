package entity;

import entity.Entity;

public class Player extends Entity {
    int destX, destY;
    double speed;

    public Player(String imageName, int x, int y, int width, int height) {
        super(imageName, x, y, width, height);
        speed = 5;
        destX = x;
        destY = y;
    }

    public void setDestX(int destX) {
        this.destX = destX;
    }

    public void setDestY(int destY) {
        this.destY = destY;
    }

    public void calcD() {
        int totalX = destX - x;
        int totalY = destY - y;
        double totalLength = Math.sqrt(totalX * totalX + totalY * totalY);
        if(totalLength < speed) {
            dx = totalX;
            dy = totalY;
        } else {
            dx = (int)Math.round(totalX * (speed / totalLength));
            dy = (int)Math.round(totalY * (speed / totalLength));
        }
        //System.out.println("dx: " + dx + "\ndy: " + dy + "\ntotalX: " + totalX + "\ntotalY: " + totalY + "\ntotalD: " + totalD + "\nspeed: " + (speed) + "\n----------------");
    }
}
