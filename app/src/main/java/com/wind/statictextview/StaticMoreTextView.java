package com.wind.statictextview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.util.AttributeSet;
import android.util.Log;

public class StaticMoreTextView extends StaticTextView {

    private final static int MAX_SHOW_LINES = 5;

    private boolean inMiniState = true;
    private MoreCompSpan customMoreSpan;

    public StaticMoreTextView(Context context) {
        super(context);
        init();
    }

    public StaticMoreTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StaticMoreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    public void setInMiniState(boolean inMiniState) {
        this.inMiniState = inMiniState;
        requestLayout();
    }

    public void setCustomMoreSpan(MoreCompSpan customMoreSpan) {
        this.customMoreSpan = customMoreSpan;
    }

    public MoreCompSpan getMoreSpan() {
        if (customMoreSpan == null) {
            customMoreSpan = new MoreCompSpan(getContext(), " 展示更多");
            customMoreSpan.setOnMoreClickListener(moreClickListener);
        }

        return customMoreSpan;
    }

    @Override
    protected CharSequence onPreDraw(StaticLayout layout) {
        super.onPreDraw(layout);
        Log.d("StaticMoreTextView", "onPreDraw line:" + layout.getLineCount());
        if (inMiniState) {
            if (layout.getLineCount() > MAX_SHOW_LINES) {
                MoreCompSpan moreSpan = getMoreSpan();
                int index = layout.getLineEnd(MAX_SHOW_LINES - 1);
                CharSequence label = layout.getText();
                /*
                 *一行的宽度减去占位符的宽度剩下的宽度如果比当行文字的宽度小，则需要裁剪掉当行的文字来放下more的span
                 */
                float moreWidth = layout.getPaint().measureText(moreSpan.getSource().toString());
                float freeSpace = layout.getWidth() - moreWidth;
                float lineSpace = layout.getLineRight(MAX_SHOW_LINES - 1);
                boolean needSubSource = freeSpace < lineSpace;
                if (needSubSource) {
                    label = label.subSequence(0, index - moreSpan.getMoreLength() - 1);
                } else {
                    label = label.subSequence(0, index - 1);
                }
                SpannableStringBuilder builder = new SpannableStringBuilder(label);
                builder.append("...");
                builder.append(moreSpan);
                return builder;
            }
        }
        return null;
    }


    private MoreCompSpan.OnMoreClickListener moreClickListener = new MoreCompSpan.OnMoreClickListener() {
        @Override
        public void onMoreClick() {
            setInMiniState(false);
        }
    };
}
