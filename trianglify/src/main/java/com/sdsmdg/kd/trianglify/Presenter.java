package com.sdsmdg.kd.trianglify;


import android.graphics.Point;
import com.sdsmdg.kd.trianglify.models.Triangulation;

import java.util.List;
import java.util.Vector;


/**
 * Created by suyash on 18/3/17.
 */

public class Presenter {
    TrianglifyViewInterface view;

    public Presenter(TrianglifyViewInterface view) {
        this.view = view;
    }

    private List<Point> generateGrid() {
        //TODO generates Grid by querying presenter.getTypeGrid()
        return null;
    }

    private Triangulation generateTriangulation(List<Point> inputGrid) {
        //TODO generate Triangulation from inputGrid based on presenter.getTriangulation()
        return null;
    }

    private Triangulation generateColoredSoup() {
        //TODO generates colored soup using presenter.getPallete()
        return null;
    }

    public void generateSoup() {
        //TODO Call generateGrid(), generateTriangulation(), generateColoredSoup()
    }

    public Triangulation getSoup() {
        //TODO Implement returns generated soup
        return null;
    }

    public void clearSoup() {
        //TODO sets local soup variable to null
    }
}
