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

    public Presenter(TrianglifyViewInterface view) {
        this.view = view;
    }


    private List<Vector2D> generateGrid() {
        int gridType = view.getTypeGrid();
        Patterns patterns;

        switch (gridType) {
            case TrianglifyViewInterface.GRID_RECTANGLE:
                patterns = new Rectangle(
                        view.getBleedX(), view.getBleedY(), view.getGridHeight(),
                        view.getGridWidth(), view.getCellSize(), view.getVariance());
                break;
            case TrianglifyViewInterface.GRID_CIRCLE:
                patterns = new Circle(
                        view.getBleedX(), view.getBleedY(), 8, view.getGridHeight(),
                        view.getGridWidth(), view.getCellSize(), view.getVariance());
                break;
            default:
                patterns = new Rectangle(
                        view.getBleedX(), view.getBleedY(), view.getGridHeight(),
                        view.getGridWidth(), view.getCellSize(), view.getVariance());
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
        Colorizer colorizer = new FixedPointsColorizer(inputTriangulation,
                view.getPalette(), view.getGridHeight(), view.getGridWidth());
        return colorizer.getColororedTriangulation();
    }

    public void generateSoup() {
        triangulation = generateTriangulation(generateGrid());
        triangulation = generateColoredSoup(triangulation);
    }

    public Triangulation getSoup() {
        generateSoup();
        return triangulation;
    }

    public void clearSoup() {
        triangulation = null;
    }
}
