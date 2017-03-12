package com.sdsmdg.kd.trianglify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.sdsmdg.kd.trianglify.generators.DelaunayTriangulator;
import com.sdsmdg.kd.trianglify.generators.GridGenerator;
import com.sdsmdg.kd.trianglify.models.Grid;
import com.sdsmdg.kd.trianglify.models.Triangle;
import com.sdsmdg.kd.trianglify.models.Triangulation;


public class TrianglifyView extends View {

    public Paint mPaint = new Paint();
    public GridGenerator gridGenerator = new GridGenerator();
    Grid grid;
    DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator();
    Triangulation triangulation;

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // temporary background to check whether the view is being rendered
        setBackgroundColor(Color.parseColor("#FF000000"));
        mPaint.setColor(Color.parseColor("#66FFFFFF"));
    }


    @Override
    protected void onDraw(Canvas canvas) {

        grid = gridGenerator.generateGrid(getMeasuredWidth(), getMeasuredHeight(), 60, 30);
        delaunayTriangulator.getGrid(grid);

        //TODO call the methods to generate triangulation here (after the other two are done)

        triangulation = delaunayTriangulator.getTriangulation();

        //TODO pass this triangulation to renderer, and set the color (data type int) of each triangle in the list (Kriti)

        //draw the triangulation on the view
        for (Triangle triangle : triangulation.getTriangleList()) {
            Paint paint = new Paint();
            int fillColor = triangle.getColor();
            paint.setColor(fillColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            Path path = new Path();
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(triangle.b.x, triangle.b.y);
            path.lineTo(triangle.c.x, triangle.c.y);
            path.lineTo(triangle.a.x, triangle.a.y);
            path.close();

            canvas.drawPath(path, paint);
        }
    }
}
