package com.wind.statictextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合显示span的textView，组成部分是：MoreSpan+List<BaseInflateSpan>。
 * <p>
 * 其中MoreSpan是单独设置的，在onPreDraw时先渲染，再遍历spans，逐个渲染。
 */
public class StaticMultiSpanTextView extends StaticTextView {

    /**
     * 展示更多相关属性
     * start
     */
    private final static int MAX_SHOW_LINES = 5;

    private boolean inMiniState;
    private MoreCompSpan customMoreSpan;

    /**
     * 展示更多相关属性
     * end
     */

    private List<BaseInflateSpan> spans;

    public StaticMultiSpanTextView(Context context) {
        super(context);
        init(null);
    }

    public StaticMultiSpanTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StaticMultiSpanTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.StaticMultiSpanTextView);
            inMiniState = array.getBoolean(R.styleable.StaticMultiSpanTextView_miniState, false);
            array.recycle();
        }

        spans = new ArrayList<>();
    }

    public void setInMiniState(boolean inMiniState) {
        this.inMiniState = inMiniState;
        requestLayout();
    }

    public void setCustomMoreSpan(MoreCompSpan customMoreSpan) {
        this.customMoreSpan = customMoreSpan;
        if (customMoreSpan != null) {
            customMoreSpan.setOnMoreClickListener(moreClickListener);
        }
    }

    public MoreCompSpan getMoreSpan() {
        if (customMoreSpan == null) {
            customMoreSpan = new MoreCompSpan(getContext(), " 展示更多");
            customMoreSpan.setOnMoreClickListener(moreClickListener);
        }

        return customMoreSpan;
    }

    public void addSpan(BaseInflateSpan span) {
        if (span == null) {
            return;
        }

        spans.add(span);
    }

    public void refresh() {
        requestLayout();
    }

    /**
     * 构建用于重绘的展示更多span
     *
     * @param layout
     * @return
     */
    private SpannableStringBuilder buildMoreSpan(StaticLayout layout) {
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
            SpannableStringBuilder reDraw = new SpannableStringBuilder(label);
            reDraw.append("...");
            reDraw.append(moreSpan);

            return reDraw;
        }

        return null;
    }

    @Override
    protected CharSequence onPreDraw(StaticLayout layout) {
        SpannableStringBuilder reDraw = null;
        /*
        先渲染MoreSpan，组成新的SpannableStringBuilder，用于返回。如果不需要处理加载更多，这个返回是空的。
         */
        if (inMiniState) {
            reDraw = buildMoreSpan(layout);
        }

        /*
         * 逐个渲染spans
         */
        if (!spans.isEmpty()) {
            if (reDraw == null) {
                reDraw = new SpannableStringBuilder(layout.getText());
            }

            for (BaseInflateSpan span : spans) {
                span.inflateSpan(reDraw);
            }
        }

        if (reDraw != null) {
            return reDraw;
        }
        return super.onPreDraw(layout);
    }

    private MoreCompSpan.OnMoreClickListener moreClickListener = new MoreCompSpan.OnMoreClickListener() {
        @Override
        public void onMoreClick() {
            setInMiniState(false);
        }
    };

}
