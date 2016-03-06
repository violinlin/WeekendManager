package com.whl.weekendmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.CommentBean;
import com.whl.weekendmanager.kit.CircleImageView;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:
 * 2016/3/6
 */
public class CommentAdapter extends BaseAdapter {
    private List<CommentBean> datas;
    private Context context;

    public CommentAdapter(Context context, List<CommentBean> datas) {
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
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false);
            holder = new MyViewHolder();
            holder.commentTV = (TextView) convertView.findViewById(R.id.tv_comment);
            holder.nameTV = (TextView) convertView.findViewById(R.id.tv_name);
            holder.iconIV = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        CommentBean bean = datas.get(position);
        holder.nameTV.setText(bean.getUserBean().getUser_name());
        holder.commentTV.setText(bean.getContent());
        Picasso.with(context).load(bean.getUserBean().getAvatar()).into(holder.iconIV);
        return convertView;
    }

    private class MyViewHolder {
        TextView nameTV;
        CircleImageView iconIV;
        TextView commentTV;
    }
}
