package com.sdsmdg.kd.trianglify.generators;

import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.Triangulation;

/**
 * Created by shyam on 12-Mar-17.
 */

public interface Triangulator  {

    public void setGrid(Grid grid);

    public Triangulation getTriangulation();

}
