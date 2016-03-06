package com.whl.weekendmanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.NearTagBean;
import com.whl.weekendmanager.kit.CircleImageView;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:附近模块的表情实体类
 * 2016/3/5
 */
public class NearTagAdapter extends BaseAdapter {
    private List<NearTagBean> datas;
    private Context context;

    public NearTagAdapter(Context context, List<NearTagBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.near_tag_item_layout, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.iconIV = (CircleImageView) convertView.findViewById(R.id.iv_near_tag_icon);

            viewHolder.nameTV = (TextView) convertView.findViewById(R.id.tv_near_tag_name);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        NearTagBean bean = datas.get(position);
        viewHolder.nameTV.setText(bean.getTag_name());
        viewHolder.iconIV.setBackgroundColor(Color.parseColor("#" + bean.getTag_color()));
        return convertView;
    }

    private class MyViewHolder {
        public CircleImageView iconIV;
        public TextView nameTV;
    }
}
