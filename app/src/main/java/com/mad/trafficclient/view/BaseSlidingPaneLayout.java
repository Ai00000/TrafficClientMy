package com.mad.trafficclient.view;

import android.content.Context;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by Administrator on 2017/6/2.
 */

public class BaseSlidingPaneLayout extends SlidingPaneLayout {
    float motionX;
    float edgeSlop;

    public BaseSlidingPaneLayout(Context context) {
        super(context, null);
    }

    public BaseSlidingPaneLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BaseSlidingPaneLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        edgeSlop = configuration.getScaledEdgeSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                motionX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();
                if (!isOpen() && motionX > edgeSlop && canScroll(this, false, Math.round(x - motionX), Math.round(x), Math.round(y))) {
                    MotionEvent event = MotionEvent.obtain(ev);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onInterceptTouchEvent(event);
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }
}
