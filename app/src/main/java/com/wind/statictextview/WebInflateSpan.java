package com.wind.statictextview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebInflateSpan extends BaseInflateSpan {

    private String regex = "(((http|ftp|https|rtsp|mms):\\/{2}(([0-9a-z_-]+\\.)+([a-zA-Z]{2,9}|(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))(:[0-9]+)?($|\\s|(?=[^\\x00-\\x7F])|((\\#|\\/|\\\\|\\?)([\\S0-9a-zA-Z\\d-_\\.\\/\\?&\\%\\!\\@\\#\\$><=!\\+\\*~;:\\(\\)\\|\\[\\]\\\\]+)?))))|(((http|ftp|https|rtsp|mms):\\/{2})?(([0-9a-z_-]+\\.)+(xyz|ren|biz|pub|com|edu|gov|net|org|ac|ad|ae|af|ag|al|an|ao|aq|ar|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|im|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|cr|cu|cv|cx|cy|cz|cz|de|dj|dk|dm|do|dz|ec|ee|eg|er|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|th|io|iq|ir|je|jm|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mk|ml|mn|mn|mo|mp|mr|ms|mt|mu|mv|mw|mx|my|mz|na|nc|ne|nf|ng|nl|np|nr|nu|nz|pe|pf|pg|ph|pl|pm|pn|pr|ps|pt|pw|py|qa|re|ra|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sj|sk|sl|sm|sn|su|sv|sy|sz|tc|td|tf|tg|tj|tk|tl|tm|tn|to|tp|tr|tt|tv|tw|tz|ua|ug|uk|us|(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}))(:[0-9]+)?($|\\s|(?=[^\\x00-\\x7F])|((\\#|\\/|\\\\|\\?)([\\S0-9a-zA-Z\\d-_\\.\\/\\?&\\%\\!\\@\\#\\$><=!\\+\\*~;:\\(\\)\\|\\[\\]\\\\]+)?))))|(hellotalk:\\/{2}([0-9a-zA-Z\\d-_\\\\\\/\\?=&])+))";

    private Context context;

    public WebInflateSpan(Context context) {
        this.context = context;
    }

    @Override
    protected void inflateSpan(Spannable text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher != null) {
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                String url = text.subSequence(start, end).toString();
                text.setSpan(new WebClickSpan(url), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    class WebClickSpan extends ClickableSpan {

        private String url;

        public WebClickSpan(String url) {
            this.url = url;
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(0xff0066cc);
            ds.setUnderlineText(false);
        }

        @Override
        public void onClick(@NonNull View widget) {
            if (!TextUtils.isEmpty(url) && context != null) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri content = Uri.parse(url);
                Log.d("WebInflateSpan", "onClick scheme=" + content.getScheme());
                if (TextUtils.isEmpty(content.getScheme())) {
                    content = content.buildUpon().scheme("http").build();
                }
                intent.setData(content);
                context.startActivity(intent);
            }
        }
    }
}
