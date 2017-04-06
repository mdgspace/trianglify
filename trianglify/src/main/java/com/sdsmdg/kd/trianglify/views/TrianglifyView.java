package com.sdsmdg.kd.trianglify.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
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
    boolean fillStroke;
    int paletteNumber;
    Palette palettesArray[] = {Palette.YlGn, Palette.YlGnBu, Palette.GnBu, Palette.BuGn, Palette.PuBuGn,
            Palette.PuBu, Palette.BuPu, Palette.RdPu, Palette.PuRd, Palette.OrRd, Palette.YlOrRd,
            Palette.YlOrBr, Palette.Purples, Palette.Blues, Palette.Greens, Palette.Oranges,
            Palette.Reds, Palette.Greys, Palette.PuOr, Palette.BrBG, Palette.PRGn, Palette.PiYG,
            Palette.RdBu, Palette.RdGy, Palette.RdYlBu, Palette.Spectral, Palette.RdYlGn};
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
            fillStroke = typedArray.getBoolean(R.styleable.TrianglifyView_fillStrokes, true);
            paletteNumber = typedArray.getInt(R.styleable.TrianglifyView_palette, 0);
            palette = palettesArray[paletteNumber];
        }finally {
            typedArray.recycle();
        }
    }

    @Override
    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
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
        return typeGrid;
    }

    public void setTypeGrid(int typeGrid) {
        typeGrid = typeGrid;
    }

    @Override
    public int getVariance() {
        return variance;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }

    @Override
    public int getPaletteNumber() {
        return paletteNumber;
    }

    public void setPaletteNumber(int paletteNumber) {
        this.paletteNumber = paletteNumber;
    }

    @Override
    public Triangulation getTriangulation() {
        return triangulation;
    }

    public void setTriangulation(Triangulation triangulation) {
        this.triangulation = triangulation;
    }

    @Override
    public boolean isFillTriangle() {
        return fillTriangle;
    }

    public void setFillTriangle(boolean fillTriangle) {
        this.fillTriangle = fillTriangle;
    }

    @Override
    public boolean isFillStroke() {
        return fillStroke;
    }

    public void setFillStroke(boolean fillStroke) {
        this.fillStroke = fillStroke;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        generateAndPlot(canvas);
    }

    void generateAndPlot(Canvas canvas) {
        generate();
        plotOnCanvas(canvas);
    }

    void generate() {
        this.triangulation = presenter.getSoup();
    }

    void plotOnCanvas(Canvas canvas) {
        for (int i = 0; i < triangulation.getTriangleList().size(); i++){
            drawTriangle(canvas, triangulation.getTriangleList().get(i));
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
        if (isFillTriangle() && isFillStroke()) {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        } else if (isFillTriangle()) {
            paint.setStyle(Paint.Style.FILL);
        } else if (isFillStroke()) {
            paint.setStyle(Paint.Style.STROKE);
        } else {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
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
}