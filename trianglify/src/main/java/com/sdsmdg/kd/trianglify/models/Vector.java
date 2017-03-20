package com.sdsmdg.kd.trianglify.models;

import android.graphics.Point;

/**
 * Created by shyam on 20-Mar-17.
 */

public class Vector {
    Point a;

    public Vector(Point a){
        this.a.x = a.x;
        this.a.y = a.y;
    }

    public Vector sub(Point point) {
        Point subPoint = new Point();
        subPoint.x = this.a.x - point.x;
        subPoint.y = this.a.y - point.y;
        return new Vector(subPoint);
    }

    public Vector add(Point point) {
        Point addPoint = new Point();
        addPoint.x = this.a.x + point.x;
        addPoint.y = this.a.y + point.y;
        return new Vector(addPoint);
    }

    public Vector mult(int scalar) {
        Point multPoint = new Point();
        multPoint.x = this.a.x * scalar;
        multPoint.y = this.a.y * scalar;
        return new Vector(multPoint);
    }

    public double mag() {
        return Math.sqrt(this.a.x * this.a.x + this.a.y * this.a.y);
    }

    public int dot(Point point) {
        return this.a.x * point.x + this.a.y * point.y;
    }

    public Point getPoint() {
        return a;
    }
}
