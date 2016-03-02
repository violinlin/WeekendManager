package com.whl.weekendmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.AreaBean;
import com.whl.weekendmanager.kit.CircleImageView;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:地区模块GridView的适配器
 * 2016/3/2
 */
public class AreaGVAdapter extends BaseAdapter {
    private List<AreaBean> datas;
    private Context context;

    public AreaGVAdapter(Context context, List<AreaBean> datas) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.area_item_layout, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.areaDes = (TextView) convertView.findViewById(R.id.tv_area_des);
            viewHolder.areaLogo = (CircleImageView) convertView.findViewById(R.id.iv_area_logo);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        AreaBean areaBean = datas.get(position);
        viewHolder.areaDes.setText(areaBean.getTag_name());
        Picasso.with(context).load(areaBean.getIconurl()).into(viewHolder.areaLogo);
        return convertView;
    }

    private class MyViewHolder {
        CircleImageView areaLogo;
        TextView areaDes;
    }
}
