package com.gitrose.mobile.view;

import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

public class VertivcalClickableMovementMethod extends LinkMovementMethod {
    private static VertivcalClickableMovementMethod sInstance;

    public static VertivcalClickableMovementMethod getInstance() {
        if (sInstance == null) {
            sInstance = new VertivcalClickableMovementMethod();
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
            VerticalImageSpan[] imageSpans = (VerticalImageSpan[]) buffer.getSpans(off, off, VerticalImageSpan.class);
            if (link.length != 0) {
                if (action == 1) {
                    link[0].onClick(widget);
                    return true;
                } else if (action != 0) {
                    return true;
                } else {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                    return true;
                }
            } else if (imageSpans.length == 0) {
                Selection.removeSelection(buffer);
            } else if (action == 1) {
                imageSpans[0].onClick(widget);
                return true;
            } else if (action != 0) {
                return true;
            } else {
                Selection.setSelection(buffer, buffer.getSpanStart(imageSpans[0]), buffer.getSpanEnd(imageSpans[0]));
                return true;
            }
        }
        return false;
    }
}
