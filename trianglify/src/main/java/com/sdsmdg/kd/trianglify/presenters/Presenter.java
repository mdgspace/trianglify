package com.sdsmdg.kd.trianglify.presenters;


import com.sdsmdg.kd.trianglify.utilities.triangulator.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.utilities.triangulator.NotEnoughPointsException;
import com.sdsmdg.kd.trianglify.utilities.triangulator.Vector2D;
import com.sdsmdg.kd.trianglify.utilities.patterns.Circle;
import com.sdsmdg.kd.trianglify.utilities.patterns.Patterns;
import com.sdsmdg.kd.trianglify.utilities.patterns.Rectangle;
import com.sdsmdg.kd.trianglify.utilities.colorizers.Colorizer;
import com.sdsmdg.kd.trianglify.utilities.colorizers.FixedPointsColorizer;
import com.sdsmdg.kd.trianglify.views.TrianglifyViewInterface;

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

    /**
     * Generates a grid on basis of selected grid type
     * @return Grid of Vector2D
     */
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

    /**
     * Generates soup and returns triangulation
     * @return triangulation corresponding to current instance parameters
     */
    public Triangulation getSoup(int justColor) {
        if(justColor == 0){
            generateSoup();
        }else if(justColor == 1){
            generateColoredSoup(triangulation);
        }
        return triangulation;
    }

    /**
     * Generates colored triangulation.
     */
    public void generateSoup() {
        triangulation = generateTriangulation(generateGrid());
        triangulation = generateColoredSoup(triangulation);
    }

    /**
     * Creates triangles from a list of points
     * @param inputGrid Grid of points for generating triangles
     * @return List of Triangles generated from list of input points
     */
    private Triangulation generateTriangulation(List<Vector2D> inputGrid) {
        DelaunayTriangulator triangulator = new DelaunayTriangulator(inputGrid);
        try {
            triangulator.triangulate();
        } catch (NotEnoughPointsException e){
            e.printStackTrace();
        }
        return new Triangulation(triangulator.getTriangles());
    }

    /**
     * Colors each triangle in triangulation and stores color as triangle's color variable
     * @param inputTriangulation triangulation to color
     * @return Colored triangulation of input triangulation
     */
    private Triangulation generateColoredSoup(Triangulation inputTriangulation) {
        Colorizer colorizer = new FixedPointsColorizer(inputTriangulation,
                view.getPalette(), view.getGridHeight() + 2*view.getBleedY(),
                view.getGridWidth() + 2*view.getBleedX(), view.isRandomColoringEnabled());
        return colorizer.getColororedTriangulation();
    }

    public void clearSoup() {
        triangulation = null;
    }
}
