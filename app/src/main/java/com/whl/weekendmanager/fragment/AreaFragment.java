package com.whl.weekendmanager.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.activity.HandPickActivity;
import com.whl.weekendmanager.adapter.AreaGVAdapter;
import com.whl.weekendmanager.bean.AreaBean;
import com.whl.weekendmanager.netcontrol.NetControl;
import com.whl.weekendmanager.progress.ProgressHUD;
import com.whl.weekendmanager.util.Constant;
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
public class AreaFragment extends BaseFragment {

    private List<AreaBean> datas;
    private AreaGVAdapter gvAdapter;
    private SwipeRefreshLayout swipe;

    public AreaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.area_fragment, container, false);
        initView(view);
        requestData();
        return view;
    }

    /**
     * 请求网络数据
     */
    private void requestData() {
//        String param = "{\"app_id\":\"200003\",\"params\":\"eyJjaXR5X2lkIjoxMSwiY3VycGFnZSI6MSwiZ3JvdXBfaWQiOi0xLCJwZXJwYWdlIjowLCJ2ZXJz\\naW9uIjoidjI5OCJ9\\n\",\"verify\":\"9d0a68d3a561120218a176ef533798e3\"}";
        ProgressHUD.getInstance(getContext()).show();
        NetControl.getInstance().postAsyn("v29/discover/grouptaglist", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                swipe.setRefreshing(false);
                ProgressHUD.getInstance(getContext()).cancel();
            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                ProgressHUD.getInstance(getContext()).cancel();
                Log.d("whl", responseJSON.toString());
                swipe.setRefreshing(false);
                JSONObject body = responseJSON.getJSONObject("body");
                JSONArray areaList = body.getJSONArray("area_list");
                AreaBean areaBean;
                for (int i = 0; i < areaList.length(); i++) {
                    areaBean = new AreaBean();
                    areaBean.parseJSON(areaList.getJSONObject(i));
                    datas.add(areaBean);
                }
                gvAdapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam("city_id", Constant.CITY_ID_BEIJING,
                "curpage",1,
                "group_id",-1,
                "perpage",20,
                "version","v298"));
/**
 * Utils.buildJosonParam("app_id", "200003", "params", "eyJjaXR5X2lkIjoxMSwiY3VycGFnZSI6MSwiZ3JvdXBfaWQiOi0xLCJwZXJwYWdlIjoyMCwidmVy\n" +
 "c2lvbiI6InYyOTgifQ==\n", "verify", "7bdfe3a467310cc5a980e866a310795f")
 */
    }

    /**
     * 初始化布局控件
     *
     * @param view
     */
    private void initView(View view) {
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swip_layout);
        swipe.setColorSchemeColors(Color.parseColor("#6BD3FF"),
                Color.parseColor("#FF7121"),Color.parseColor("#FFFF00"));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                requestData();

            }
        });
        GridView gfidView = (GridView) view.findViewById(R.id.grid_view);
        gfidView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AreaBean bean = datas.get(position);
                Toast.makeText(getContext(), datas.get(position).getTag_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), HandPickActivity.class);
                intent.putExtra("name", bean.getTag_name());
                intent.putExtra("id", bean.getTag_id());
                startActivity(intent);
            }
        });
        datas = new LinkedList<AreaBean>();
        gvAdapter = new AreaGVAdapter(getContext(), datas);
        gfidView.setAdapter(gvAdapter);
    }


}
