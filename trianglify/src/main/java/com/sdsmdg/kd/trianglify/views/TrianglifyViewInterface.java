package com.sdsmdg.kd.trianglify.views;

/**
 * Created by suyash on 18/3/17.
 */

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangulation;

public interface TrianglifyViewInterface {
    int GRID_RECTANGLE = 0;
    int GRID_CIRCLE = 1;

    int getBleedX();
    int getBleedY();
    int getTypeGrid();
    int getGridWidth();
    int getGridHeight();
    int getVariance();
    int getCellSize();
    void setFillViewCompletely(boolean fillViewCompletely);
    void setViewState(TrianglifyView.ViewState viewState);
    TrianglifyView.ViewState getViewState();
    boolean isFillTriangle();
    boolean isDrawStrokeEnabled();
    boolean isRandomColoringEnabled();
    Palette getPalette();
    Triangulation getTriangulation();
    void invalidateView(Triangulation triangulation);
}
