package com.gitrose.mobile.view;

import android.content.Context;
import android.text.Html;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class TextViewFixTouchConsume extends TextView {
    boolean dontConsumeNonUrlClicks;
    boolean linkHit;

    public static class LocalLinkMovementMethod extends LinkMovementMethod {
        static LocalLinkMovementMethod sInstance;

        public static LocalLinkMovementMethod getInstance() {
            if (sInstance == null) {
                sInstance = new LocalLinkMovementMethod();
            }
            return sInstance;
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();
            if (action == 1 || action == 0) {
                int x = (((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX();
                int y = (((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY();
                Layout layout = widget.getLayout();
                int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
                ClickableSpan[] link = (ClickableSpan[]) buffer.getSpans(off, off, ClickableSpan.class);
                if (link.length != 0) {
                    if (action == 1) {
                        link[0].onClick(widget);
                    } else if (action == 0) {
                        Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                    }
                    if (!(widget instanceof TextViewFixTouchConsume)) {
                        return true;
                    }
                    ((TextViewFixTouchConsume) widget).linkHit = true;
                    return true;
                }
            }
            return false;
        }
    }

    public TextViewFixTouchConsume(Context context) {
        super(context);
        this.dontConsumeNonUrlClicks = true;
    }

    public TextViewFixTouchConsume(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dontConsumeNonUrlClicks = true;
    }

    public TextViewFixTouchConsume(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.dontConsumeNonUrlClicks = true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.linkHit = false;
        boolean res = super.onTouchEvent(event);
        if (event.getAction() == 0) {
            return true;
        }
        if (this.dontConsumeNonUrlClicks) {
            return this.linkHit;
        }
        return res;
    }

    public void setTextViewHTML(String html) {
        setText(new SpannableStringBuilder(Html.fromHtml(html)));
    }

    public boolean hasFocusable() {
        return false;
    }
}
