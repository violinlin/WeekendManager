package com.whl.weekendmanager.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.okhttp.Request;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.activity.NearRecommendActivity;
import com.whl.weekendmanager.adapter.NearTagAdapter;
import com.whl.weekendmanager.bean.NearBean;
import com.whl.weekendmanager.bean.NearTagBean;
import com.whl.weekendmanager.interfacep.OnChangeFragmentListener;
import com.whl.weekendmanager.interfacep.OnMyItemClickListener;
import com.whl.weekendmanager.netcontrol.NetControl;
import com.whl.weekendmanager.progress.ProgressHUD;
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
public class NearFragmentNormal extends android.support.v4.app.Fragment {


    private SwipeRefreshLayout swipeRefreshLayout;
    private List<NearBean> datas;
    private MyAdapter adapter;
    private final int TYPE_CLASSFY = 1;
    private final int TYPE_SORT = 2;
    private final int TYPE_MAP = 3;
    private Animation animEnter;
    private Animation animExit;
    private String param = "";
    private boolean canRefresh = true;

    public NearFragmentNormal() {
        // Required empty public constructor
    }

    public NearFragmentNormal(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.near_normal_fragment, container, false);
        initView(view);
        requestData(tagID, sortway);
        loadAnim();
        return view;
    }

    /**
     * 网络数据请求
     */
    private void requestData(int tagID, String sortway) {
        ProgressHUD.getInstance(getContext()).show();
        NetControl.getInstance().postAsyn("v29/section/list", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                swipeRefreshLayout.setRefreshing(false);
                ProgressHUD.getInstance(getContext()).cancel();
            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                ProgressHUD.getInstance(getContext()).cancel();
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
//            "app_id", "200003", "params", "eyJhY2Nlc3NfdG9rZW4iOiIiLCJjYXRfdHlwZSI6Im5lYXJieSIsInRhZ19pZCI6Ii0xIiwiY3Vy\nbGF0IjoiNDAuMDQ5NzYiLCJjdXJsbmciOiIxMTYuMjk4ODQxIiwic29ydHdheSI6Im5lYXJieSIs\nImdyb3VwX2lkIjoiMCIsImxvY2FsX3RhZ2lkIjowLCJsb2NhbF90YWdsYWJlbF9pZCI6MCwib3Ro\nZXJfdWlkIjowLCJwZXJwYWdlIjoyMCwibG9jYWxfYXJlYWlkIjowLCJjdXJwYWdlIjoxLCJjaXR5\nX2lkIjoxMSwidmVyc2lvbiI6InYyOTgifQ\u003d\u003d\n", "verify", "5ef87d4c6897766a3e44bcbef80a8f23"
        }, Utils.buildJosonParam("access_token", "", "cat_type", "nearby",
                "tag_id", tagID, "curlat", curlat, "curlng", curlng, "sortway",
                sortway, "group_id", 0, "local_tagid", 0, "local_taglabel_id", 0, "other_uid", 0, "perpage", 20,
                "local_areaid", 0, "curpage", curpage, "city_id", 11, "version", "v298"));
    }

    int tagID = -1;
    int curpage = 1;
    String sortway = "nearby";
    String curlat = "40.033174";
    String curlng = "116.365118";
    LinearLayout tabLayout;
    RelativeLayout sortLayout;
    RelativeLayout classfyLayout;

    private void initView(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_layout);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#6BD3FF"),
                Color.parseColor("#FF7121"),Color.parseColor("#FFFF00"));
        swipeRefreshLayout.setEnabled(canRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                datas.clear();
                tagID = -1;
                curpage = 1;
                sortway = "nearby";
                requestData(tagID, sortway);
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        datas = new LinkedList<>();
        adapter = new MyAdapter(getContext(), datas);
        adapter.setOnMyItemClickListener(new OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), NearRecommendActivity.class);
                intent.putExtra("icon", datas.get(position).getPic());
                intent.putExtra("name", datas.get(position).getSection_title());
                intent.putExtra("id", datas.get(position).getSection_id());
                intent.putExtra("poi", datas.get(position).getPoiInfo());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        tabLayout = (LinearLayout) view.findViewById(R.id.ly_tab_layout);

        initTabLayout(view);
        initSortLayout(view);
        initClassFyLayout(view);
