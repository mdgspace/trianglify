package com.sdsmdg.kd.trianglify.models;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shyam on 12-Mar-17.
 */

public class Triangulation {
    private List<Triangle> triangleList;

    public Triangulation(){
    }

    public List<Triangle> getTriangleList() {
        return triangleList;
    }

    public void add(Triangle triangle){
        this.triangleList.add(triangle);
    }

    public void remove(Triangle triangle){
        this.triangleList.remove(triangle);
    }

    public Triangle findContainingTriangle(Point point) {
        for (Triangle triangle : triangleList) {
            if (triangle.contains(point)) {
                return triangle;
            }
        }
        return null;
    }

    public Triangle findNeighbour(Triangle triangle, Edge edge) {
        for (Triangle triangleInSoup : triangleList) {
            if (triangleInSoup.isNeighbour(edge) && triangleInSoup != triangle) {
                return triangleInSoup;
            }
        }
        return null;
    }

    public Triangle findOneTriangleSharing(Edge edge) {
        for (Triangle triangle : triangleList) {
            if (triangle.isNeighbour(edge)) {
                return triangle;
            }
        }
        return null;
    }

    public Edge findNearestEdge(Point point) {
        List<Edge> edgeList = new ArrayList<Edge>();

        for (Triangle triangle : triangleList) {
            edgeList.add(triangle.findNearestEdge(point));
        }

        Edge[] edgeArray = new Edge[edgeList.size()];
        edgeList.toArray(edgeArray);

        Arrays.sort(edgeArray);
        return edgeArray[0];
    }

    public void removeTrianglesUsing(Point vertex) {
        List<Triangle> trianglesToBeRemoved = new ArrayList<Triangle>();

        for (Triangle triangle : triangleList) {
            if (triangle.hasVertex(vertex)) {
                trianglesToBeRemoved.add(triangle);
            }
        }

        triangleList.removeAll(trianglesToBeRemoved);
    }
}
