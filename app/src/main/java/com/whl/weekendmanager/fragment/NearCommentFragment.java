package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.adapter.CommentAdapter;
import com.whl.weekendmanager.bean.CommentBean;
import com.whl.weekendmanager.kit.CircleImageView;
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

    private int secectionid;
    private MyAdapter adapter;
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
        secectionid = getArguments().getInt("id", 1229);
        requestData();
        return view;
    }

    private void requestData() {
//        http://apiv30.chengmi.com/v29/remark/sectionremarklist HTTP/1.1
        NetControl.getInstance().postAsyn("v29/remark/sectionremarklist", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
//                Log.d("whl",responseJSON.toString());
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
        }, Utils.buildJosonParam("access_token", "",
                "curpage", 1,
                "perpage", 20,
                "remark_type", -1,
                "section_id", secectionid,
                "version", "v298"
        ));
    }

    /**
     * Utils.buildJosonParam(
     * "app_id", "200003"
     * , "params", "eyJhY2Nlc3NfdG9rZW4iOiIiLCJjdXJwYWdlIjoxLCJwZXJwYWdlIjoyMCwicmVtYXJrX3R5cGUi\n" +
     * "Oi0xLCJzZWN0aW9uX2lkIjoyMTY2LCJ2ZXJzaW9uIjoidjI5OCJ9\n"
     * , "verify", "1bc8efad21b6f4e0b0113600e581d973")
     *
     * @param view
     */
    private void initView(View view) {
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.lv_list_view);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        datas = new LinkedList<>();
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_item_layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            CommentBean bean = datas.get(position);
            holder.nameTV.setText(bean.getUserBean().getUser_name());
            holder.commentTV.setText(bean.getContent());
            Picasso.with(getContext()).load(bean.getUserBean().getAvatar()).into(holder.iconIV);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV;
        CircleImageView iconIV;
        TextView commentTV;

        public MyViewHolder(View itemView) {
            super(itemView);
            commentTV = (TextView) itemView.findViewById(R.id.tv_comment);
            nameTV = (TextView) itemView.findViewById(R.id.tv_name);
            iconIV = (CircleImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
