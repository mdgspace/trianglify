package com.sdsmdg.kd.trianglify.models;

import com.sdsmdg.kd.trianglify.utilities.triangulator.Vector2D;

import java.util.List;

/**
 * Created by shyam on 12-Mar-17.
 */

public class Grid {
    private List<Vector2D> gridPoints;

    public Grid(List<Vector2D> gridPoints){
        this.gridPoints = gridPoints;
    }

    public List<Vector2D> getGridPoints() {
        return gridPoints;
    }
}
