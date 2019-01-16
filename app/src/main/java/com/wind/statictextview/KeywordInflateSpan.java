package com.wind.statictextview;

import android.graphics.Color;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeywordInflateSpan extends BaseInflateSpan {

    private String keyword;
    private int color = Color.YELLOW;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void inflateSpan(Spannable text) {
        if (TextUtils.isEmpty(text))
            return;

        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(text);
        if (matcher != null) {
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                text.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
