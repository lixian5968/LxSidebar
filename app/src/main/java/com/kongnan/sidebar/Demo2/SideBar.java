package com.kongnan.sidebar.Demo2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.kongnan.sidebar.R;


public class SideBar extends View {
    private String[] indexNames = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint paint = new Paint(); // onDraw()中用到的绘制工具类对象
    private TextView indexViewer; // 显示当前选中的索引的放大版
    private int currentChoice = -1; // 当前选择的字母
    private OnIndexChoiceChangedListener listener; // 回调接口，用来监听选中索引该表时出发的事件

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取焦点改变背景颜色
        int totalWidth = getWidth(); // SideLayout控件的高度
        int totalHeight = getHeight(); // SideLayout控件的宽度
        int singleHeight = (totalHeight - 5) / indexNames.length; // 每一个字母的高度
        for (int i = 0; i < indexNames.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            // 选中的状态
            if (i == currentChoice) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            float xPos = totalWidth / 2 - paint.measureText(indexNames[i]) / 2; // x坐标等于中间-字符串宽度的一半
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(indexNames[i], xPos, yPos, paint);
            paint.reset(); // 重置画笔
//            invalidate();
        }
    }

    @Override // 为自定义控件设置触摸事件
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY(); // 点钱手指所在的Y坐标
        final int lastChoice = currentChoice;
        Log.e("lxTouch",currentChoice+"");
        final OnIndexChoiceChangedListener listener = this.listener;
        final int c = (int) (y / getHeight() * indexNames.length); // 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数
        switch (action) {
            case MotionEvent.ACTION_UP: // 抬起手指
                setBackgroundColor(Color.parseColor("#00000000")); // 背景设置为透明
                currentChoice = -1;
                invalidate(); // 实时更新视图绘制
                if (indexViewer != null) {
                    indexViewer.setVisibility(View.INVISIBLE); // 隐藏放大的索引
                }
                Log.e("lxACTION_UP",currentChoice+"");
                break;
            default: // 按下手指或手指滑动
                setBackgroundResource(R.drawable.sidebar_background);
                if (lastChoice != c) { // 选中的索引改变时
                    if (c >= 0 && c < indexNames.length) {
                        if (listener != null) {
                            listener.onIndexChoiceChanged(indexNames[c]);
                        }
                        if (indexViewer != null) {
                            indexViewer.setText(indexNames[c]);
                            indexViewer.setVisibility(View.VISIBLE);
                        }
                        currentChoice = c;
                        invalidate();
                    }
                }
                Log.e("lxDefault",currentChoice+"");
                break;
        }
        return true;
    }

    public void setIndexViewer(TextView indexViewer) {
        this.indexViewer = indexViewer;
    }

    public void setOnIndexChoiceChangedListener(OnIndexChoiceChangedListener listener) {
        this.listener = listener;
    }

    /**
     * 回调接口，用来监听选中索引该表时出发的事件
     */
    public interface OnIndexChoiceChangedListener {
        void onIndexChoiceChanged(String s);
    }
}