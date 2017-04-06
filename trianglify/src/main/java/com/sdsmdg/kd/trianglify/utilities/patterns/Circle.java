package com.sdsmdg.kd.trianglify.utilities.patterns;


import com.sdsmdg.kd.trianglify.utilities.triangulator.Vector2D;

import java.util.List;
import java.util.Random;

/**
 * Created by suyash on 12/3/17.
 */

 public class Circle implements Patterns {
    private final Random random = new Random();
    private int bleedX = 0;
    private int bleedY = 0;
    private int pointsPerCircle = 8;

    private int height = 0;
    private int width = 0;

    private int cellSize = 0;
    private int variance = 0;

    public List<Vector2D> grid;

    public Random getRandom() {
        return random;
    }

    public int getBleedX() {
        return bleedX;
    }

    public void setBleedX(int bleedX) {
        this.bleedX = bleedX;
    }

    public int getBleedY() {
        return bleedY;
    }

    public void setBleedY(int bleedY) {
        this.bleedY = bleedY;
    }

    public int getPointsPerCircle() {
        return pointsPerCircle;
    }

    public void setPointsPerCircle(int pointsPerCircle) {
        this.pointsPerCircle = pointsPerCircle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getVariance() {
        return variance;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }

    public Circle(int bleedX, int bleedY, int pointsPerCircle, int height, int width, int cellSize, int variance) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;

        this.pointsPerCircle = pointsPerCircle;

        this.height = height;
        this.width = width;

        this.cellSize = cellSize;
        this.variance = variance;
    }

    public List<Vector2D> generate() {
        Vector2D center = new Vector2D(width / 2, height / 2);

        grid.clear();

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
                this.grid.add(new Vector2D(x, y));
            }
        }

        return grid;
    }
}
