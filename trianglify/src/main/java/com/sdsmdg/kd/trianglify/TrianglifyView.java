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

import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangle;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.models.triangulator.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.models.triangulator.NotEnoughPointsException;
import com.sdsmdg.kd.trianglify.models.triangulator.Triangle2D;
import com.sdsmdg.kd.trianglify.models.triangulator.Vector2D;
import com.sdsmdg.kd.trianglify.patterns.Patterns;

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
    Palette typeColor;
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
        Toast.makeText(getContext(), "TJos os adkfa", Toast.LENGTH_LONG).show();
        //generate();
        //plotOnCanvas(canvas);
        Vector<Vector2D> pointSet = new Vector<>();
        pointSet.add(new Vector2D(0,0));

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 10; j++){
                if (i != j)
                    pointSet.add(new Vector2D(i*100,j*100));
            }
        }

        DelaunayTriangulator triangulator = new DelaunayTriangulator(pointSet);
        try {
            triangulator.triangulate();
        } catch (NotEnoughPointsException e){
            e.printStackTrace();
        }

        List<Triangle2D> triangleSoup = (triangulator.getTriangles());

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);

        for (int i = 0; i < triangleSoup.size(); i++){
            drawTriangle(canvas, paint, triangleSoup.get(i));
        }

    }

    public void drawTriangle(Canvas canvas, Paint paint, Triangle2D triangle2D) {
        Path path = new Path();

        canvas.drawLine((float)triangle2D.a.x, (float)triangle2D.a.y,(float)triangle2D.b.x, (float)triangle2D.b.y, paint);
        canvas.drawLine((float)triangle2D.b.x, (float)triangle2D.b.y,(float)triangle2D.c.x, (float)triangle2D.c.y, paint);
        canvas.drawLine((float)triangle2D.c.x, (float)triangle2D.c.y,(float)triangle2D.a.x, (float)triangle2D.a.y, paint);

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

