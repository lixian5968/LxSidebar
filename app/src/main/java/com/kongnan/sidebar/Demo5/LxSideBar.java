package com.kongnan.sidebar.Demo5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.kongnan.sidebar.R;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * Created by lixian on 2016/7/5.
 */
public class LxSideBar extends View {

    String[] mLetters = null;
    Context ct;
    float mDensity;


    public LxSideBar(Context context) {
        super(context);
        init(context);
    }

    public LxSideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LxSideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private int mChoose = -1;
    private float mY;
    private float mHalfWidth, mHalfHeight;
    private float mLetterHeight;
    private boolean mStartEndAnim;
    private float mAnimStep;
    private Paint mPaint;
    int mTextColor;


    private RectF mIsDownRect = new RectF();
    int mTouchSlop;

    public void init(Context ct) {
        this.ct = ct;
        this.mLetters = ct.getResources().getStringArray(R.array.letter_list);
        mDensity = getContext().getResources().getDisplayMetrics().density;
        setPadding(0, dip2px(20), 0, dip2px(20));
        this.mPaint = new Paint();
        this.mTextColor = Color.GRAY;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(this.mTextColor);

        mChoose = -1;
        mAnimStep = 0;
        mTouchSlop = ViewConfiguration.get(ct).getScaledTouchSlop();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfWidth = w - dip2px(16);
        mHalfHeight = h - getPaddingTop() - getPaddingBottom();
        float lettersLen = getLettersSize();
        mLetterHeight = mHalfHeight / lettersLen;
        int textSize = (int) (mHalfHeight * 0.7f / lettersLen);
        this.mPaint.setTextSize(textSize);

        mIsDownRect.set(w - dip2px(16 * 2), 0, w, h);
    }

    private int dip2px(int dipValue) {
        return (int) (dipValue * mDensity + 0.5f);
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float diff, diffY, diffX;
        for (int i = 0; i < getLettersSize(); i++) {
            float letterPosY = mLetterHeight * (i + 1) + getPaddingTop();
            canvas.save();
            diffX = 0f;
            diffY = 0f;
            if (mChoose > -1 && Math.abs(i - mChoose) < 5) {
//                diff = (float) (2.1f - Math.abs(i - mChoose) * 0.2) - mAnimStep;

                double t = (i - mChoose + 5) / 10.0;
                diff = (float) ((1 - t) * (1 - t) + 2 * t * (1 - t) * 2.2 + t * t );
                if (diff < 1f) {
                    diff = 1f;
                }
            } else {
                diff = 1f;
            }
            canvas.scale(diff, diff, mHalfWidth * 1.20f + diffX, letterPosY + diffY);
            if (diff == 1f) {
                this.mPaint.setAlpha(255);
                this.mPaint.setTypeface(Typeface.DEFAULT);
            } else {
                int alpha = (int) (255 * (1 - Math.min(0.9, diff - 1)));
                if (mChoose == i)
                    alpha = 255;
                this.mPaint.setAlpha(alpha);
                this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            }
            canvas.drawText(mLetters[i], mHalfWidth, letterPosY, this.mPaint);
            canvas.restore();
        }
        if (EndAnimation && mAnimStep < 0.7f) {
            mAnimStep += 0.6f;
            postInvalidateDelayed(50);
        } else {
            mAnimStep = 0;
            mStartEndAnim = false;
        }


    }

    private int getLettersSize() {
        return mLetters.length;
    }

    public void setCheck(int i) {
        mChoose = i;
        mAnimStep = 0;
        invalidate();
    }

    int c; // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数
    int mActivePointerId = INVALID_POINTER;
    boolean EndAnimation = false;
    float mInitialDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                final float initialDownY = getMotionEventY(event, mActivePointerId);
                if (initialDownY == -1) {
                    return false;
                }
                if (!mIsDownRect.contains(event.getX(), event.getY())) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                final float newy = getMotionEventY(event, mActivePointerId);
                if (newy == -1) {
                    return false;
                }
                final float yDiff = Math.abs(newy - mInitialDownY);
                //防止移动距离过短
                if (yDiff > mTouchSlop) {
                    float y = event.getY(); // 点钱手指所在的Y坐标
                    float endY = y - getPaddingTop() - getPaddingBottom();
                    c = (int) (endY / mHalfHeight * getLettersSize());
                    setCheck(c);
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_UP:
                Log.e("lx", "ACTION_UP");
                mOnTouchingLetterChangedListener.onTouchingLetterChanged(mLetters[c]);
                mChoose = -1;
                mAnimStep = 0f;
                invalidate();
                return false;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }


    public void setOnTextChangeListener(OnTouchingLetterChangedListener listener) {
        this.mOnTouchingLetterChangedListener = listener;
    }

    interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }

    private OnTouchingLetterChangedListener mOnTouchingLetterChangedListener;


}
