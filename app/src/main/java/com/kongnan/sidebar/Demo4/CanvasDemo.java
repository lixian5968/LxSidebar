package com.kongnan.sidebar.Demo4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lixian on 2016/7/5.
 */
public class CanvasDemo extends View {

    public CanvasDemo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasDemo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CanvasDemo(Context context) {
        super(context);
        init();
    }

    Paint mPaint0;
    Paint mPaint;
    int mTextColor;
    int mTextColor0;

    public void init() {
        mPaint = new Paint();
        mTextColor = Color.GRAY;
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mTextColor);


        mPaint0 = new Paint();
        mTextColor0 = Color.WHITE;
        mPaint0.setAntiAlias(true);
        mPaint0.setTextAlign(Paint.Align.CENTER);
        mPaint0.setColor(mTextColor0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int px = getMeasuredWidth();
        int py = getMeasuredWidth();
//// Draw background
//        canvas.drawRect(0, 0, px, py, mPaint0);
//        canvas.save();
//        //可以看成坐标系（观看者） 从正面 转 到左面了
//        canvas.rotate(90, px / 2, py / 2);
//// Draw up arrow
//        canvas.drawLine(px / 2, 0, 0, py / 2, mPaint);
//        canvas.drawLine(px / 2, 0, px, py / 2, mPaint);
//        canvas.drawLine(px / 2, 0, px / 2, py, mPaint);
//// Draw circle
//        canvas.drawCircle(px - 10, py - 10, 10, mPaint);
//        canvas.restore();

        canvas.save();
        canvas.scale(2, 2, px / 2, py / 3);
        canvas.drawCircle(px / 2, py / 3, 10, mPaint);
        canvas.restore();

        canvas.drawCircle(px / 2, py / 3, 10, mPaint);

    }
}
