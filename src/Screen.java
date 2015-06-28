import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Screen extends JPanel implements Runnable {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private int tickCount;
    private boolean mousePressed;
    private int mouseX;
    private int mouseY;
    private Thread animator;

    private ArrayList<Entity> entities;
    private Player player;
    public Screen() {
        addMouseListener(new MAdapter(this));
        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDoubleBuffered(true);

        loadEntities();

        mousePressed = false;

    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    private void loadEntities() {
        entities = new ArrayList<Entity>();
        entities.add(new Entity("images/barrel.png", 400, 300, 32, 32));
        player = new Player("images/homeboy.png", 600, 500, 32, 64);
        mouseX = 600;
        mouseY = 500;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        drawEntities(g2d);
        drawPlayer(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawEntities(Graphics2D g2d) {
        for(Entity e : entities) {
            e.draw(g2d, this);
        }
    }

    private void drawPlayer(Graphics2D g2d) {
        player.draw(g2d, this);
    }

    public void tick() {
        tickCount++;
        for(Entity e : entities) {
            e.move();
        }

        if(mousePressed) {
            Point mouseP = MouseInfo.getPointerInfo().getLocation();
            Point thisP = this.getLocationOnScreen();
            player.setDestX(mouseP.x - thisP.x - player.getWidth() / 2);
            player.setDestY(mouseP.y - thisP.y - player.getHeight() / 2);
        }

        player.calcD();
        player.move();
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
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
