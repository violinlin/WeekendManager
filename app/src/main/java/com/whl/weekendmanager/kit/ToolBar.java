package com.whl.weekendmanager.kit;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.whl.weekendmanager.R;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:自定义头部样式
 * 2016/3/10
 */
public class ToolBar extends FrameLayout {
    public ToolBar(Context context) {
        super(context,null);
    }

    public  ImageView backIV;
    public TextView leftTV;
    public TextView centerTV;
    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view= LayoutInflater.from(context).inflate(R.layout.tool_bar_layout,this,true);
        backIV= (ImageView) view.findViewById(R.id.tool_back_iv);
        leftTV= (TextView) view.findViewById(R.id.tool_left_tv);
        centerTV= (TextView) view.findViewById(R.id.tool_center_tv);

    }

}
