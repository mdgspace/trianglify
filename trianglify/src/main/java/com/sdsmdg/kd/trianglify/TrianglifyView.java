package com.sdsmdg.kd.trianglify;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class TrianglifyView extends View implements TrianglifyViewInterface{
    int bleedX;
    int bleedY;
    int size;
    int TypeGrid;
    int variance;
    int scheme;
    int typeColor;
    int pattern;
    int triangulation;

    Presenter presenter;

    @Override
    public int getBleedX() {
        return bleedX;
    }

    public void setBleedX(int bleedX) {
        this.bleedX = bleedX;
    }

    @Override
    public int getBleedY() {
        return bleedY;
    }

    public void setBleedY(int bleedY) {
        this.bleedY = bleedY;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getTypeGrid() {
        return TypeGrid;
    }

    public void setTypeGrid(int typeGrid) {
        TypeGrid = typeGrid;
    }

    @Override
    public int getVariance() {
        return variance;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }

    @Override
    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }

    @Override
    public int getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(int typeColor) {
        this.typeColor = typeColor;
    }

    @Override
    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }

    @Override
    public int getTriangulation() {
        return triangulation;
    }

    public void setTriangulation(int triangulation) {
        this.triangulation = triangulation;
    }

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.presenter = new Presenter(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}