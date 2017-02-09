package com.sdsmdg.kd.trianglify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.sdsmdg.kd.trianglify.generators.GridGenerator;


public class TrianglifyView extends View {

    public Paint mPaint = new Paint();
    public GridGenerator gridGenerator = new GridGenerator();

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // temporary background to check whether the view is being rendered
        setBackgroundColor(Color.parseColor("#FF000000"));
        mPaint.setColor(Color.parseColor("#66FFFFFF"));
    }


    @Override
    protected void onDraw(Canvas canvas) {

        gridGenerator.generateGrid(getMeasuredWidth(), getMeasuredHeight(), 60, 30);

        for (Point p : gridGenerator.grid) {
            canvas.drawCircle(p.x, p.y, 10, mPaint);
        }
    }
}