//        tabLayout.setAnimation();
    }

    //初始化分类布局
    List<NearTagBean> tagDatas = new LinkedList<NearTagBean>();
    NearTagAdapter tagAdapter;

    private void initClassFyLayout(View view) {
        classfyLayout = (RelativeLayout) view.findViewById(R.id.rl_near_tag_layout);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_near_tag);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_near_eat:
                        groupID = "1";
                        break;
                    case R.id.rb_near_drink:
                        groupID = "2";
//                        Toast.makeText(getContext(),"喝",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_near_play:
                        groupID = "3";
                        break;
                    case R.id.rb_near_enjoy:
                        groupID = "4";
                        break;
                    case R.id.rb_near_all:
                        groupID = "0";
                        break;
                }
                if (tagDatas != null) {
                    tagDatas.clear();
                }
                requestTagData(tagAdapter, tagDatas);

            }
        });
        RadioButton eatRB = (RadioButton) view.findViewById(R.id.rb_near_eat);
        RadioButton drinkRB = (RadioButton) view.findViewById(R.id.rb_near_drink);
        RadioButton playRB = (RadioButton) view.findViewById(R.id.rb_near_play);
        RadioButton enjoyRB = (RadioButton) view.findViewById(R.id.rb_near_enjoy);
        ListView tagList = (ListView) view.findViewById(R.id.lv_near_tag_list);
        tagAdapter = new NearTagAdapter(getContext(), tagDatas);
        tagList.setAdapter(tagAdapter);
        ImageView cancelIV = (ImageView) view.findViewById(R.id.iv_near_backbtn1);
        tagList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagID = tagDatas.get(position).getTag_id();
                requestData(tagID, sortway);
                datas.clear();
                classfyLayout.setVisibility(View.GONE);
                classfyLayout.setAnimation(animExit);
            }
        });
        cancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classfyLayout.setVisibility(View.GONE);
                classfyLayout.setAnimation(animExit);
            }
        });

    }

    /**
     * tag标签的网络请求
     *
     * @param tagAdapter
     * @param tagDatas   http://apiv30.chengmi.com/v29/section/grouptaglist
     */
    public String groupID = "0";

    private void requestTagData(final NearTagAdapter tagAdapter, final List<NearTagBean> tagDatas) {
        ProgressHUD.getInstance(getContext()).show();
        NetControl.getInstance().postAsyn("v29/section/grouptaglist", new NetControl.StringCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                ProgressHUD.getInstance(getContext()).cancel();
            }

            @Override
            public void onResponse(JSONObject responseJSON) throws JSONException {
                ProgressHUD.getInstance(getContext()).cancel();
                Log.d("whl", "near_tag" + responseJSON.toString());
                JSONObject bodyJSON = responseJSON.getJSONObject("body");
                JSONArray tagArray = bodyJSON.getJSONArray("tag_list");
                NearTagBean tagBean;
                tagDatas.clear();
                for (int i = 0; i < tagArray.length(); i++) {
                    JSONObject itemJSON = tagArray.getJSONObject(i);
                    tagBean = new NearTagBean();
                    tagBean.parseJSON(itemJSON);
                    tagDatas.add(tagBean);

                }
                tagAdapter.notifyDataSetChanged();

            }
        }, Utils.buildJosonParam("access_token", "",
                "cat_type", "nearby",
                "tag_id", "-1",
                "curlat", "39.95445",
                "curlng", "116.321457",
                "sortway", "nearby",
                "group_id", groupID,//全部0 吃1 喝2 玩 3逛4
                "local_tagid", 0,
                "local_taglabel_id", 0,
                "other_uid", 0,
                "perpage", 20,
                "local_areaid", 0,
                "curpage", 1,
                "city_id", 11,
                "version", "v298"));
