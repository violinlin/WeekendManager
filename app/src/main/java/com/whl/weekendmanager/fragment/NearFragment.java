package com.whl.weekendmanager.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.Util;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.NearBean;
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
public class NearFragment extends BaseFragment {


    private SwipeRefreshLayout swipeRefreshLayout;
    private List<NearBean> datas;
    private MyAdapter adapter;

    public NearFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.near_fragment, container, false);
        initView(view);
        requestData();
        return view;
    }

    /**
     * 网络数据请求
     */
    private void requestData() {
        NetControl.getInstance().postAsynParam("v29/section/list", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("whl", responseJSON.toString());
                JSONObject body = responseJSON.getJSONObject("body");
                JSONArray sectionArray = body.getJSONArray("section_list");
                NearBean nearBean;
                for (int i = 0; i < sectionArray.length(); i++) {
                    JSONObject json = sectionArray.getJSONObject(i);
                    nearBean = new NearBean();
                    nearBean.parseJSON(json);
                    datas.add(nearBean);
                }
                adapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam("app_id", "200003", "params", "eyJhY2Nlc3NfdG9rZW4iOiIiLCJjYXRfdHlwZSI6Im5lYXJieSIsInRhZ19pZCI6Ii0xIiwiY3Vy\nbGF0IjoiNDAuMDQ5NzYiLCJjdXJsbmciOiIxMTYuMjk4ODQxIiwic29ydHdheSI6Im5lYXJieSIs\nImdyb3VwX2lkIjoiMCIsImxvY2FsX3RhZ2lkIjowLCJsb2NhbF90YWdsYWJlbF9pZCI6MCwib3Ro\nZXJfdWlkIjowLCJwZXJwYWdlIjoyMCwibG9jYWxfYXJlYWlkIjowLCJjdXJwYWdlIjoxLCJjaXR5\nX2lkIjoxMSwidmVyc2lvbiI6InYyOTgifQ\u003d\u003d\n", "verify", "5ef87d4c6897766a3e44bcbef80a8f23"));
    }

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        datas = new LinkedList<>();
        adapter = new MyAdapter(getContext(), datas);
        recyclerView.setAdapter(adapter);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private Context context;
        private List<NearBean> datas;

        public MyAdapter(Context context, List<NearBean> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.near_item_layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            NearBean nearBean = datas.get(position);
            NearBean.PoiInfo poiInfo = nearBean.getPoiInfo();
            holder.likeTV.setText(nearBean.getFav_count() + "");
            holder.titleTV.setText(nearBean.getSection_title());
            holder.addreTV.setText(poiInfo.getPoi_name() + "·" + poiInfo.getDistance());
            String picS = nearBean.getPic();
            holder.backIV.setImageURI(Uri.parse(picS));
            Log.d("whl",picS);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView addreTV;
        public TextView titleTV;
        public TextView likeTV;
        public SimpleDraweeView backIV;

        public MyViewHolder(View itemView) {
            super(itemView);
            addreTV = (TextView) itemView.findViewById(R.id.tv_address);
            titleTV = (TextView) itemView.findViewById(R.id.tv_title);
            likeTV = (TextView) itemView.findViewById(R.id.tv_like);
            backIV = (SimpleDraweeView) itemView.findViewById(R.id.iv_background);
        }
    }
}
