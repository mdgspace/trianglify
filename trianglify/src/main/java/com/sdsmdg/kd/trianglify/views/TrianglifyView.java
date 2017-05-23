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
    private int bleedX;
    private int bleedY;
    private int gridHeight;
    private int gridWidth;
    private int typeGrid;
    private int variance;
    private int cellSize;
    private int paletteNumber;
    private boolean fillTriangle;
    private boolean drawStroke;
    private boolean randomColoring;
    private Palette palette;
    private Triangulation triangulation;
    private Presenter presenter;

    /**
     *This variable is used to know whether the user wants the view to completely fill the passed gridHeight
     * and gridWidth. If it is TRUE, then it will throw an exception whenever either bleedX or bleedY are not
     * greater than the cellSize. If it is FALSE, then it will not check for the above condition, and hence will
     * not throw an exception.
     */
    private boolean fillViewCompletely;

    /**
     * Flag for keeping track of changes in attributes of the view. Helpful in increasing
     * performance by stopping unnecessary regeneration of triangulation. Look at smartUpdate method for more.
     * if triangulation is null then value is NULL_TRIANGULATION
     * if triangulation is unchanged then value is UNCHANGED_TRIANGULATION
     * if change in fillTriangle or drawStroke then value is PAINT_STYLE_CHANGED
     * if change in grid width, grid height, variance, bleedX, bleedY, typeGrid or cell size then value is GRID_PARAMETERS_CHANGED
     * if change in palette or random coloring then value is COLOR_SCHEME_CHANGED
     */
    private ViewState viewState = ViewState.NULL_TRIANGULATION;
    public enum ViewState {
        NULL_TRIANGULATION,
        UNCHANGED_TRIANGULATION,
        PAINT_STYLE_CHANGED,
        COLOR_SCHEME_CHANGED,
        GRID_PARAMETERS_CHANGED
    }

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrianglifyView, 0, 0);
        attributeSetter(a);
        this.presenter = new Presenter(this);
    }

    private void attributeSetter(TypedArray typedArray) {

        bleedX = (int) typedArray.getDimension(R.styleable.TrianglifyView_bleedX, 0);
        bleedY = (int) typedArray.getDimension(R.styleable.TrianglifyView_bleedY, 0);
        variance = (int) typedArray.getDimension(R.styleable.TrianglifyView_variance, 10);
        cellSize = (int) typedArray.getDimension(R.styleable.TrianglifyView_cellSize, 40);
        typeGrid = typedArray.getInt(R.styleable.TrianglifyView_gridType, 0);
        fillTriangle = typedArray.getBoolean(R.styleable.TrianglifyView_fillTriangle, true);
        drawStroke = typedArray.getBoolean(R.styleable.TrianglifyView_fillStrokes, false);
        paletteNumber = typedArray.getInt(R.styleable.TrianglifyView_palette, 0);
        palette = Palette.getPalette(paletteNumber);
        typeGrid = GRID_RECTANGLE;
        randomColoring = typedArray.getBoolean(R.styleable.TrianglifyView_randomColoring, false);
        fillViewCompletely = typedArray.getBoolean(R.styleable.TrianglifyView_fillViewCompletely, false);

        typedArray.recycle();

        if (fillViewCompletely) {
            checkViewFilledCompletely();
        }

    }

    @Override
    public int getBleedX() {
        return bleedX;
    }

    public TrianglifyView setBleedX(int bleedX) {
        this.bleedX = bleedX;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        if (fillViewCompletely) {
            checkViewFilledCompletely();
        }
        return this;
    }

    @Override
    public int getBleedY() {
        return bleedY;
    }

    public TrianglifyView setBleedY(int bleedY) {
        this.bleedY = bleedY;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        if (fillViewCompletely) {
            checkViewFilledCompletely();
        }
        return this;
    }

    @Override
    public int getGridHeight() {
        return gridHeight;
    }

    public TrianglifyView setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getGridWidth() {
        return gridWidth;
    }

    public TrianglifyView setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getTypeGrid() {
        return typeGrid;
    }

    public TrianglifyView setTypeGrid(int typeGrid) {
        this.typeGrid = typeGrid;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getVariance() {
        return variance;
    }

    public TrianglifyView setVariance(int variance) {
        this.variance = variance;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getCellSize() {
        return cellSize;
    }

    public TrianglifyView setCellSize(int cellSize) {
        this.cellSize = cellSize;
        this.viewState = ViewState.GRID_PARAMETERS_CHANGED;
        if (fillViewCompletely) {
            checkViewFilledCompletely();
        }
        return this;
    }

    @Override
    public void setFillViewCompletely(boolean fillViewCompletely) {
        this.fillViewCompletely = fillViewCompletely;
    }

    public boolean isFillViewCompletely() {
        return fillViewCompletely;
    }

    @Override
    public boolean isFillTriangle() {
        return fillTriangle;
    }

    public TrianglifyView setFillTriangle(boolean fillTriangle) {
        this.fillTriangle = fillTriangle;
        if (this.viewState != ViewState.GRID_PARAMETERS_CHANGED && this.viewState != ViewState.COLOR_SCHEME_CHANGED) {
            this.viewState = ViewState.PAINT_STYLE_CHANGED;
        }
        return this;
    }

    @Override
    public boolean isDrawStrokeEnabled() {
        return drawStroke;
    }

    public TrianglifyView setDrawStrokeEnabled(boolean drawStroke) {
        this.drawStroke = drawStroke;
        if (this.viewState != ViewState.GRID_PARAMETERS_CHANGED && this.viewState != ViewState.COLOR_SCHEME_CHANGED) {
            this.viewState = ViewState.PAINT_STYLE_CHANGED;
        }
        return this;
    }

    @Override
    public boolean isRandomColoringEnabled() {
        return randomColoring;
    }

    public TrianglifyView setRandomColoring(boolean randomColoring) {
        this.randomColoring = randomColoring;
        if (this.viewState != ViewState.GRID_PARAMETERS_CHANGED) {
            this.viewState = ViewState.COLOR_SCHEME_CHANGED;
        }
        return this;
    }

    @Override
    public Palette getPalette() {
        return palette;
    }

    public TrianglifyView setPalette(Palette palette) {
        this.palette = palette;
        if (this.viewState != ViewState.GRID_PARAMETERS_CHANGED) {
            this.viewState = ViewState.COLOR_SCHEME_CHANGED;
        }
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
    public void setViewState(ViewState viewState) {
        this.viewState = viewState;
    }

    @Override
    public ViewState getViewState() {
        return viewState;
    }


    /**
     * invalidateView method invalidates the view by setting
     * @param triangulation to the view instance triangulation and calling invalidate method.
     * Once invalidated, the viewState is changed to UNCHANGED_TRIANGULATION to denote no change in the triangulation
     * parameters after rendering the view.
     */
    @Override
    public void invalidateView(Triangulation triangulation) {
        this.setTriangulation(triangulation);
        invalidate();
        this.viewState = ViewState.UNCHANGED_TRIANGULATION;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gridHeight = getHeight();
        gridWidth = getWidth();
        if (triangulation != null) {
            plotOnCanvas(canvas);
        } else {
            generateAndInvalidate();
        }
    }

    /**
     * smartUpdate method ensures the increase in performance by generating only the necessary changes in triangulation.
     * According to the value of viewState, it makes the necessary method call.
     */
    public void smartUpdate() {
        presenter.updateView();
    }

    /**
     * generateAndInvalidate method is called when the triangulation is to be generated from scratch. It sets the
     * GenerateOnlyColor boolean of presenter to false so that when generateSoupAndInvalidateView is
     * called, the grid and delaunay triangulation is regenerated according to the new parameters, followed by
     * colorization and plotting of the triangulation onto the view.
     */
    public void generateAndInvalidate() {
        presenter.setGenerateOnlyColor(false);
        presenter.generateSoupAndInvalidateView();
    }

    private void plotOnCanvas(Canvas canvas) {
        for (int i = 0; i < triangulation.getTriangleList().size(); i++) {
            drawTriangle(canvas, triangulation.getTriangleList().get(i));
        }
    }

    /**
     * Draws triangle on the canvas object passed using the parameters of current view instance
     * @param canvas Canvas to paint on
     * @param triangle2D Triangle to draw on canvas
     */
    private void drawTriangle(Canvas canvas, Triangle2D triangle2D) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int color = triangle2D.getColor();

        /*
         * Add 0xff000000 for alpha channel required by android.graphics.Color
         */
        color += 0xff000000;

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

    /**
     * This method checks whether the view will be filled completely by testing if both bleedY and bleedX are
     * greater than cellSize. If not, then it throws an illegal argument exception.
     *
     * Explaination for Condition:
     * Bleed defines the dimensions of extra size that TrianglifyView view generates so that triangles
     * on the edge don't appear to be chopped off. In most of the cases min{bleedX, bleedY} > cellSize
     * would ensure that the view is completely filled.
     */

    private void checkViewFilledCompletely() {
        if (bleedY <= cellSize || bleedX <= cellSize) {
            throw new IllegalArgumentException("bleedY and bleedX should be larger than cellSize for view to be completely filled.");
        }
    }
}
