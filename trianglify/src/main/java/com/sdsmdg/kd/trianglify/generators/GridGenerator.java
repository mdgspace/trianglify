package com.sdsmdg.kd.trianglify.generators;

import android.graphics.Point;

import java.util.Random;
import java.util.Vector;

public class GridGenerator {

    private final Random random = new Random();
    private int bleedX = 0;
    private int bleedY = 0;
    private Vector<Point> grid = new Vector<>();

    private int N_CIRCLE_POINTS = 8;

    public GridGenerator() {
    }

    public GridGenerator(int bleedX, int bleedY) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;
    }

    public Vector<Point> generateRectangularGrid(int width, int height, int cellSize, int variance) {
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

    public Vector<Point> generateCircularGrid(int width, int height, int cellSize, int variance) {
        grid.clear();

        Point center = new Point(width / 2, height / 2);
        int maxRadius = Math.max(width + bleedX, height + bleedY);
        grid.add(center);

        double slice, angle;
        int x, y;

        for (int radius = cellSize; radius < maxRadius; radius += cellSize) {
            slice = 2 * Math.PI / N_CIRCLE_POINTS;
            for (int i = 0; i < N_CIRCLE_POINTS; i++) {
                angle = slice * i;
                x = (int) (center.x + radius * Math.cos(angle)) + random.nextInt(variance);
                y = (int) (center.y + radius * Math.sin(angle)) + random.nextInt(variance);
                grid.add(new Point(x, y));
            }
        }

        return grid;
    }

}
