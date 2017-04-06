package com.sdsmdg.kd.trianglify.utilities.patterns;

import com.sdsmdg.kd.trianglify.utilities.triangulator.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    List<Vector2D> grid;

    public Rectangle(int bleedX, int bleedY, int height, int width, int cellSize, int variance) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;

        this.variance = variance;
        this.cellSize = cellSize;

        this.height = height;
        this.width = width;

        grid = new ArrayList<>();
    }

    @Override
    public List<Vector2D> generate(){
        grid.clear();

        float x, y;
        for (int j = -bleedY; j < height + bleedY; j += cellSize) {
            for (int i = -bleedX; i < width + bleedX; i += cellSize) {
                x = i + random.nextInt(variance);
                y = j + random.nextInt(variance);
                this.grid.add(new Vector2D(x, y));
            }
        }

        return grid;
    }
}

