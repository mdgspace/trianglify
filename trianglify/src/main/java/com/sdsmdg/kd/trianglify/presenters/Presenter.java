package com.sdsmdg.kd.trianglify.presenters;


import android.os.AsyncTask;

import com.sdsmdg.kd.trianglify.utilities.triangulator.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.utilities.triangulator.NotEnoughPointsException;
import com.sdsmdg.kd.trianglify.utilities.triangulator.Vector2D;
import com.sdsmdg.kd.trianglify.utilities.patterns.Circle;
import com.sdsmdg.kd.trianglify.utilities.patterns.Patterns;
import com.sdsmdg.kd.trianglify.utilities.patterns.Rectangle;
import com.sdsmdg.kd.trianglify.utilities.colorizers.Colorizer;
import com.sdsmdg.kd.trianglify.utilities.colorizers.FixedPointsColorizer;
import com.sdsmdg.kd.trianglify.views.TrianglifyView;
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
    private TrianglifyViewInterface view;
    private Triangulation triangulation;
    private TriangleGeneratorTask generatorTask;

    /**
     * flag that keeps track of whether just the color of the triangulation is to be changed or not.
     */
    private boolean generateOnlyColor;

    public Presenter(TrianglifyViewInterface view) {
        this.view = view;
    }

    public boolean getGenerateOnlyColor() {
        return generateOnlyColor;
    }

    public void setGenerateOnlyColor(boolean generateOnlyColor) {
        this.generateOnlyColor = generateOnlyColor;
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
     * Generates soup corresponding to current instance parameters
     * @return triangulation generated
     */
    public Triangulation getSoup() {
        if (generateOnlyColor) {
            triangulation = generateColoredSoup(triangulation);
        } else {
            generateSoup();
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
        } catch (NotEnoughPointsException e) {
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
        view.setViewState(TrianglifyView.ViewState.NULL_TRIANGULATION);
    }

    /**
     * generateSoupAndInvalidateView method starts a new thread to regenerate the triangulation so
     * that the regeneration is not done on the UI thread, thereby reducing the workload on the UI thread.
     */
    public void generateSoupAndInvalidateView() {
        if (generatorTask != null) {
            generatorTask.cancel(true);
        }
        generatorTask = new TriangleGeneratorTask();
        generatorTask.execute();
    }

    /**
     * TriangleGeneratorTask specifies the task of the thread. It regenerates the triangulation in the background in
     * accordance to the value of the generateOnlyColor boolean. Upon the generation of the triangulation, it calls
     * upon the invalidateView method to render the triangulation to the view.
     */

    class TriangleGeneratorTask extends AsyncTask<Void, Void, Triangulation> {

        @Override
        protected Triangulation doInBackground(Void... params) {
            return getSoup();
        }

        @Override
        protected void onPostExecute(Triangulation triangulation) {
            super.onPostExecute(triangulation);
            view.invalidateView(triangulation);
        }
    }
}
