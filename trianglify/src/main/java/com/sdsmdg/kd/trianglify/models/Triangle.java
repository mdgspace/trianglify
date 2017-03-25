package com.sdsmdg.kd.trianglify.models;


import android.graphics.Color;
import com.sdsmdg.kd.trianglify.utilities.Point;;

public class Triangle {
    public Point a;
    public Point b;
    public Point c;
    private int color;

    public Triangle (Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public boolean isClockwise (Point p1, Point p2, Point p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p1.y - p3.y) * (p2.x - p3.x) < 0;
    }

    public boolean contains (Point p) {
        return isClockwise(p, a, b) == isClockwise(p, b, c) == isClockwise(p, c, a);
    }

    public boolean isInCircumcircle (Point p) {
        int d11 = a.x - p.x;
        int d21 = b.x - p.x;
        int d31 = c.x - p.x;

        int d12 = a.y - p.y;
        int d22 = b.y - p.y;
        int d32 = c.y - p.y;

        int d13 = d11 * d11 + d12 * d12;
        int d23 = d21 * d21 + d22 * d22;
        int d33 = d31 * d31 + d32 * d32;

        int determinant = d11 * (d22 * d33 - d32 * d23)
                        - d12 * (d21 * d33 - d31 * d23)
                        + d13 * (d21 * d32 - d31 * d22);

        return isClockwise(a, b, c) ? determinant < 0 : determinant > 0;
    }

    public Point getCentroid(){
        Point centroid = new Point(0,0);
        centroid.x = ((a.x) + (b.x) + (c.x))/3;
        centroid.y = ((a.y) + (b.y) + (c.y))/3;
        return centroid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
