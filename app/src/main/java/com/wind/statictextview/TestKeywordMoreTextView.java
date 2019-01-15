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

public class TestKeywordMoreTextView extends StaticMoreTextView {

    private String keyword;

    public TestKeywordMoreTextView(Context context) {
        super(context);
    }

    public TestKeywordMoreTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestKeywordMoreTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
        requestLayout();
    }

    @Override
    protected CharSequence onPreDraw(StaticLayout layout) {
        CharSequence cs = super.onPreDraw(layout);
        if (!TextUtils.isEmpty(keyword)) {
            Pattern r = Pattern.compile(keyword);
            Matcher matcher = r.matcher(layout.getText());
            if (matcher != null) {
                SpannableString keyBuilder = null;
                while (matcher.find()) {
                    if (keyBuilder == null) {
                        if (cs != null) {
                            keyBuilder = new SpannableString(cs);
                        } else {
                            keyBuilder = new SpannableString(layout.getText());
                        }
                    }
                    int start = matcher.start();
                    int end = matcher.end();
                    if (start < keyBuilder.length() && end < keyBuilder.length()) {
                        keyBuilder.setSpan(new ForegroundColorSpan(Color.YELLOW), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

                if (keyBuilder != null) {
                    return keyBuilder;
                }
            }
        }
        return cs;
    }
}
