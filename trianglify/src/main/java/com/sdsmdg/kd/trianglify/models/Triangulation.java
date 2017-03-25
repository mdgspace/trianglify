package com.sdsmdg.kd.trianglify.models;

import java.util.List;

/**
 * Created by shyam on 12-Mar-17.
 */

public class Triangulation {
    private List<Triangle> triangleList;

    public Triangulation(List<Triangle> triangleList){
        this.triangleList = triangleList;
    }

    public List<Triangle> getTriangleList() {
        return triangleList;
    }
}
