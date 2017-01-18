package com.sdsmdg.kd.trianglify.models;


import android.graphics.Point;

public class Triangle {
    public Point a;
    public Point b;
    public Point c;

    public Triangle (Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private boolean isClockwise (Point p1, Point p2, Point p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p1.y - p3.y) * (p2.x - p3.x) < 0f;
    }

    public boolean contains (Point p) {
        return isClockwise(p, a, b) == isClockwise(p, b, c) == isClockwise(p, c, a);
    }
}
