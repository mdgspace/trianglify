package com.sdsmdg.kd.trianglify.generators;

import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.Triangulation;

/**
 * Created by shyam on 12-Mar-17.
 */

public class DelaunayTriangulator implements Triangulator {
    Grid grid;
    Triangulation delaunayTriangulation;

    @Override
    public void getGrid(Grid grid) {
        this.grid = grid;
    }

    @Override
    public Triangulation getTriangulation() {
        return delaunayTriangulation;
    }

    //TODO put code to intake grid and give out triangulation via delaunay triangulation algorithm (KD)
}
