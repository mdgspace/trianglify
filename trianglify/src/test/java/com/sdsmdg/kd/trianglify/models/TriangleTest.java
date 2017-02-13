package com.sdsmdg.kd.trianglify.models;

import android.graphics.Point;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TriangleTest {

    Point p1, p2, p3, p4, p5;
    Triangle testTriangle;

    @Test
    public void isInCircumcircle_isCorrect() throws Exception {

        p1 = new FakePoint(0, 0);
        p2 = new FakePoint(0, 3);
        p3 = new FakePoint(4, 0);
        p4 = new FakePoint(1, 1);
        p5 = new FakePoint(9, 9);

        testTriangle = new Triangle(p1, p2, p3);

        assertTrue(testTriangle.isInCircumcircle(p4));
        assertFalse(testTriangle.isInCircumcircle(p5));
    }

    class FakePoint extends Point {
        FakePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
