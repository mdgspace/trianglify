package com.sdsmdg.kd.trianglify.generators;

import android.graphics.Point;

import java.util.Random;
import java.util.Vector;

public class GridGenerator {

    private final Random random = new Random();
    private int bleedX = 0;
    private int bleedY = 0;
    private int pointsPerCircle = 8;

    public Vector<Point> grid = new Vector<>();

    public enum GridType {CIRCULAR, RECTANGULAR}

    ;
    public GridType currentGridtype = GridType.RECTANGULAR;

    public GridGenerator() {
    }

    public GridGenerator(int bleedX, int bleedY) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;
    }

    public void generateGrid(int width, int height, int cellSize, int variance) {
        this.grid.clear();
        
        if (this.currentGridtype == GridType.RECTANGULAR) {
            int x, y;
            for (int j = -bleedY; j < height + bleedY; j += cellSize) {
                for (int i = -bleedX; i < width + bleedX; i += cellSize) {
                    x = i + random.nextInt(variance);
                    y = j + random.nextInt(variance);
                    this.grid.add(new Point(x, y));
                }
            }
        } else if (this.currentGridtype == GridType.CIRCULAR) {
            Point center = new Point(width / 2, height / 2);
            int maxRadius = Math.max(width + bleedX, height + bleedY);
            this.grid.add(center);

            double slice, angle;
            int x, y;

            for (int radius = cellSize; radius < maxRadius; radius += cellSize) {
                slice = 2 * Math.PI / pointsPerCircle;
                for (int i = 0; i < pointsPerCircle; i++) {
                    angle = slice * i;
                    x = (int) (center.x + radius * Math.cos(angle)) + random.nextInt(variance);
                    y = (int) (center.y + radius * Math.sin(angle)) + random.nextInt(variance);
                    this.grid.add(new Point(x, y));
                }
            }
        }
    }
}