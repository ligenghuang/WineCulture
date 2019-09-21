package com.lgh.huanglib.util.cusview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * use in xml
 * use in code
 */

public class EmailCenterTextView extends TextView {
    public EmailCenterTextView(Context context) {
        super(context);
    }

    public EmailCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmailCenterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (null != drawables) {
            Drawable drawableLeft = drawables[0];
            Drawable drawableRight = drawables[2];
            float textWidth = getPaint().measureText(getText().toString());
            if (null != drawableLeft) {
                setGravity(Gravity.START | Gravity.CENTER_VERTICAL| Gravity.TOP| Gravity.LEFT);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableLeft.getIntrinsicWidth();
                if (getWidth() - contentWidth > 0) {
                    canvas.translate((getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
                }
            }
            if (null != drawableRight) {
                setGravity(Gravity.END | Gravity.CENTER_VERTICAL| Gravity.TOP| Gravity.LEFT);
                float contentWidth = textWidth + getCompoundDrawablePadding() + drawableRight.getIntrinsicWidth();
                if (getWidth() - contentWidth > 0) {
                    canvas.translate(-(getWidth() - contentWidth - getPaddingRight() - getPaddingLeft()) / 2, 0);
                }
            }
            if (null == drawableRight && null == drawableLeft) {
                setGravity(Gravity.CENTER| Gravity.TOP| Gravity.LEFT);
            }
        }
        super.onDraw(canvas);
    }
}
