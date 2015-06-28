package level;

import entity.Barrel;
import entity.Entity;
import level.tile.DirtTile;
import level.tile.GrassTile;
import level.tile.Tile;

import java.util.ArrayList;
import java.util.Random;

public class Level {
    int width, height; // level size, in 32px tiles
    public Tile[][] tiles; // layer one and two
    public ArrayList<Entity> entities;
    Random rand;

    public Level(int width, int height) {
        rand = new Random();
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        entities = new ArrayList<Entity>();
        this.generate();
    }

    public Level(int width, int height, long seed) {
        rand = new Random(seed);
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        entities = new ArrayList<Entity>();
        this.generate();
    }

    public Level(int width, int height, Tile[][] ground, ArrayList<Entity> entities) {
        rand = new Random(); // likely won't be used
        this.width = width;
        this.height = height;
        this.tiles = ground;
        this.entities = entities;
    }

    private void generate() {
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(Math.random() > 0.01) tiles[x][y] = new GrassTile();
                else tiles[x][y] = new DirtTile();
            }
        }

        for(int i = 0; i < width * height / 100; i++) {
            entities.add(new Barrel(rand.nextInt(width * 32), rand.nextInt(height * 32)));
        }

        System.out.println(entities.size() + " entities.");
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
