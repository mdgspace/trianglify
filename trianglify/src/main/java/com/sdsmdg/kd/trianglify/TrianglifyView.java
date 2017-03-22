package com.sdsmdg.kd.trianglify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangle;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.patterns.Patterns;

public class TrianglifyView extends View implements TrianglifyViewInterface{
    int bleedX;
    int bleedY;
    int gridHeight;
    int gridWidth;
    int TypeGrid;
    int variance;
    int scheme;
    int cellSize;
    Palette typeColor;
    Patterns pattern;
    Triangulation triangulation;

    Presenter presenter;

    final public static int GRID_RECTANGLE = 0;
    final public static int GRID_CIRCLE = 1;

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    @Override
    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    @Override
    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

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
    public Palette  getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(Palette typeColor) {
        this.typeColor = typeColor;
    }

    @Override
    public Patterns getPattern() {
        return pattern;
    }

    public void setPattern(Patterns pattern) {
        this.pattern = pattern;
    }

    @Override
    public Triangulation getTriangulation() {
        return triangulation;
    }

    public void setTriangulation(Triangulation triangulation) {
        this.triangulation = triangulation;
    }

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.presenter = new Presenter(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        generateAndPlot(canvas);
    }

    void generate() {
        presenter.generateSoup();
        this.triangulation = presenter.getSoup();
    }

    void generateAndPlot(Canvas canvas) {
        generate();
        plotOnCanvas(canvas);
    }

    void plotOnCanvas(Canvas canvas) {
        if (triangulation != null) {
            for (Triangle triangle : triangulation.getTriangleList()) {
                Paint paint = new Paint();
                int fillColor = triangle.getColor();
                paint.setColor(fillColor);
                paint.setStyle(Paint.Style.FILL);
                paint.setAntiAlias(true);

                Path path = new Path();
                path.setFillType(Path.FillType.EVEN_ODD);
                path.moveTo(triangle.b.x, triangle.b.y);
                path.moveTo(triangle.b.x, triangle.b.y);
                path.moveTo(triangle.c.x, triangle.c.y);
                path.close();

                canvas.drawPath(path, paint);
            }
        }
    }
}

