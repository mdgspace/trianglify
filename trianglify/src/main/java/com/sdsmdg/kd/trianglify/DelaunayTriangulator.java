package com.sdsmdg.kd.trianglify;

import android.graphics.Point;
import android.util.Log;
import android.widget.Toast;

import com.sdsmdg.kd.trianglify.models.Edge;
import com.sdsmdg.kd.trianglify.models.Triangle;
import com.sdsmdg.kd.trianglify.models.Triangulation;

import java.util.List;

/**
 * Created by shyam on 19-Mar-17.
 */

public class DelaunayTriangulator implements Triangulator {
    private Triangulation triangulation;

    @Override
    public Triangulation generateTriangulation(List<Point> grid) {
        triangulation = new Triangulation();
        int maxOfAnyCoordinate = 0;

        if(grid.size() < 3){
            //TODO throw exception for grid size less than 3
        }

        for (Point point : grid) {
            maxOfAnyCoordinate = Math.max(Math.max(point.x, point.y), maxOfAnyCoordinate);
        }

        maxOfAnyCoordinate *= 16;

        Point p1 = new Point(0, 3 * maxOfAnyCoordinate);
        Point p2 = new Point(3* maxOfAnyCoordinate, 0);
        Point p3 = new Point(-3 * maxOfAnyCoordinate, -3 * maxOfAnyCoordinate);

        Triangle superTriangle = new Triangle(p1, p2, p3);

        triangulation.add(superTriangle);

        for (int i = 0; i < grid.size(); i++) {
            Triangle triangle = triangulation.findContainingTriangle(grid.get(i));

            if (triangle == null) {
                Edge edge = triangulation.findNearestEdge(grid.get(i));

                Triangle first = triangulation.findOneTriangleSharing(edge);
                Triangle second = triangulation.findNeighbour(first, edge);

                Point firstNoneEdgeVertex = first.getNoneEdgeVertex(edge);
                Point secondNoneEdgeVertex = second.getNoneEdgeVertex(edge);

                triangulation.remove(first);
                triangulation.remove(second);

                Triangle triangle1 = new Triangle(edge.a, firstNoneEdgeVertex, grid.get(i));
                Triangle triangle2 = new Triangle(edge.b, firstNoneEdgeVertex, grid.get(i));
                Triangle triangle3 = new Triangle(edge.a, secondNoneEdgeVertex, grid.get(i));
                Triangle triangle4 = new Triangle(edge.b, secondNoneEdgeVertex, grid.get(i));

                triangulation.add(triangle1);
                triangulation.add(triangle2);
                triangulation.add(triangle3);
                triangulation.add(triangle4);

                legalizeEdge(triangle1, new Edge(edge.a, firstNoneEdgeVertex), grid.get(i));
                legalizeEdge(triangle2, new Edge(edge.b, firstNoneEdgeVertex), grid.get(i));
                legalizeEdge(triangle3, new Edge(edge.a, secondNoneEdgeVertex), grid.get(i));
                legalizeEdge(triangle4, new Edge(edge.b, secondNoneEdgeVertex), grid.get(i));
            } else {

                Point a = triangle.a;
                Point b = triangle.b;
                Point c = triangle.c;

                triangulation.remove(triangle);

                Triangle first = new Triangle(a, b, grid.get(i));
                Triangle second = new Triangle(b, c, grid.get(i));
                Triangle third = new Triangle(c, a, grid.get(i));

                triangulation.add(first);
                triangulation.add(second);
                triangulation.add(third);

                legalizeEdge(first, new Edge(a, b), grid.get(i));
                legalizeEdge(second, new Edge(b, c), grid.get(i));
                legalizeEdge(third, new Edge(c, a), grid.get(i));
            }
        }

        triangulation.removeTrianglesUsing(superTriangle.a);
        triangulation.removeTrianglesUsing(superTriangle.b);
        triangulation.removeTrianglesUsing(superTriangle.c);

        return triangulation;
    }

    private void legalizeEdge(Triangle triangle, Edge edge, Point newVertex) {
        Triangle neighbourTriangle = triangulation.findNeighbour(triangle, edge);

        if (neighbourTriangle != null) {
            if (neighbourTriangle.isInCircumcircle(newVertex)) {
                triangulation.remove(triangle);
                triangulation.remove(neighbourTriangle);

                Point noneEdgeVertex = neighbourTriangle.getNoneEdgeVertex(edge);

                Triangle firstTriangle = new Triangle(noneEdgeVertex, edge.a, newVertex);
                Triangle secondTriangle = new Triangle(noneEdgeVertex, edge.b, newVertex);

                triangulation.add(firstTriangle);
                triangulation.add(secondTriangle);

                legalizeEdge(firstTriangle, new Edge(noneEdgeVertex, edge.a), newVertex);
                legalizeEdge(secondTriangle, new Edge(noneEdgeVertex, edge.b), newVertex);
            }
        }
    }

}
