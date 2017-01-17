package com.sdsmdg.kd.trianglify;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class TrianglifyView extends View {

    public Paint mPaint = new Paint();

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // temporary background to check whether the view is being rendered
        setBackgroundColor(Color.parseColor("#FF000000"));
        mPaint.setColor(Color.parseColor("#FFFFFFFF"));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, 100, mPaint);
    }
}
