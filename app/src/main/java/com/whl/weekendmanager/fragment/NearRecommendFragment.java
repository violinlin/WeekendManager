package com.whl.weekendmanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.activity.MapActivity;
import com.whl.weekendmanager.adapter.RecommendInfoAdapter;
import com.whl.weekendmanager.bean.ArticleBean;
import com.whl.weekendmanager.dialog.MyDialog;
import com.whl.weekendmanager.interfacep.MyOnCancelListener;
import com.whl.weekendmanager.interfacep.OnMyItemClickListener;
import com.whl.weekendmanager.kit.CircleImageView;
import com.whl.weekendmanager.netcontrol.NetControl;
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


    public NearRecommendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_recommend, container, false);
        initView(view);
        requestData();
        return view;
    }

    private List<ArticleBean.ContentBean> datas;
    private RecommendInfoAdapter adapter;
    View headView;
    ListView recommendLV;
    private TextView headName;
    private TextView headDate;
    private TextView headLocation;
    private CircleImageView headIcon;
    ArticleBean articleBean;

    private void initView(View view) {
        recommendLV = (ListView) view.findViewById(R.id.lv_recommend_list);
        datas = new LinkedList<>();
        adapter = new RecommendInfoAdapter(getContext(), datas);
        recommendLV.setAdapter(adapter);
        headView = LayoutInflater.from(getContext()).inflate(
                R.layout.recommend_head_layout, recommendLV, false);
        headName = (TextView) headView.findViewById(R.id.tv_name);
        headDate = (TextView) headView.findViewById(R.id.tv_date);
        headLocation = (TextView) headView.findViewById(R.id.tv_location);
        headIcon = (CircleImageView) headView.findViewById(R.id.iv_icon);
    }

    private void requestData() {
//        http://apiv30.chengmi.com/v29/article/view HTTP/1.1
        NetControl.getInstance().postAsynParam("v29/article/view", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                JSONObject bodyJSON = responseJSON.getJSONObject("body");
                JSONObject articleInfo = bodyJSON.getJSONObject("article_info");
                articleBean = new ArticleBean();
                articleBean.parseJSON(articleInfo);
                datas.addAll(articleBean.getContentList());
                recommendLV.addHeaderView(headView);
                headName.setText(articleBean.getAuthorInfo().getUser_name());
                headLocation.setText(articleBean.getPoiInfoBean().getPoi_name());
                headDate.setText(articleBean.getAuthorInfo().getCreated_at() + "");
                Picasso.with(getContext())
                        .load(articleBean.getAuthorInfo().getAvatar())
                        .into(headIcon);
                adapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam("app_id", "200003"
                , "params", "eyJhY2Nlc3NfdG9rZW4iOiIiLCJsbmciOiIxMTYuMzY1MzIiLCJsYXQiOiI0MC4wMzMzMjQiLCJh\n" +
                        "cnRpY2xlX2lkIjoyNjAyNywiY2l0eV9pZCI6MTEsInZlcnNpb24iOiJ2Mjk4In0=\n",
                "verify", "1065a3573f31295842202b08cde7e7e8"));
    }


}
