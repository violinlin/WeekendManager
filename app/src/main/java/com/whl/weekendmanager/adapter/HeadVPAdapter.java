package com.whl.weekendmanager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.whl.weekendmanager.bean.HeadFlashBean;
import com.whl.weekendmanager.interfacep.OnMyItemClickListener;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:主页广告栏适配器
 * 2016/2/27
 */
public class HeadVPAdapter extends PagerAdapter {
    private Context context;
    private List<View> views;

    private OnMyItemClickListener onMyItemClickListener;

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }

    public HeadVPAdapter(Context context, List<View> views) {
        this.context = context;
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position % views.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = views.get(position % views.size());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyItemClickListener.onItemClick(v, position % views.size());
            }
        });
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        container.addView(view);
        return view;
    }
}
