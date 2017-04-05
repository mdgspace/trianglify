package com.sdsmdg.kd.trianglify.utilities.triangulator;

/**
 * Exception thrown by the Delaunay triangulator when it is initialized with
 * less than three points.
 * 
 * @author Johannes Diemke
 */
public class NotEnoughPointsException extends Exception {

    private static final long serialVersionUID = 7061712854155625067L;

    public NotEnoughPointsException() {
    }

    public NotEnoughPointsException(String s) {
        super(s);
    }

}