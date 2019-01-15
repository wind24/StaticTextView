package com.wind.statictextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 支持属性：
 * app:text      文本内容
 * app:textColor 文本颜色
 * app:textSize  文本尺寸
 */
public class StaticTextView extends View {

    private TextPaint paint;
    private StaticLayout staticLayout;
    private CharSequence label;

    public StaticTextView(Context context) {
        super(context);
        init(null);
    }

    public StaticTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StaticTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        int color = Color.BLACK;
        float fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        CharSequence label = null;
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.StaticTextView);
            color = array.getColor(R.styleable.StaticTextView_textColor, color);
            fontSize = array.getDimension(R.styleable.StaticTextView_textSize, fontSize);
            label = array.getText(R.styleable.StaticTextView_text);
            array.recycle();
        }
        paint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(fontSize);

        if (label != null) {
            setText(label);
        }
    }

    /**
     * 构建StaticLayout
     *
     * @param label
     * @param needRefreshLayout
     */
    private void buildLayout(CharSequence label, boolean needRefreshLayout) {
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        StaticLayout layout = new StaticLayout(label, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
        if (needRefreshLayout) {
            if (staticLayout != null) {
                int oldWidth = staticLayout.getWidth();
                int oldHeight = staticLayout.getHeight();
                staticLayout = layout;
            /*
            如果之前渲染过文字，并且两次前后的宽高度都一样，就直接重绘，不调用requestLayout。反之则调用requestLayout
             */
                if (oldWidth == layout.getWidth() && oldHeight == layout.getHeight()) {
                    Log.d("StaticTextView", "buildLayout 1 direct re draw");
                    invalidate();
                } else {
                    Log.d("StaticTextView", "buildLayout 1 requestLayout");
                    requestLayout();
                }
            } else {
                Log.d("StaticTextView", "buildLayout 2 requestLayout");
                requestLayout();
            }
        } else {
            Log.d("StaticTextView", "buildLayout 3");
            staticLayout = layout;
        }
    }

    /**
     * 设置文本
     * <p>
     * 1、如果文本为空，则将layout置为null，并requestLayout
     * 2、如果view之前已经绘制过，即宽度大于0，则先创建staticLayout，并且根据需要选择重新绘制或者requestLayout
     * 3、如果view没有绘制过，则requestLayout
     *
     * @param label
     */
    public void setText(CharSequence label) {
        this.label = label;
        if (label == null) {
            staticLayout = null;
            requestLayout();
        } else if (getMeasuredWidth() > 0) {
            buildLayout(label, true);
        } else {
            requestLayout();
        }
    }

    /**
     * 设置字体颜色，重绘
     *
     * @param color
     */
    public void setTextColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    /**
     * 设置字体大小，因为会改变布局大小，所以requestLayout
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        paint.setTextSize(textSize);
        requestLayout();
    }

    /**
     * 设置字体大小，因为会改变布局大小，所以requestLayout
     *
     * @param value
     * @param unit
     */
    public void setTextSize(float value, int unit) {
        float textSize = TypedValue.applyDimension(unit, value, getResources().getDisplayMetrics());
        setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (label != null) {
            buildLayout(label, false);
            int height = staticLayout.getHeight() + getPaddingTop() + getPaddingBottom();
            setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (staticLayout != null) {
            canvas.translate(getPaddingLeft(), getPaddingTop());
            canvas.save();
            staticLayout.draw(canvas);
            canvas.restore();
        }
    }
}
