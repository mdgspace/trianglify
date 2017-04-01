package com.sdsmdg.kd.trianglify.models;

import com.sdsmdg.kd.trianglify.models.triangulator.Triangle2D;

import java.util.List;

/**
 * Created by shyam on 12-Mar-17.
 */

public class Triangulation {
    private List<Triangle2D> triangleList;

    public Triangulation(List<Triangle2D> triangleList){
        this.triangleList = triangleList;
    }

    public List<Triangle2D> getTriangleList() {
        return triangleList;
    }
}
