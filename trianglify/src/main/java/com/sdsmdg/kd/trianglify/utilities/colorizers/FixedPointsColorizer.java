package com.sdsmdg.kd.trianglify.utilities.colorizers;

import android.util.Log;

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangle;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.utilities.Point;

import static android.content.ContentValues.TAG;

/**
 * <h1>Fixed Point Colorizer</h1>
 * <b>Description :</b>
 * Fixed point colorizer contains methods that colorize triangles
 * based on the color palette provided in the constructer.
 *
 * @author suyash
 * @since 24/3/17.
 */

public class FixedPointsColorizer implements Colorizer {
    private Triangulation triangulation;
    private Palette colorPalette;

    private int gridWidth;
    private int gridHeight;

    public Palette getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(Palette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public Triangulation getTriangulation() {
        return triangulation;
    }

    public void setTriangulation(Triangulation triangulation) {
        this.triangulation = triangulation;
    }

    public FixedPointsColorizer(Triangulation triangulation, Palette colorPalette,
                                int gridHeight, int gridWidth) {
        this.triangulation = triangulation;
        this.colorPalette = colorPalette;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    @Override
    public Triangulation getColororedTriangulation(){
        if (triangulation != null){
            for (Triangle triangle : triangulation.getTriangleList()){
                triangle.setColor(getColorForPoint(triangle.getCentroid()));
            }
        } else {
            Log.i(TAG, "colorizeTriangulation: Triangulation cannot be null!");
        }
        return getTriangulation();
    }

    /**
     * Returns color corresponding to the point passed in parameter by
     * calculating average of specified by the palette.
     *
     *
     * Relation between palette color and position on rectangle is
     * depicted in the following figure:
     *
     *(c1 are corresponding int values representing color in ColorPalette.java)
     *    c1              c2                c3
     *       +-------------+--------------+
     *       |             |              |
     *       |     r1      |      r2      |
     *       |             |              |
     *    c8 +------------c9--------------+ c4
     *       |             |              |
     *       |     r3      |      r4      |
     *       |             |              |
     *       +-------------+--------------+
     *    c7              c6                c5
     *
     *
     * <b>Algorithm</b>
     * Grid provided is divided into four regions r1 to r4. Each of the region
     * is considered independent on calculating color for a point.
     *
     * Sub-rectangle in which given point lies has four vertices, denoted by
     * Point topLeft, topRight, bottomLeft and bottomRight. Algorith then
     * calculates weighted mean of color corresponding to vertices (seperately
     * in x-axis and y-axis). Result of this calculation is returned as int.
     *
     * @param point Point to get color for
     * @return  Color corresponding to current point
     */


    private int getColorForPoint(Point point){
        int topLeftColor, topRightColor;
        int bottomLeftColor, bottomRightColor;

        Point topLeft, topRight;
        Point bottomLeft, bottomRight;

        // Following if..else identifies which sub-rectangle given point lies
        if (point.x < gridWidth/2 && point.y < gridHeight/2) {
            topLeftColor = colorPalette.get_c1(); // Seriously? underscore in Java method names! kriti--
            topRightColor = colorPalette.get_c2();
            bottomLeftColor = colorPalette.get_c8();
            bottomRightColor = colorPalette.get_c9();
        } else if (point.x >= gridWidth/2 && point.y < gridHeight/2) {
            topLeftColor = colorPalette.get_c2();
            topRightColor = colorPalette.get_c3();
            bottomLeftColor = colorPalette.get_c9();
            bottomRightColor = colorPalette.get_c4();
        } else if (point.x >= gridWidth/2 && point.y >= gridHeight/2) {
            topLeftColor = colorPalette.get_c9();
            topRightColor = colorPalette.get_c4();
            bottomLeftColor = colorPalette.get_c6();
            bottomRightColor = colorPalette.get_c5();
        } else {
            topLeftColor = colorPalette.get_c8();
            topRightColor = colorPalette.get_c9();
            bottomLeftColor = colorPalette.get_c7();
            bottomRightColor = colorPalette.get_c6();
        }

        // Calculate corners of sub rectangle in which point is identified
        topLeft = new Point(
                (point.x >= gridWidth/2) ? gridWidth/2 : 0,
                (point.y >= gridHeight/2) ? gridHeight/2 : 0);
        topRight = new Point(
                (point.x >= gridWidth/2) ? gridWidth : gridWidth/2,
                (point.y >= gridHeight/2) ? gridHeight/2 : 0);
        bottomLeft = new Point(
                (point.x >= gridWidth/2) ? gridWidth/2 : 0,
                (point.y >= gridHeight/2) ? gridHeight : gridHeight/2);
        bottomRight = new Point(
                (point.x >= gridWidth/2) ? gridWidth : gridWidth/2,
                (point.y >= gridHeight/2) ? gridHeight : gridHeight/2);

        // Calculates weighted mean of colors

        int weightedTopColor = (topRightColor*(point.x - topLeft.x)
                + (topLeftColor)*(topRight.x - point.x))
                / ((topRight.x - topLeft.x));
        int weightedBottomColor = (bottomRightColor*(point.x - topLeft.x)
                + (bottomLeftColor)*(topRight.x - point.x))
                / ((topRight.x - topLeft.x));

        int weightedLeftColor = (bottomLeftColor*(point.y - topLeft.y)
                + (topLeftColor)*(bottomLeft.y - point.y))
                / ((bottomLeft.y - topLeft.y));
        int weightedRightColor = (bottomRightColor*(point.y - topRight.y)
                + (topRightColor)*(bottomRight.y - point.y))
                / ((bottomRight.y - topRight.y));



        int weightedYColor = (weightedRightColor*(point.x - topLeft.x)
                + (weightedLeftColor)*(topRight.x - point.x))
                / ((topRight.x - topLeft.x));

        int weightedXColor = (weightedBottomColor*(point.y - topLeft.y)
                + (weightedTopColor)*(bottomLeft.y - point.y))
                / ((bottomLeft.y - topLeft.y));

        // This comment is self explanatory
        return avg(weightedXColor, weightedYColor);
    }

    /**
     * Calculates average of given numbers without using floating point
     * operations
     *
     * @param args Values to calculate average of
     * @return  Average of values provided
     */
    private int avg(int...args) {
        int sum = 0;
        for (int arg : args) {
            sum += arg;
        }
        return sum/args.length;
    }
}
