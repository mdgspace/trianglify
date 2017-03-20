package com.sdsmdg.kd.trianglify.models;

import android.graphics.Point;

/**
 * Created by shyam on 20-Mar-17.
 */

public class Edge {
    public Point a;
    public Point b;
    public double distance;

    public Edge(Point a , Point b){
        this(a, b, 0);
    }

    public Edge(Point a, Point b, double distance){
        this.a = a;
        this.b = b;
        this.distance = distance;
    }

}
