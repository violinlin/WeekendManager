package com.whl.weekendmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.ArticleBean;
import com.whl.weekendmanager.util.Constant;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:用户推荐详情的实体类
 * 2016/3/6
 */
public class RecommendInfoAdapter extends BaseAdapter {
    private List<ArticleBean.ContentBean> datas;
    private Context context;

    public RecommendInfoAdapter(Context context, List<ArticleBean.ContentBean> datas) {
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
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == Constant.TYPE_CONTENTCH) {
            convertView = getCHView(position, convertView, parent);
        } else if (getItemViewType(position) == Constant.TYPE_CONTENTPIC) {
            convertView = getPICView(position, convertView, parent);
        }

        return convertView;
    }

    private View getPICView(int position, View convertView, ViewGroup parent) {
        PICHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.recommend_pic_item_layout, parent, false);
            holder = new PICHolder();
            holder.picIV = (ImageView) convertView.findViewById(R.id.iv_recommend_pic);
            convertView.setTag(holder);
        } else {
            holder = (PICHolder) convertView.getTag();
        }
        Picasso.with(context).load(datas.get(position).getPic()).into(holder.picIV);
        return convertView;
    }

    private View getCHView(int position, View convertView, ViewGroup parent) {
        CHHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.recommend_ch_item_layout, parent, false);
            holder = new CHHolder();
            holder.chTV = (TextView) convertView.findViewById(R.id.iv_recommend_ch);
            convertView.setTag(holder);
        } else {
            holder = (CHHolder) convertView.getTag();
        }
        holder.chTV.setText(datas.get(position).getCh());
        return convertView;

    }

    private class PICHolder {
        ImageView picIV;
    }

    private class CHHolder {
        TextView chTV;
    }
}
