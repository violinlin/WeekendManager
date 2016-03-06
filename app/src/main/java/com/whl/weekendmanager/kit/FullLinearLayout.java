package com.whl.weekendmanager.kit;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:
 * 2016/3/6
 */
public class FullLinearLayout extends LinearLayout {
    public FullLinearLayout(Context context) {
        super(context);
    }

    public FullLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
