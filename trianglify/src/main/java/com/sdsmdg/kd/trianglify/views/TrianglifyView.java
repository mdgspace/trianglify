package com.sdsmdg.kd.trianglify.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

import com.sdsmdg.kd.trianglify.presenters.Presenter;
import com.sdsmdg.kd.trianglify.R;
import com.sdsmdg.kd.trianglify.models.Palette;
import com.sdsmdg.kd.trianglify.models.Triangulation;
import com.sdsmdg.kd.trianglify.utilities.triangulator.Triangle2D;

public class TrianglifyView extends View implements TrianglifyViewInterface{
    int bleedX;
    int bleedY;
    int gridHeight;
    int gridWidth;
    int typeGrid;
    int variance;
    int cellSize;

    boolean fillTriangle;
    boolean drawStroke;
    boolean randomColoring;

    int paletteNumber;
    Palette palette;

    Triangulation triangulation;
    Presenter presenter;

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrianglifyView, 0, 0);
        attributeSetter(a);
        this.presenter = new Presenter(this);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        gridWidth = width;
        gridHeight =height;
    }

    public void attributeSetter(TypedArray typedArray){
        try{
            bleedX = (int) typedArray.getDimension(R.styleable.TrianglifyView_bleedX, 0);
            bleedY = (int) typedArray.getDimension(R.styleable.TrianglifyView_bleedY, 0);
            variance = (int) typedArray.getDimension(R.styleable.TrianglifyView_variance, 10);
            cellSize = (int) typedArray.getDimension(R.styleable.TrianglifyView_cellSize, 40);
            typeGrid = typedArray.getInt(R.styleable.TrianglifyView_gridType, 0);
            fillTriangle = typedArray.getBoolean(R.styleable.TrianglifyView_fillTriangle, true);
            drawStroke = typedArray.getBoolean(R.styleable.TrianglifyView_fillStrokes, false);
            paletteNumber = typedArray.getInt(R.styleable.TrianglifyView_palette, 0);
            palette = Palette.values()[paletteNumber];
            typeGrid = GRID_RECTANGLE;
            randomColoring = typedArray.getBoolean(R.styleable.TrianglifyView_randomColoring, false);
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    public Palette getPalette() {
        return palette;
    }

    public TrianglifyView setPalette(Palette palette) {
        this.palette = palette;
        return this;
    }

    @Override
    public int getCellSize() {
        return cellSize;
    }

    public TrianglifyView setCellSize(int cellSize) {
        this.cellSize = cellSize;
        return this;
    }

    @Override
    public int getGridHeight() {
        return gridHeight;
    }

    public TrianglifyView setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
        return this;
    }

    @Override
    public int getGridWidth() {
        return gridWidth;
    }

    public TrianglifyView setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
        return this;
    }

    @Override
    public int getBleedX() {
        return bleedX;
    }

    public TrianglifyView setBleedX(int bleedX) {
        this.bleedX = bleedX;
        return this;
    }

    @Override
    public int getBleedY() {
        return bleedY;
    }

    public TrianglifyView setBleedY(int bleedY) {
        this.bleedY = bleedY;
        return this;
    }

    @Override
    public int getTypeGrid() {
        return typeGrid;
    }

    public TrianglifyView setTypeGrid(int typeGrid) {
        typeGrid = typeGrid;
        return this;
    }

    @Override
    public int getVariance() {
        return variance;
    }

    public TrianglifyView setVariance(int variance) {
        this.variance = variance;
        return this;
    }

    @Override
    public int getPaletteNumber() {
        return paletteNumber;
    }

    public TrianglifyView setPaletteNumber(int paletteNumber) {
        this.paletteNumber = paletteNumber;
        return this;
    }

    @Override
    public Triangulation getTriangulation() {
        return triangulation;
    }

    public TrianglifyView setTriangulation(Triangulation triangulation) {
        this.triangulation = triangulation;
        return this;
    }

    @Override
    public boolean isFillTriangle() {
        return fillTriangle;
    }

    public TrianglifyView setFillTriangle(boolean fillTriangle) {
        this.fillTriangle = fillTriangle;
        return this;
    }

    @Override
    public boolean isDrawStrokeEnabled() {
        return drawStroke;
    }

    public TrianglifyView setDrawStrokeEnabled(boolean drawStroke) {
        this.drawStroke = drawStroke;
        return this;
    }

    @Override
    public boolean isRandomColoringEnabled() {
        return randomColoring;
    }

    public TrianglifyView setRandomColoring(boolean randomColoring) {
        this.randomColoring = randomColoring;
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gridHeight = getHeight();
        gridWidth = getWidth();
        if(this.triangulation!=null) {
            plotOnCanvas(canvas);
        }
        else {
            generateAndInvalidate();
        }
    }
    public void generateAndInvalidate(){
        presenter.generateSoupAndInvalidateView();
    }

/*
    void generateAndPlot(Canvas canvas) {
        generate();
        plotOnCanvas(canvas);
    }

    void generate() {
        this.triangulation = presenter.getSoup();
    }*/

    void plotOnCanvas(Canvas canvas) {
        for (int i = 0; i < triangulation.getTriangleList().size(); i++){
            drawTriangle(canvas, triangulation.getTriangleList().get(i));
        }
    }

    /**
     * Draws triangle on the canvas object passed using the parameters of current view instance
     * @param canvas Canvas to paint on
     * @param triangle2D Triangle to draw on canvas
     */
    public void drawTriangle(Canvas canvas, Triangle2D triangle2D) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color = triangle2D.getColor();

        /*
         * Right shifts number by 8 bits (2 hex for alpha) since color received in triangle2D
         * is without alpha channel.
         */
        color <<= 8;
        color += 255;

        paint.setColor(color);
        paint.setStrokeWidth(4);
        if (isFillTriangle() && isDrawStrokeEnabled()) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        } else if (isFillTriangle()) {
            paint.setStyle(Paint.Style.FILL);
        } else if (isDrawStrokeEnabled()) {
            paint.setStyle(Paint.Style.STROKE);
        } else {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        paint.setAntiAlias(true);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        path.moveTo(triangle2D.a.x - bleedX, triangle2D.a.y - bleedY);
        path.lineTo(triangle2D.b.x - bleedX, triangle2D.b.y - bleedY);
        path.lineTo(triangle2D.c.x - bleedX, triangle2D.c.y - bleedY);
        path.lineTo(triangle2D.a.x - bleedX, triangle2D.a.y - bleedY);
        path.close();

        canvas.drawPath(path, paint);
    }
}