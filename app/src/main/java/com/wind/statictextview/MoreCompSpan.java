package com.wind.statictextview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.View;

public class MoreCompSpan extends SpannableString {

    private OnMoreClickListener onMoreClickListener;

    private CharSequence source;
    private int fontColor = 0xffdddddd;
    private float textSize;
    private Context context;

    public MoreCompSpan(Context context, CharSequence source) {
        super(source);
        this.source = source;
        this.context = context;
        setSpan(new MoreClickSpan(), 0, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public CharSequence getSource() {
        return source;
    }

    /**
     * 展示更多的Span所占用的字符长度
     *
     * @return
     */
    public int getMoreLength() {
        return 5;
    }

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    protected float getTextSize() {
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, context.getResources().getDisplayMetrics());
        return textSize;
    }

    class MoreClickSpan extends ClickableSpan {

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(fontColor);
            ds.setTextSize(getTextSize());
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(@NonNull View view) {
            if (onMoreClickListener != null) {
                onMoreClickListener.onMoreClick();
            }
        }
    }

    interface OnMoreClickListener {
        void onMoreClick();
    }

}
