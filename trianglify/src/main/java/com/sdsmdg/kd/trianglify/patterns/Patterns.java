package com.sdsmdg.kd.trianglify.patterns;



import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.triangulator.Vector2D;

import android.graphics.Point;

import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * Created by suyash on 17/3/17.
 */


public interface Patterns {
    public List<Vector2D> generate();
}
