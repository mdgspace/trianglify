package com.sdsmdg.kd.trianglify;

import android.graphics.Point;

import com.sdsmdg.kd.trianglify.models.Triangulation;

import java.util.List;

/**
 * Created by shyam on 19-Mar-17.
 */

public interface Triangulator {
    Triangulation generateTriangulation(List<Point> grid);
}
