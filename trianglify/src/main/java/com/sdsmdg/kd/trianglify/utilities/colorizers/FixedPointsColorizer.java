package com.sdsmdg.kd.trianglify.utilities.colorizers;

import android.os.Debug;
import android.os.SystemClock;
import android.util.Log;

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.utilities.triangulator.Triangle2D;
import com.sdsmdg.kd.trianglify.utilities.triangulator.Vector2D;

import com.sdsmdg.kd.trianglify.utilities.ThreadLocalRandom;

import java.sql.Time;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * <h1>Fixed Point Colorizer</h1>
 * <b>Description :</b>
 * Fixed point colorizer contains methods that colorize triangles
 * based on the color palette provided in the constructor.
 *
 * @author suyash
 * @since 24/3/17.
 */

public class FixedPointsColorizer implements Colorizer {
    private ThreadLocalRandom random;
    private Triangulation triangulation;
    private Palette colorPalette;

    private int gridWidth;
    private int gridHeight;

    private Boolean randomColoring = false;

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
        this(triangulation, colorPalette, gridHeight, gridWidth, false);
    }

    public FixedPointsColorizer(Triangulation triangulation, Palette colorPalette,
                                int gridHeight, int gridWidth, Boolean randomColoring) {
        this.randomColoring = randomColoring;
        random = new ThreadLocalRandom();
        this.triangulation = triangulation;
        this.colorPalette = colorPalette;
        this.gridHeight = gridHeight;
        this.gridWidth = gridWidth;
    }

    @Override
    public Triangulation getColororedTriangulation(){
        if (triangulation != null){
            for (Triangle2D triangle : triangulation.getTriangleList()){
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

    // Sorry for such long method, here's a ASCII potato
    //          __
    //         /   \
    //        /  o  \
    //       |     o \
    //      / o      |
    //     /    o    |
    //     \__o___o__/
    //

    private int getColorForPoint(Vector2D point){
        if (randomColoring){
            return colorPalette.getColor(random.nextInt(9)+1);
        } else {

            ExtendedColor topLeftColor, topRightColor;
            ExtendedColor bottomLeftColor, bottomRightColor;

            Point topLeft, topRight;
            Point bottomLeft, bottomRight;

            // Following if..else identifies which sub-rectangle given point lies
            if (point.x < gridWidth/2 && point.y < gridHeight/2) {
                topLeftColor = new ExtendedColor(colorPalette.getC1());
                topRightColor = new ExtendedColor(colorPalette.getC2());
                bottomLeftColor = new ExtendedColor(colorPalette.getC8());
                bottomRightColor = new ExtendedColor(colorPalette.getC9());
            } else if (point.x >= gridWidth/2 && point.y < gridHeight/2) {
                topLeftColor = new ExtendedColor(colorPalette.getC2());
                topRightColor = new ExtendedColor(colorPalette.getC3());
                bottomLeftColor = new ExtendedColor(colorPalette.getC9());
                bottomRightColor = new ExtendedColor(colorPalette.getC4());
            } else if (point.x >= gridWidth/2 && point.y >= gridHeight/2) {
                topLeftColor = new ExtendedColor(colorPalette.getC9());
                topRightColor = new ExtendedColor(colorPalette.getC4());
                bottomLeftColor = new ExtendedColor(colorPalette.getC6());
                bottomRightColor = new ExtendedColor(colorPalette.getC5());
            } else {
                topLeftColor = new ExtendedColor(colorPalette.getC8());
                topRightColor = new ExtendedColor(colorPalette.getC9());
                bottomLeftColor = new ExtendedColor(colorPalette.getC7());
                bottomRightColor = new ExtendedColor(colorPalette.getC6());
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

            ExtendedColor weightedTopColor = new ExtendedColor(
                    (int)((topRightColor.r*(point.x - topLeft.x) + (topLeftColor.r)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x))),
                    (int)((topRightColor.g*(point.x - topLeft.x) + (topLeftColor.g)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x))),
                    (int)((topRightColor.b*(point.x - topLeft.x) + (topLeftColor.b)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x)))
            );
            ExtendedColor weightedBottomColor = new ExtendedColor(
                    (int) ((bottomRightColor.r*(point.x - topLeft.x) + (bottomLeftColor.r)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x))),
                    (int)((bottomRightColor.g*(point.x - topLeft.x) + (bottomLeftColor.g)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x))),
                    (int)((bottomRightColor.b*(point.x - topLeft.x) + (bottomLeftColor.b)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x)))

            );
            ExtendedColor weightedLeftColor = new ExtendedColor(
                    (int)((bottomLeftColor.r*(point.y - topLeft.y) + (topLeftColor.r)*(bottomLeft.y - point.y))
                            / ((bottomLeft.y - topLeft.y))),
                    (int)((bottomLeftColor.g*(point.y - topLeft.y) + (topLeftColor.g)*(bottomLeft.y - point.y))
                            / ((bottomLeft.y - topLeft.y))),
                    (int)((bottomLeftColor.b*(point.y - topLeft.y) + (topLeftColor.b)*(bottomLeft.y - point.y))
                            / ((bottomLeft.y - topLeft.y)))
            );

            ExtendedColor weightedRightColor = new ExtendedColor(
                    (int)((bottomRightColor.r*(point.y - topRight.y)
                            + (topRightColor.r)*(bottomRight.y - point.y))
                            / ((bottomRight.y - topRight.y))),
                    (int)((bottomRightColor.g*(point.y - topRight.y)
                            + (topRightColor.g)*(bottomRight.y - point.y))
                            / ((bottomRight.y - topRight.y))),
                    (int)((bottomRightColor.b*(point.y - topRight.y)
                            + (topRightColor.b)*(bottomRight.y - point.y))
                            / ((bottomRight.y - topRight.y)))
            );


            ExtendedColor weightedYColor = new ExtendedColor(
                    (int)((weightedRightColor.r*(point.x - topLeft.x)
                            + (weightedLeftColor.r)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x))),
                    (int)((weightedRightColor.g*(point.x - topLeft.x)
                            + (weightedLeftColor.g)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x))),
                    (int)((weightedRightColor.b*(point.x - topLeft.x)
                            + (weightedLeftColor.b)*(topRight.x - point.x))
                            / ((topRight.x - topLeft.x)))
            );


            ExtendedColor weightedXColor = new ExtendedColor(
                    (int)((weightedBottomColor.r*(point.y - topLeft.y)
                            + (weightedTopColor.r)*(bottomLeft.y - point.y))
                            / ((bottomLeft.y - topLeft.y))),
                    (int)((weightedBottomColor.g*(point.y - topLeft.y)
                            + (weightedTopColor.g)*(bottomLeft.y - point.y))
                            / ((bottomLeft.y - topLeft.y))),
                    (int)((weightedBottomColor.b*(point.y - topLeft.y)
                            + (weightedTopColor.b)*(bottomLeft.y - point.y))
                            / ((bottomLeft.y - topLeft.y)))

            );

            return ExtendedColor.avg(weightedXColor, weightedYColor).toInt();
        }
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
