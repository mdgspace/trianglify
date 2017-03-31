package com.sdsmdg.kd.trianglify;

/**
 * Created by suyash on 18/3/17.
 */

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.patterns.Patterns;

interface TrianglifyViewInterface {
    int GRID_RECTANGLE = 0;
    int GRID_CIRCLE = 1;

    int TRIANGULATION_DELAUNAY = 0;
    int TRIANGULATION_SHAMOS = 0;

    int getBleedX();
    int getBleedY();
    int getTypeGrid();
    int getGridWidth();
    int getGridHeight();
    int getVariance();
    int getScheme();
    int getCellSize();
    int getTriangulationType();
    Palette getTypeColor();
    Patterns getPattern();
    Triangulation getTriangulation();
}
