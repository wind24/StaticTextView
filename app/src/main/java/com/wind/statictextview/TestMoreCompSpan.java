package com.wind.statictextview;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Spanned;
import android.text.style.ImageSpan;

public class TestMoreCompSpan extends MoreCompSpan {
    public TestMoreCompSpan(Context context) {
        super(context, "悟");
        Drawable drawable = context.getDrawable(R.drawable.ic_launcher_background);
        if (drawable != null) {
            Rect bound = new Rect(0, 0, 40, 40);
            drawable.setBounds(bound);
            setSpan(new ImageSpan(drawable), 0, getSource().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    @Override
    public int getMoreLength() {
        return 2;
    }
}
