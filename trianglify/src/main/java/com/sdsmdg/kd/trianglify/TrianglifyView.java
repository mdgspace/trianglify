package com.sdsmdg.kd.trianglify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.models.triangulator.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.models.triangulator.NotEnoughPointsException;
import com.sdsmdg.kd.trianglify.models.triangulator.Triangle2D;
import com.sdsmdg.kd.trianglify.models.triangulator.Vector2D;
import com.sdsmdg.kd.trianglify.patterns.Patterns;
import com.sdsmdg.kd.trianglify.patterns.Rectangle;
import com.sdsmdg.kd.trianglify.utilities.colorizers.Colorizer;
import com.sdsmdg.kd.trianglify.utilities.colorizers.FixedPointsColorizer;

import java.util.List;
import java.util.Vector;

import static android.content.ContentValues.TAG;

public class TrianglifyView extends View implements TrianglifyViewInterface{
    int bleedX;
    int bleedY;
    int gridHeight;
    int gridWidth;
    int TypeGrid;
    int variance;
    int scheme;
    int cellSize;
    int triangulationType;
    Palette palette;
    Patterns pattern;
    Triangulation triangulation;

    Presenter presenter;

    @Override
    public int getTriangulationType() {
        return triangulationType;
    }

    public void setTriangulationType(int triangulationType) {
        this.triangulationType = triangulationType;
    }

    @Override
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
    public Palette  getPalette() {
        return palette;
    }

    public void setTypePalette(Palette palette) {
        this.palette = palette;
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

        Toast.makeText(getContext(), "TJos os adkfa", Toast.LENGTH_LONG).show();
        //generate();
        //plotOnCanvas(canvas);
        Vector<Vector2D> pointSet = new Vector<>();

        Patterns newPatterns = new Rectangle(50, 50, 1600, 800, 50, 20);
        List<Vector2D> newGrid = newPatterns.generate();

        DelaunayTriangulator triangulator = new DelaunayTriangulator(newGrid);

        try {
            triangulator.triangulate();
        } catch (NotEnoughPointsException e){
            e.printStackTrace();
        }

        Triangulation newTriangulation = new Triangulation(triangulator.getTriangles());

        Colorizer newColorizer = new FixedPointsColorizer(newTriangulation, Palette.GnBu, 1800, 1000);
        newTriangulation = newColorizer.getColororedTriangulation();
        for (int i = 0; i < newTriangulation.getTriangleList().size(); i++){
            drawTriangle(canvas, newTriangulation.getTriangleList().get(i));
        }

    }

    public void drawTriangle(Canvas canvas, Triangle2D triangle2D) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color = triangle2D.getColor();

        //Right shifts number by 8 bits (2 hex for alpha)
        color <<= 8;
        color += 255;

        paint.setColor(color);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        path.moveTo((float)triangle2D.a.x, (float)triangle2D.a.y);
        path.lineTo((float)triangle2D.b.x, (float)triangle2D.b.y);
        path.lineTo((float)triangle2D.c.x, (float)triangle2D.c.y);
        path.lineTo((float)triangle2D.a.x, (float)triangle2D.a.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    void plotOnCanvas(Canvas canvas) {
        if (triangulation != null) {
            for (Triangle2D triangle : triangulation.getTriangleList()) {
                Paint paint = new Paint();
                int fillColor = triangle.getColor();
                paint.setColor(fillColor);
                paint.setStrokeWidth(4);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
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