package com.whl.weekendmanager.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.internal.Util;
import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
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
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:用户推荐详情
 * 2016/3/6
 */
public class RecommendInfoActivity extends Activity {
    private List<ArticleBean.ContentBean> datas;
    private RecommendInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_info_activity);
        initView();
        initDialog();
        requestData();
    }

    private void initDialog() {
        myDialog = new MyDialog(RecommendInfoActivity.this, R.style.MyDialog);

    }

    View headView;
    ListView recommendLV;
    private TextView headName;
    private TextView headDate;
    private TextView headLocation;
    private CircleImageView headIcon;
    private MyDialog myDialog;
    ArticleBean articleBean;

    private void initView() {
        recommendLV = (ListView) findViewById(R.id.lv_recommend_list);
        datas = new LinkedList<>();
        adapter = new RecommendInfoAdapter(this, datas);
        recommendLV.setAdapter(adapter);
        headView = LayoutInflater.from(this).inflate(
                R.layout.recommend_head_layout, recommendLV, false);
        headName = (TextView) headView.findViewById(R.id.tv_name);
        headDate = (TextView) headView.findViewById(R.id.tv_date);
        headLocation = (TextView) headView.findViewById(R.id.tv_location);
        headIcon = (CircleImageView) headView.findViewById(R.id.iv_icon);
        headLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articleBean != null) {
                    myDialog.setLocation(articleBean.getPoiInfoBean().getPoi_name());
                    myDialog.setLocationInfo(articleBean.getPoiInfoBean().getPoi_address());
                    myDialog.setDate(articleBean.getCreated_at() + "");
                    myDialog.setOnLocationClickListener(new OnMyItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(RecommendInfoActivity.this, MapActivity.class);
                            double lat = articleBean.getPoiInfoBean().getPoi_lat();
                            double lng = articleBean.getPoiInfoBean().getPoi_lng();
                            String name = articleBean.getPoiInfoBean().getPoi_name();
                            intent.putExtra("lat", lat);
                            intent.putExtra("lng", lng);
                            intent.putExtra("name", name);
                            startActivity(intent);
                        }
                    });
                    myDialog.setCancelListener(new MyOnCancelListener() {
                        @Override
                        public void cancel() {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.show();
                }

            }
        });

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
                Picasso.with(RecommendInfoActivity.this)
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
