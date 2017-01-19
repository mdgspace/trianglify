package com.sdsmdg.kd.trianglify.generators;

import android.graphics.Point;

import java.util.Random;
import java.util.Vector;

public class GridGenerator {

    private final Random random = new Random();
    private int bleedX = 0;
    private int bleedY = 0;
    private Vector<Point> grid = new Vector<>();

    public GridGenerator() {}

    public GridGenerator(int bleedX, int bleedY) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;
    }

    public Vector<Point> generateGrid (int width, int height, int cellSize, int variance) {
        grid.clear();

        int x, y;
        for (int j = -bleedY; j < height + bleedY; j += cellSize) {
            for (int i = -bleedX; i < width + bleedX; i += cellSize) {
                x = i + random.nextInt(variance);
                y = j + random.nextInt(variance);
                grid.add(new Point(x, y));
            }
        }

        return grid;
    }
}
