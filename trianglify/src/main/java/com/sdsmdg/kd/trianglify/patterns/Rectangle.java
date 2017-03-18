package com.sdsmdg.kd.trianglify.patterns;

import android.graphics.Point;
import com.sdsmdg.kd.trianglify.models.Grid;
import java.util.Random;
import java.util.Vector;

/**
 * Created by suyash on 12/3/17.
 */

public class Rectangle implements Patterns {
    private final Random random = new Random();
    private int bleedX = 0;
    private int bleedY = 0;

    private int height= 0;
    private int width= 0;

    private int cellSize = 0;
    private int variance= 0;

    public Vector<Point> gridPoints = new Vector<>();
    public Grid grid;

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

    public Vector<Point> getGridPoints() {
        return gridPoints;
    }

    public void setGridPoints(Vector<Point> gridPoints) {
        this.gridPoints = gridPoints;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Rectangle(int bleedX, int bleedY, int width, int cellSize, int variance) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;

        this.variance = variance;
        this.cellSize = cellSize;

        this.bleedX= bleedX;
        this.bleedY = bleedY;
    }

    @Override
    public Grid generate(){
        this.gridPoints.clear();

        int x, y;
        for (int j = -bleedY; j < height + bleedY; j += cellSize) {
            for (int i = -bleedX; i < width + bleedX; i += cellSize) {
                x = i + random.nextInt(variance);
                y = j + random.nextInt(variance);
                this.gridPoints.add(new Point(x, y));
            }
        }

        grid = new Grid(gridPoints);
        return grid;
    }
}

