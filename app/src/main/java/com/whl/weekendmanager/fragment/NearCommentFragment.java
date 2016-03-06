package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.squareup.okhttp.Request;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.adapter.CommentAdapter;
import com.whl.weekendmanager.bean.CommentBean;
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
public class NearCommentFragment extends android.support.v4.app.Fragment {


    private CommentAdapter adapter;
    private List<CommentBean> datas;

    public NearCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_comment, container, false);
        initView(view);
        requestData();
        return view;
    }

    private void requestData() {
//        http://apiv30.chengmi.com/v29/remark/sectionremarklist HTTP/1.1
        NetControl.getInstance().postAsynParam("v29/remark/sectionremarklist", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                JSONObject body = responseJSON.getJSONObject("body");
                JSONArray array = body.getJSONArray("remark_list");
                CommentBean bean;
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    bean = new CommentBean();
                    bean.parseJSON(object);
                    datas.add(bean);
                }
                adapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam(
                "app_id", "200003"
                , "params", "eyJhY2Nlc3NfdG9rZW4iOiIiLCJjdXJwYWdlIjoxLCJwZXJwYWdlIjoyMCwicmVtYXJrX3R5cGUi\n" +
                        "Oi0xLCJzZWN0aW9uX2lkIjoyMTY2LCJ2ZXJzaW9uIjoidjI5OCJ9\n"
                , "verify", "1bc8efad21b6f4e0b0113600e581d973"));
    }

    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.lv_list_view);
        datas = new LinkedList<>();
        adapter = new CommentAdapter(getContext(), datas);
        listView.setAdapter(adapter);
    }


}
