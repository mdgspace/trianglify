package com.sdsmdg.kd.trianglify;


import com.sdsmdg.kd.trianglify.models.triangulator.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.generators.Triangulator;
import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.models.triangulator.NotEnoughPointsException;
import com.sdsmdg.kd.trianglify.models.triangulator.Vector2D;
import com.sdsmdg.kd.trianglify.patterns.Circle;
import com.sdsmdg.kd.trianglify.patterns.Patterns;
import com.sdsmdg.kd.trianglify.patterns.Rectangle;
import com.sdsmdg.kd.trianglify.utilities.colorizers.Colorizer;
import com.sdsmdg.kd.trianglify.utilities.colorizers.FixedPointsColorizer;

import java.util.List;

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

    private List<Vector2D> generateGrid() {
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

    private Triangulation generateTriangulation(List<Vector2D> inputGrid) {
        DelaunayTriangulator triangulator = new DelaunayTriangulator(inputGrid);
        try {
            triangulator.triangulate();
        } catch (NotEnoughPointsException e){
            e.printStackTrace();
        }
        return new Triangulation(triangulator.getTriangles());
    }

    private Triangulation generateColoredSoup(Triangulation inputTriangulation) {
        Colorizer colorizer = new FixedPointsColorizer(inputTriangulation, view.getPalette(), view.getGridHeight(), view.getGridWidth())
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
