package com.wind.statictextview;

import android.text.Spannable;

/**
 * 根据自定义的业务来实现的span，添加到StaticMultiSpanTextView，叠加实现各种text效果
 */
public abstract class BaseInflateSpan {

    protected abstract void inflateSpan(Spannable text);

}
