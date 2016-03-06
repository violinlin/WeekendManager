package com.whl.weekendmanager.kit;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:自定义viewpager 解决和scrollView的冲突
 * 2016/3/6
 */
public class FullViewPager extends ViewPager {
    public FullViewPager(Context context) {
        super(context);
    }

    public FullViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
         boolean inter=true;
        if (ev.getAction()==MotionEvent.ACTION_BUTTON_PRESS){
            inter=false;
        }
        return inter;
    }

}
