import entity.Entity;
import entity.Player;
import level.Level;
import level.tile.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private int viewportX = 0; // where the camera is currently
    private int viewportY = 0;
    private boolean mousePressed;
    private Thread animator;

    private Level level;
    private Player player;

    public Screen() {
        addMouseListener(new MAdapter(this));
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);

        level = new Level(30, 30, 2);
        player = new Player("images/homeboy.png", WIDTH / 2, HEIGHT / 2, 32, 64);

        mousePressed = false;

    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawTiles(g2d);
        drawEntities(g2d);
        drawPlayer(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawTiles(Graphics2D g2d) {
        Tile[][] tiles = level.getTiles();
        for(int y = viewportY / 32; y <= (viewportY + HEIGHT) / 32; y++) {
            for(int x = viewportX / 32; x <= (viewportX + WIDTH) / 32; x++) {
                g2d.drawImage(tiles[x][y].getImage(), x * 32 - viewportX, y * 32 - viewportY, this);
            }
        }
    }

    private void drawEntities(Graphics2D g2d) {
        for(Entity e : level.getEntities()) {
            if(e.shouldRender(viewportX, viewportY, WIDTH, HEIGHT))
                g2d.drawImage(e.getImage(), e.getX() - viewportX, e.getY() - viewportY, this);
        }
    }

    private void drawPlayer(Graphics2D g2d) {
        g2d.drawImage(player.getImage(), player.getX() - viewportX, player.getY() - viewportY, this);
    }

    public void tick() {
        for(Entity e : level.getEntities()) {
            e.move();
        }

        if(mousePressed) {
            Point mouseP = MouseInfo.getPointerInfo().getLocation();
            Point thisP = this.getLocationOnScreen();
            player.setDestX(viewportX + mouseP.x - thisP.x - player.getWidth() / 2);
            player.setDestY(viewportY + mouseP.y - thisP.y - player.getHeight() / 2);
        }

        player.calcD();
        player.move();
        viewportX = player.getX() + player.getWidth() / 2 - WIDTH / 2; // center camera on player
        viewportY = player.getY() + player.getHeight() / 2 - HEIGHT / 2;
        if(viewportX < 0) viewportX = 0;
        if(viewportX >= level.getWidth() * 32 - WIDTH) viewportX = level.getWidth() * 32 - WIDTH - 1;
        if(viewportY < 0) viewportY = 0;
        if(viewportY >= level.getHeight() * 32 - HEIGHT) viewportY = level.getHeight() * 32 - HEIGHT - 1;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseX(int mouseX) {
    }

    public void setMouseY(int mouseY) {
    }

    // taken from Minicraft by Notch
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60;
        int frames = 0;
        int ticks = 0;
        long lastTimer1 = System.currentTimeMillis();
        boolean running = true;

        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;
            while (unprocessed >= 1) {
                ticks++;
                tick();
                unprocessed -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                repaint();
            }

            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                System.out.println(ticks + " ticks, " + frames + " fps");
                frames = 0;
                ticks = 0;
            }
        }
    }

    private class MAdapter extends MouseAdapter {
        Screen parent;

        public MAdapter(Screen parent) {
            this.parent = parent;
        }

//        public void mouseDragged(MouseEvent e) {
//            parent.setMouseX(e.getX());
//            parent.setMouseY(e.getY());
//        }

        public void mousePressed(MouseEvent e) {
            parent.setMousePressed(true);
            parent.setMouseX(e.getX());
            parent.setMouseY(e.getY());
        }

        public void mouseReleased(MouseEvent e) {
            parent.setMousePressed(false);
        }
    }
}
