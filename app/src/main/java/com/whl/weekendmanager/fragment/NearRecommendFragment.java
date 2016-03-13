package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.ArticleBean;
import com.whl.weekendmanager.kit.CircleImageView;
import com.whl.weekendmanager.netcontrol.NetControl;
import com.whl.weekendmanager.progress.ProgressHUD;
import com.whl.weekendmanager.util.Constant;
import com.whl.weekendmanager.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearRecommendFragment extends android.support.v4.app.Fragment {

    private int selectID =26026;
    public NearRecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_recommend, container, false);
        initView(view);
        selectID=getArguments().getInt("id");
        requestData();
        return view;
    }

    private List<ArticleBean.ContentBean> datas;
    RecyclerView recommendLV;

    ArticleBean articleBean;
    MyAdapter adapter;

    private void initView(View view) {
        recommendLV = (RecyclerView) view.findViewById(R.id.lv_recommend_list);
        datas = new LinkedList<>();
        adapter = new MyAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recommendLV.setLayoutManager(manager);
        recommendLV.setAdapter(adapter);

    }

    private void requestData() {
//        http://apiv30.chengmi.com/v29/article/view HTTP/1.1
        ProgressHUD.getInstance(getContext()).show();
        NetControl.getInstance().postAsyn("v29/article/view", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ProgressHUD.getInstance(getContext()).cancel();
            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                ProgressHUD.getInstance(getContext()).cancel();
                Log.d("whl",responseJSON.toString());
                JSONObject bodyJSON = responseJSON.getJSONObject("body");
                JSONObject articleInfo = bodyJSON.getJSONObject("article_info");
                articleBean = new ArticleBean();
                articleBean.parseJSON(articleInfo);
                ArticleBean.ContentBean bean = new ArticleBean().new ContentBean();
                bean.setType(Constant.TYPE_HEAD);
                datas.add(0, bean);
                datas.addAll(articleBean.getContentList());
                adapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam("access_token","","lng","116.321853",
                "lat","39.954206","article_id",selectID,"city_id",11,"version","v298"));
    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder = null;
            View view;
            if (viewType == Constant.TYPE_CONTENTPIC) {
                view = LayoutInflater.from(getContext()).inflate(
                        R.layout.recommend_pic_item_layout, parent, false);
                holder = new ItemPICViewHolder(view);
            } else if (viewType == Constant.TYPE_HEAD) {
                view = LayoutInflater.from(getContext()).inflate(
                        R.layout.recommend_head_layout, recommendLV, false);
                holder = new HeadViewHolder(view);
            } else if (viewType == Constant.TYPE_CONTENTCH) {
                view = LayoutInflater.from(getContext()).inflate(
                        R.layout.recommend_ch_item_layout, parent, false);
                holder = new ItemCHViewHolder(view);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemPICViewHolder) {
                ItemPICViewHolder itemViewHolder = (ItemPICViewHolder) holder;
                Picasso.with(getContext()).load(datas.get(position).getPic()).into(itemViewHolder.picIV);
            } else if (holder instanceof HeadViewHolder) {
                HeadViewHolder headViewHolder = (HeadViewHolder) holder;
                headViewHolder.headName.setText(articleBean.getAuthorInfo().getUser_name());
                headViewHolder.headLocation.setText(articleBean.getPoiInfoBean().getPoi_name());
                headViewHolder.headDate.setText(articleBean.getAuthorInfo().getCreated_at() + "");
                Picasso.with(getContext())
                        .load(articleBean.getAuthorInfo().getAvatar())
                        .into(headViewHolder.headIcon);
            } else if (holder instanceof ItemCHViewHolder) {
                ItemCHViewHolder itemCHViewHolder = (ItemCHViewHolder) holder;
                itemCHViewHolder.chTV.setText(datas.get(position).getCh());
            }

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public int getItemViewType(int position) {
            return datas.get(position).getType();
        }
    }

    private class ItemPICViewHolder extends RecyclerView.ViewHolder {
        ImageView picIV;

        public ItemPICViewHolder(View itemView) {
            super(itemView);
            picIV = (ImageView) itemView.findViewById(R.id.iv_recommend_pic);
        }
    }

    private class ItemCHViewHolder extends RecyclerView.ViewHolder {
        TextView chTV;

        public ItemCHViewHolder(View itemView) {
            super(itemView);
            chTV = (TextView) itemView.findViewById(R.id.iv_recommend_ch);
        }
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView headName;
        TextView headDate;
        TextView headLocation;
        CircleImageView headIcon;

        public HeadViewHolder(View itemView) {
            super(itemView);
            headName = (TextView) itemView.findViewById(R.id.tv_name);
            headDate = (TextView) itemView.findViewById(R.id.tv_date);
            headLocation = (TextView) itemView.findViewById(R.id.tv_location);
            headIcon = (CircleImageView) itemView.findViewById(R.id.iv_icon);
        }
    }


}
