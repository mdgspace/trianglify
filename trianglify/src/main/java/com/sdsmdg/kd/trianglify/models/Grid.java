package com.sdsmdg.kd.trianglify.models;

import android.graphics.Point;

import com.sdsmdg.kd.trianglify.models.triangulator.Vector2D;

import java.util.List;
import java.util.Vector;

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
