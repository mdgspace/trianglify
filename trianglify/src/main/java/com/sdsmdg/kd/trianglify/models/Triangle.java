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

    private float sign (Point p1, Point p2, Point p3) {
        return (p1.x - p3.x) * (p2.y - p3.y) - (p1.y - p3.y) * (p2.x - p3.x);
    }

    public boolean contains (Point p) {
        boolean pab, pbc, pca;

        pab = sign(p, a, b) < 0f;
        pbc = sign(p, b, c) < 0f;

        if (pab == pbc)
            return false;

        pca = sign(p, c, a) < 0f;

        return (pab == pca);
    }
}
