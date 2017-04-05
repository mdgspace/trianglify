//package com.sdsmdg.kd.trianglify.models;
//
//
//
//import com.sdsmdg.kd.trianglify.utilities.Point;
//import org.junit.Test;
//
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class TriangleTest {
//
//    private Point p1, p2, p3, p4, p5;
//    private Triangle testTriangle;
//
//    @Test
//    public void isInCircumcircle_isCorrect() throws Exception {
//
//        p1 = new Point(0, 0);
//        p2 = new Point(0, 3);
//        p3 = new Point(4, 0);
//        p4 = new Point(1, 1);
//        p5 = new Point(9, 9);
//
//        testTriangle = new Triangle(p1, p2, p3);
//
//        assertTrue(testTriangle.isInCircumcircle(p4));
//        assertFalse(testTriangle.isInCircumcircle(p5));
//    }
//
//    @Test
//    public void isClockwise_isCorrect() throws Exception {
//
//        p1 = new Point(1, 1);
//        p2 = new Point(1, 2);
//        p3 = new Point(2, 2);
//
//        testTriangle = new Triangle(p1, p2, p3);
//
//        assertTrue(testTriangle.isClockwise(p1, p2, p3));
//        assertFalse(testTriangle.isClockwise(p3, p2, p1));
//    }
//
//    @Test
//    public void contains_isCorrect() throws Exception {
//
//        p1 = new Point(0, 0);
//        p2 = new Point(0, 3);
//        p3 = new Point(4, 0);
//        p4 = new Point(1, 1);
//        p5 = new Point(9, 9);
//
//        testTriangle = new Triangle(p1, p2, p3);
//
//        assertTrue(testTriangle.contains(p4));
//        assertFalse(testTriangle.contains(p5));
//    }
//
//}
