package com.whl.weekendmanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.Util;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.activity.HandPickActivity;
import com.whl.weekendmanager.bean.AreaBean;
import com.whl.weekendmanager.bean.ArticleBean;
import com.whl.weekendmanager.bean.LabelBean;
import com.whl.weekendmanager.interfacep.OnMyItemClickListener;
import com.whl.weekendmanager.kit.FlowLayout;
import com.whl.weekendmanager.netcontrol.NetControl;
import com.whl.weekendmanager.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelFragment extends BaseFragment {

    private List<LabelBean> datas;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter adapter;

    public LabelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.label_fragment_, container, false);
        initView(view);
        requestData();
        return view;
    }

    /**
     * 初始化布局控件
     *
     * @param view
     */
    private void initView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_label);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                requestData();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        datas = new LinkedList<LabelBean>();
        adapter = new MyAdapter();
        adapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LabelBean.TAGBean tagBean = ( LabelBean.TAGBean) view.getTag();
                Intent intent = new Intent(getContext(), HandPickActivity.class);
                intent.putExtra("name", tagBean.getTag_name());
                intent.putExtra("id", tagBean.getTag_id());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

    }

    /**
     * 请求网络数据
     */
    private void requestData() {
//        String param = "{\"app_id\":\"200003\",\"params\":\"eyJjaXR5X2lkIjoxMSwiY3VycGFnZSI6MSwiZ3JvdXBfaWQiOi0xLCJwZXJwYWdlIjoyMCwidmVy\\nc2lvbiI6InYyOTgifQ\\u003d\\u003d\\n\",\"verify\":\"7bdfe3a467310cc5a980e866a310795f\"}";
        NetControl.getInstance().postAsynParam("v29/discover/grouptaglist", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
//                Log.d("whl", responseJSON.toString());
                swipeRefreshLayout.setRefreshing(false);
                JSONObject body = responseJSON.getJSONObject("body");
                JSONArray areaList = body.getJSONArray("special_list");
                LabelBean labelBean;
                for (int i = 0; i < areaList.length(); i++) {
                    labelBean = new LabelBean();
                    labelBean.parseJSON(areaList.getJSONObject(i));
                    datas.add(labelBean);
                }
                adapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam("app_id", "200003", "params", "eyJjaXR5X2lkIjoxMSwiY3VycGFnZSI6MSwiZ3JvdXBfaWQiOi0xLCJwZXJwYWdlIjowLCJ2ZXJz\n" +
                "aW9uIjoidjI5OCJ9\n", "verify", "9d0a68d3a561120218a176ef533798e3"));
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.label_item_layout, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            LabelBean labelBean = datas.get(position);
            holder.areaName.setText(labelBean.getGroup_name());
            for (LabelBean.TAGBean tagBean : labelBean.getTagList()) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.flow_item_layout, holder.areaLabel, false);
                TextView textView = (TextView) view.findViewById(R.id.tv_flow_tag);
                textView.setText(tagBean.getTag_name());
                view.setTag(tagBean);
                view.setOnClickListener(this);
                holder.areaLabel.addView(textView);
            }
            Log.d("whl", "------------------" + labelBean.getTagList().size());

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        @Override
        public void onClick(View v) {
            onMyItemClickListener.onItemClick(v, 0);
        }

        OnMyItemClickListener onMyItemClickListener;

        public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
            this.onMyItemClickListener = onMyItemClickListener;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView areaIcon;
        TextView areaName;
        FlowLayout areaLabel;

        public MyViewHolder(View itemView) {
            super(itemView);
            areaIcon = (ImageView) itemView.findViewById(R.id.iv_label_icon);
            areaName = (TextView) itemView.findViewById(R.id.tv_label_name);
            areaLabel = (FlowLayout) itemView.findViewById(R.id.fl_flow_layout);

        }
    }


}