/**
 * Utils.buildJosonParam("app_id", "200003",
 "params", "eyJhY2Nlc3NfdG9rZW4iOiIiLCJjYXRfdHlwZSI6Im5lYXJieSIsInRhZ19pZCI6Ii0xIiwiY3Vy\n" +
 "bGF0IjoiNDAuMDMzMzI3IiwiY3VybG5nIjoiMTE2LjM2NDk3MSIsInNvcnR3YXkiOiJuZWFyYnki\n" +
 "LCJncm91cF9pZCI6IjEiLCJsb2NhbF90YWdpZCI6MCwibG9jYWxfdGFnbGFiZWxfaWQiOjAsIm90\n" +
 "aGVyX3VpZCI6MCwicGVycGFnZSI6MjAsImxvY2FsX2FyZWFpZCI6MCwiY3VycGFnZSI6MSwiY2l0\n" +
 "eV9pZCI6MTEsInZlcnNpb24iOiJ2Mjk4In0=\n",
 "verify", "668c1aab4bb795f20cc09549e25f40b4"
 )
 */
    }

    //初始化排序布局
    private void initSortLayout(View view) {
        sortLayout = (RelativeLayout) view.findViewById(R.id.rl_nearby_sort_layout);
        TextView nearTV = (TextView) view.findViewById(R.id.tv_near_nearby);
        nearTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortway = "distance";
                adapter.notifyDataSetChanged();
                datas.clear();
                requestData(tagID, sortway);
                sortLayout.setAnimation(animExit);
                sortLayout.setVisibility(View.GONE);
            }
        });
        TextView hotTV = (TextView) view.findViewById(R.id.tv_near_hot);
        hotTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortway = "colcnt";
                datas.clear();
                adapter.notifyDataSetChanged();
                requestData(tagID, sortway);
                sortLayout.setAnimation(animExit);
                sortLayout.setVisibility(View.GONE);
            }
        });
        TextView latestTV = (TextView) view.findViewById(R.id.tv_near_latest);
        latestTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortway = "time";
                datas.clear();
                adapter.notifyDataSetChanged();
                requestData(tagID, sortway);
                sortLayout.setAnimation(animExit);
                sortLayout.setVisibility(View.GONE);
            }
        });
        ImageView cancelIV = (ImageView) view.findViewById(R.id.iv_near_backbtn);
        cancelIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortLayout.setAnimation(animExit);
                sortLayout.setVisibility(View.GONE);
            }
        });
    }

    //初始化tab标签布局
    private void initTabLayout(final View view) {
        final TextView classfyTV = (TextView) view.findViewById(R.id.tv_near_classfy);
        classfyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classfyLayout.setVisibility(View.VISIBLE);
                classfyLayout.setAnimation(animEnter);
                requestTagData(tagAdapter, tagDatas);
            }
        });
        final TextView sortTV = (TextView) view.findViewById(R.id.tv_near_sort);
        sortTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortLayout.setVisibility(View.VISIBLE);
                sortLayout.setAnimation(animEnter);

            }
        });
        final TextView mapTV = (TextView) view.findViewById(R.id.tv_near_map);

        mapTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeFragmentListener.changeFragment(datas);
            }
        });
    }

    /**
     * 加载动画资源
     */
    private void loadAnim() {
        animEnter = AnimationUtils.loadAnimation(getContext(), R.anim.list_enter);
        animExit = AnimationUtils.loadAnimation(getContext(), R.anim.list_exit);
    }

//    /**
//     * 显示popwindow
//     *
//     * @param view 显示依赖的视图
//     * @param type window中加载的类型
//     */
//    private void showPopWindow(View view, int type) {
//        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        popupWindow.setFocusable(false);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        View popLayout = LayoutInflater.from(getContext()).inflate(R.layout.near_sort_window_layout, null);
//        popupWindow.setContentView(popLayout);
//        view.setAnimation(animEnter);
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//    }


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

            MyViewHolder myViewHolder = new MyViewHolder(view);
            myViewHolder.setOnMyItemClickListener(onMyItemClickListener);

            return myViewHolder;
        }

        private OnMyItemClickListener onMyItemClickListener;

        public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
            this.onMyItemClickListener = onMyItemClickListener;
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

            Log.d("whl", picS);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView addreTV;
        public TextView titleTV;
        public TextView likeTV;
        public SimpleDraweeView backIV;
        private OnMyItemClickListener onMyItemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            addreTV = (TextView) itemView.findViewById(R.id.tv_address);
            titleTV = (TextView) itemView.findViewById(R.id.tv_title);
            likeTV = (TextView) itemView.findViewById(R.id.tv_like);
            backIV = (SimpleDraweeView) itemView.findViewById(R.id.iv_background);
        }

        @Override
        public void onClick(View v) {
            if (onMyItemClickListener != null) {
                onMyItemClickListener.onItemClick(v, getPosition());
            }
        }

        public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
            this.onMyItemClickListener = onMyItemClickListener;
        }
    }

    OnChangeFragmentListener onChangeFragmentListener;

    public void setOnChangeFragmentListener(OnChangeFragmentListener onChangeFragmentListener) {
        this.onChangeFragmentListener = onChangeFragmentListener;
    }


}
