package com.gitrose.mobile.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /* renamed from: com.gitrose.mobile.utils.TextUtil.1 */
    static class C03581 extends ClickableSpan {
        private final /* synthetic */ Context val$context;
        private final /* synthetic */ String val$url;

        C03581(String str, Context context) {
            this.val$url = str;
            this.val$context = context;
        }

        public void onClick(View widget) {
            this.val$context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.val$url)));
        }
    }

    public static SpannableString formatContent(CharSequence text, Context context) {
        SpannableString spannableString = new SpannableString(text);
        Matcher matcher = Pattern.compile("(?:https?|ftp)://[^\\s,\\[\\]\\'\")<>\uff0c\u3002\uff1b\u2018\u2019\u201c\u201d\uff1f\u4e00-\u9fa5]+").matcher(spannableString);
        while (matcher.find()) {
            String match = matcher.group();
            if (match.startsWith("http") && ((ClickableSpan[]) spannableString.getSpans(0, 1, ClickableSpan.class)).length == 0) {
                spannableString.setSpan(new C03581(match, context), matcher.start(), matcher.end(), 33);
            }
        }
        return spannableString;
    }

    public static String formatCommentContent(String text) {
        if (text.contains("<atuser>")) {
            return text.replaceAll("<atuser>", "@");
        }
        if (text.contains("</atuser>")) {
            return text.replaceAll("</atuser>", "");
        }
        return text;
    }
}
