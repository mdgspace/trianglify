package com.sdsmdg.kd.trianglify.utilities.colorizers;

/**
 * <h1>Title</h1>
 * <b>Description :</b>
 * <p>
 *
 * @author suyash
 * @since 25/3/17.
 */

public class Point{
    public int x;
    public int y;

    public Point(){
        this(0, 0);
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Point subtract(Point a, Point b){
        return new Point(a.x - a.y, b.x - b.y);
    }

    public static Point add(Point a, Point b){
        return new Point(a.x + a.y, b.x + b.y);
    }

    /**
     * Calculates mid point of two given points using integer arithmetic
     * @param a First Point
     * @param b Second Point
     * @return Mid Point
     */
    public static Point midPoint(Point a, Point b){
        return new Point((a.x + a.y) / 2, (b.x + b.y) / 2);
    }
}
