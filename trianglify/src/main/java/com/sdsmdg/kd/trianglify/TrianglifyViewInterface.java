package com.sdsmdg.kd.trianglify;

/**
 * Created by suyash on 18/3/17.
 */

import android.view.View;

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.patterns.Patterns;

interface TrianglifyViewInterface {
    int getBleedX();
    int getBleedY();
    int getTypeGrid();
    int getGridWidth();
    int getGridHeight();
    int getVariance();
    int getScheme();
    int getCellSize();
    Palette getTypeColor();
    Patterns getPattern();
    Triangulation getTriangulation();
}
