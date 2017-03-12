package com.sdsmdg.kd.trianglify.models;

import android.graphics.Point;

import java.util.Vector;

/**
 * Created by shyam on 12-Mar-17.
 */

public class Grid {
    Vector<Point> gridPoints;

    public Grid(Vector<Point> gridPoints){
        this.gridPoints = gridPoints;
    }

    public Vector<Point> getGridPoints() {
        return gridPoints;
    }
}
