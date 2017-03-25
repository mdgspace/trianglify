package com.sdsmdg.kd.trianglify.utilities.colorizers;


import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangle;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.utilities.Point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <h1>Title</h1>
 * <b>Description :</b>
 * <p>
 *
 * @author suyash
 * @since 25/3/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class ColorizerTest {
    Colorizer testColorizer;
    Triangulation mockTriangulation;
    List<Triangle> mockTriangleList;

    Point mMockPointA, mMockPointB, mMockPointC;

    @Test
    public void isColorizationForTriangulationAsExpected(){

        mockTriangleList = new ArrayList<>();

        // Centroid at : (50, 50)
        mockTriangleList.add(new Triangle(new Point(50,25),
                new Point(75,50),
                new Point(25,75)));
        // Centroid at : (25, 25)
        mockTriangleList.add(new Triangle(new Point(0,25),
                new Point(50,0),
                new Point(25,50)));

        mockTriangulation = new Triangulation(mockTriangleList);
        testColorizer = new FixedPointsColorizer(
                mockTriangulation, Palette.RdYlBu, 100, 100);

        int expectedColors[] = {Palette.RdYlBu.get_c9(),
            avg(Palette.RdYlBu.get_c1(), Palette.RdYlBu.get_c2(),
                    Palette.RdYlBu.get_c8(), Palette.RdYlBu.get_c9())};

        assertTrue(expectedColors.length == mockTriangleList.size());

        mockTriangulation = testColorizer.getColororedTriangulation();
        for (int i = 0; i < expectedColors.length; i++){
            assertTrue(mockTriangulation.getTriangleList().get(i).getColor()
                    == expectedColors[i]);
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
