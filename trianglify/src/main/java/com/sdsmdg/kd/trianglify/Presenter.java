package com.sdsmdg.kd.trianglify;


import com.sdsmdg.kd.trianglify.generators.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.generators.Triangulator;
import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.patterns.Circle;
import com.sdsmdg.kd.trianglify.patterns.Patterns;
import com.sdsmdg.kd.trianglify.patterns.Rectangle;

/**
 * <h1>Presenter.java</h1>
 * <b>Description :</b>
 * P of MVP implemented to present data generated using models
 * to a view.
 *
 * @author suyash
 * @since 18/3/17.
 */


public class Presenter {
    TrianglifyViewInterface view;
    Triangulation triangulation;

    int bleedX;
    int bleedY;
    int width;
    int height;
    int cellSize;
    int variance;

    public Presenter(TrianglifyViewInterface view) {
        this.view = view;
    }

    private void populateAttribute(){
        bleedX = view.getBleedX();
        bleedY = view.getBleedY();
        width = view.getGridWidth();
        height = view.getGridHeight();
        cellSize = view.getCellSize();
        variance = view.getVariance();
    }

    private Grid generateGrid() {
        populateAttribute();

        int gridType = view.getTypeGrid();
        Patterns patterns;

        switch (gridType) {
            case TrianglifyViewInterface.GRID_RECTANGLE:
                patterns = new Rectangle(
                        bleedX, bleedY,height, width, cellSize, variance);
                break;
            case TrianglifyViewInterface.GRID_CIRCLE:
                patterns = new Circle(
                        bleedX, bleedY, 8, height, width, cellSize, variance);
                break;
            default:
                patterns = new Rectangle(
                        bleedX, bleedY, height, width, cellSize, variance);
                break;
        }
        return patterns.generate();
    }

    private Triangulation generateTriangulation(Grid inputGrid) {
        Triangulation result;
        Triangulator triangulator;

        switch (view.getTriangulationType()){
            case (TrianglifyViewInterface.TRIANGULATION_DELAUNAY):
                triangulator = new DelaunayTriangulator();
                break;
            default:
                triangulator = new DelaunayTriangulator();
                break;
        }

        result = triangulator.setGrid(inputGrid).getTriangulation();
        return result;
    }

    private Triangulation generateColoredSoup(Triangulation inputTriangulation) {
        // TODO generates colored soup using presenter.getPallete()
        // after colorizer is implemented
        return null;
    }

    public void generateSoup() {
        triangulation = generateTriangulation(generateGrid());
        //TODO Call generateColoredSoup() after Colorizer is imlemented
    }

    public Triangulation getSoup() {
        if (triangulation == null) {
            generateSoup();
        }
        return triangulation;
    }

    public void clearSoup() {
        triangulation = null;
    }
}
