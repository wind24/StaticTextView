package com.wind.statictextview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestKeywordTextView extends StaticTextView {

    private String keyword;

    public TestKeywordTextView(Context context) {
        super(context);
    }

    public TestKeywordTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestKeywordTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        requestLayout();
    }

    @Override
    protected CharSequence onPreDraw(StaticLayout layout) {
        if (!TextUtils.isEmpty(keyword)) {
            Pattern r = Pattern.compile(keyword);
            Matcher matcher = r.matcher(layout.getText());
            if (matcher != null) {
                SpannableString keyBuilder = null;
                while (matcher.find()) {
                    if (keyBuilder == null) {
                        keyBuilder = new SpannableString(layout.getText());
                    }
                    int start = matcher.start();
                    int end = matcher.end();
                    keyBuilder.setSpan(new ForegroundColorSpan(Color.YELLOW), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                if (keyBuilder != null) {
                    return keyBuilder;
                }
            }
        }
        return super.onPreDraw(layout);
    }
}
