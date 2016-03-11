package com.whl.weekendmanager.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.activity.RecommendInfoActivity;
import com.whl.weekendmanager.adapter.HeadVPAdapter;
import com.whl.weekendmanager.bean.ArticleBean;
import com.whl.weekendmanager.bean.HeadFlashBean;
import com.whl.weekendmanager.interfacep.OnMyItemClickListener;
import com.whl.weekendmanager.kit.CircleImageView;
import com.whl.weekendmanager.netcontrol.NetControl;
import com.whl.weekendmanager.progress.ProgressHUD;
import com.whl.weekendmanager.util.Constant;
import com.whl.weekendmanager.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityFragment extends BaseFragment {

    private static final int TOTAL_COUNTER = 30;//加载服务器端的总数据
    private static final int REQUEST_COUNT = 20;//每页加载的数据
    private List<ArticleBean> datas;
    private ViewPager headPager;
    public RadioGroup indicatorGroup;
    private List<View> headViews;
    private HeadVPAdapter headVPAdapter;
    private SwipeRefreshLayout swipLayout;
    private boolean isRefresh = false;
    private Adapter recyclerAdapter;
    int currPage = 1;
    private boolean isloadMore = false;//是否是加载更多的状态
    private int pagerPosition = 0;//viewpager 的当前页数
    private boolean isLoadHead = true;
    private List<HeadFlashBean> headFlashBeans;

    public CommunityFragment(boolean isLoadHead) {
        // Required empty public constructor
        this.isLoadHead = isLoadHead;
    }

    public CommunityFragment() {

    }

    ArticleBean headBean = new ArticleBean();
    ArticleBean footBean = new ArticleBean();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fresco.initialize(getContext());
        View view = null;
        view = inflater.inflate(R.layout.community_fragment, container, false);
        datas = new LinkedList<ArticleBean>();
        headFlashBeans = new LinkedList<>();
        headBean.setType(Constant.TYPE_HEAD_VIEW);
        footBean.setType(Constant.TYPE_FOOT_VIEW);
        if (isLoadHead) {

            datas.add(0, headBean);
        }
        headViews = new LinkedList<View>();
        initView(view);
        return view;
    }

    /**
     * 初始化布局控件
     */
    RecyclerView recycler;

    private void initView(View view) {
        swipLayout = (SwipeRefreshLayout) view.findViewById(R.id.swip_layout);
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                requestData(1);
            }
        });
        recycler = (RecyclerView) view.findViewById(R.id.recyclerview);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        recyclerAdapter = new Adapter(getContext(), datas, new OnMyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), RecommendInfoActivity.class);
                intent.putExtra("articleid", datas.get(position).getArticle_id());
                startActivity(intent);
            }
        });
        recycler.setAdapter(recyclerAdapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastPosition;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastPosition == datas.size() - 1) {
                    if (!isloadMore) {
                        currPage++;
                        //分页加载更多
                        datas.add(datas.size(), footBean);
//                    recyclerAdapter.notifyItemChanged(datas.size() - 1);
                        recyclerView.scrollToPosition(datas.size() - 1);
                        isloadMore = true;
                        requestData(currPage);
                    } else
                        Toast.makeText(getContext(), "正在加载...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastPosition = manager.findLastVisibleItemPosition();
            }
        });
//        addHeadView(recyclerAdapter);
        requestData(1);

    }


    private void requestData(final int currentpage) {
        ProgressHUD.getInstance(getContext()).show();
        NetControl.getInstance().postAsyn("v29/index/forum", new NetControl.StringCallback() {//index/forum HTTP/1.1
            @Override
            public void onFailure(Request request, IOException e) {
                ProgressHUD.getInstance(getContext()).cancel();
                swipLayout.setRefreshing(false);
                Toast.makeText(getContext(), "网络错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(JSONObject response) throws JSONException {
                ProgressHUD.getInstance(getContext()).cancel();
                if (isRefresh) {
                    datas.clear();
                    if (isLoadHead) {
                        headViews.clear();
                        headFlashBeans.clear();
                        headVPAdapter.notifyDataSetChanged();
                        indicatorGroup.removeAllViews();
                        datas.add(headBean);
                    }
                    isRefresh = false;
                    swipLayout.setRefreshing(isRefresh);
                }
                Log.d("whl", response.toString());
                int code = response.getInt("code");
                if (code == 0) {
                    //移除显示正在加载的footView
                    if (currentpage > 1) {
                        datas.remove(datas.size() - 1);
                        isloadMore = false;
                    }

                    JSONObject body = response.getJSONObject("body");
                    JSONArray array = body.getJSONArray("article_list");
                    ArticleBean articleBean;
                    for (int i = 0; i < array.length(); i++) {
                        articleBean = new ArticleBean();
                        articleBean.parseJSON(array.getJSONObject(i));
                        datas.add(articleBean);
//                        Log.d("whl", "----------------" + i);
//                        Log.d("whl", articleBean.getArticle_id() + "");
//                        Log.d("whl",articleBean.getAuthorInfo().getAvatar());
                    }
                    /**
                     * 头部广告栏
                     * 只有加载第一页是才刷新广告栏
                     */
                    if (currentpage == 1 && isLoadHead) {
                        JSONArray flashJSON = body.getJSONArray("flash");
                        HeadFlashBean flashBean;
                        View itemView;
                        View indicator;
                        for (int i = 0; i < flashJSON.length(); i++) {
                            flashBean = new HeadFlashBean();
                            flashBean.parseJSON(flashJSON.getJSONObject(i));
                            itemView = LayoutInflater.from(getContext()).inflate(R.layout.community_head_item_layout, headPager, false);
                            ImageView headItem = (ImageView) itemView.findViewById(R.id.iv_head_bg);
                            if (flashBean.getPic() != null) {
                                Picasso.with(getContext()).load(flashBean.getPic()).into(headItem);
                            }
                            indicator = LayoutInflater.from(getContext()).inflate(R.layout.indicator_item_layout, headPager, false);
                            RadioButton radioButton = (RadioButton) indicator.findViewById(R.id.radioButton);
                            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(Utils.dip2px(getContext(), 10f), Utils.dip2px(getContext(), 10f));
//                            if (i != 0) {
                            layoutParams.leftMargin = Utils.px2dip(getContext(), 20);
                            indicatorGroup.addView(radioButton, layoutParams);
//                            }
                            if (i == 0) {
                                radioButton.setSelected(true);
                            }
                            headViews.add(itemView);
                            headFlashBeans.add(flashBean);
                        }

                        headPager.setAdapter(headVPAdapter);
                        headVPAdapter.notifyDataSetChanged();
                        bannerPlay();


                    }

                    recyclerAdapter.notifyDataSetChanged();
                }

            }
        }, Utils.buildJosonParam("access_token", "", "curlng", "116.370245", "curlat", "40.036975", "city_id", 11, "curpage", currentpage, "perpage", REQUEST_COUNT));

    }


    /**
     * RecyclerView 的适配器
     */
    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private List<ArticleBean> datas;
        private OnMyItemClickListener onMyItemClickListener;

        public Adapter(Context context, List<ArticleBean> datas, OnMyItemClickListener onMyItemClickListener) {
            this.onMyItemClickListener = onMyItemClickListener;
            this.context = context;
            this.datas = datas;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;

            View view = null;
            if (viewType == Constant.TYPE_NORMAL_VIEW) {
                view = LayoutInflater.from(context).inflate(R.layout.community_item_layout, parent, false);
                viewHolder = new NormalHolder(view, onMyItemClickListener);
            } else if (viewType == Constant.TYPE_HEAD_VIEW) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.community_head_layout, recycler, false);
                viewHolder = new HeadHolder(view);

            } else if (viewType == Constant.TYPE_FOOT_VIEW) {
                view = LayoutInflater.from(context).inflate(R.layout.list_footer_loading, recycler, false);
                viewHolder = new FootHodler(view);
            }
            return viewHolder;
        }

        @Override
        public int getItemViewType(int position) {
            return datas.get(position).getType();
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof NormalHolder) {
                NormalHolder normalHolder = (NormalHolder) holder;
                onNormalBindViewHolder(normalHolder, position);
            } else if (holder instanceof FootHodler) {
                FootHodler footHolder = (FootHodler) holder;
                onFootBindViewHolder(footHolder, position);
            } else if (holder instanceof HeadHolder) {
                HeadHolder headHolder = (HeadHolder) holder;
                onHeadBindViewHolder(headHolder, position);
            }


        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    /**
     * head item
     *
     * @param headHolder
     * @param position
     */
    private void onHeadBindViewHolder(HeadHolder headHolder, int position) {

    }

    /**
     * foot item
     *
     * @param footHolder
     * @param position
     */
    private void onFootBindViewHolder(FootHodler footHolder, int position) {
    }

    /**
     * normal item
     *
     * @param holder
     * @param position
     */
    private void onNormalBindViewHolder(NormalHolder holder, int position) {
        ArticleBean bean = datas.get(position);
        if (bean.getAuthorInfo().getAvatar() != null) {

            Picasso.with(this.getContext()).load(bean.getAuthorInfo().getAvatar()).into(holder.iconIV);
        }
        holder.nameTV.setText(bean.getAuthorInfo().getUser_name());
        SimpleDateFormat sDate = new SimpleDateFormat("MM-dd");

        holder.dateTV.setText(sDate.format(new Date(System.currentTimeMillis() - bean.getCreated_at())));
        for (int i = 0; i < bean.getContentList().size(); i++) {
            if (bean.getContentList().get(i).getType() == Constant.TYPE_CONTENTCH) {
                holder.infoTV.setText(bean.getContentList().get(i).getCh());
                break;
            }
        }
        for (int i = 0; i < bean.getContentList().size(); i++) {
            if (bean.getContentList().get(i).getType() == Constant.TYPE_CONTENTPIC) {
                holder.backgroundIV.setImageURI(Uri.parse(bean.getContentList().get(i).getPic()));
                break;
            }
        }

        holder.locationTV.setText(bean.getPoiInfoBean().getPoi_address());
        holder.likeTV.setText(bean.getLike_count() + "");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < bean.getTagList().size(); i++) {
            builder.append(bean.getTagList().get(i).getTag_name());
            if (i != bean.getTagList().size() - 1) {
                builder.append("·");
            }
        }
        holder.labelTV.setText(builder.toString());
    }

    /**
     * recyclerview 适配器的 viewHolder normalView
     */
    private class NormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CircleImageView iconIV;
        public TextView nameTV;
        public TextView dateTV;
        public ImageView backgroundIV;
        public TextView infoTV;
        public TextView locationTV;
        public TextView labelTV;
        public TextView likeTV;
        OnMyItemClickListener onMyItemClickListener;

        public NormalHolder(View itemView, OnMyItemClickListener onMyItemClickListener) {

            super(itemView);
            this.onMyItemClickListener = onMyItemClickListener;
            itemView.setOnClickListener(this);
            iconIV = (CircleImageView) itemView.findViewById(R.id.iv_icon);
            nameTV = (TextView) itemView.findViewById(R.id.tv_name);
            dateTV = (TextView) itemView.findViewById(R.id.tv_date);
            backgroundIV = (ImageView) itemView.findViewById(R.id.iv_background);
            infoTV = (TextView) itemView.findViewById(R.id.tv_info);
            locationTV = (TextView) itemView.findViewById(R.id.tv_location);
            labelTV = (TextView) itemView.findViewById(R.id.tv_label);
            likeTV = (TextView) itemView.findViewById(R.id.tv_like);

        }

        @Override
        public void onClick(View v) {
            if (onMyItemClickListener != null) {
                onMyItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    /**
     * recyclerview 适配器的 viewHolder footView
     */
    private class FootHodler extends RecyclerView.ViewHolder {

        public FootHodler(View itemView) {
            super(itemView);
        }
    }

    /**
     * recyclerview 适配器的 viewHolder headView
     */
    private class HeadHolder extends RecyclerView.ViewHolder {

        public HeadHolder(View itemView) {
            super(itemView);
            headPager = (ViewPager) itemView.findViewById(R.id.viewpager);
            headVPAdapter = new HeadVPAdapter(getContext(), headViews);
            headVPAdapter.setOnMyItemClickListener(new OnMyItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getContext(), RecommendInfoActivity.class);
                    intent.putExtra("articleid", headFlashBeans.get(position).getArticle_id());
                    startActivity(intent);
                }
            });
            headPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    pagerPosition = position;
                    position = position % headViews.size();
                    for (int i = 0; i < headViews.size(); i++) {
                        if (i == position) {
                            indicatorGroup.getChildAt(position).setSelected(true);
                        } else {
                            indicatorGroup.getChildAt(i).setSelected(false);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            indicatorGroup = (RadioGroup) itemView.findViewById(R.id.radiogroup);
        }
    }

    /**
     * 头部headView的轮播
     */
    Handler handler;

    public void bannerPlay() {
        pagerPosition = headViews.size() * 100;
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
//                            pagerPosition = (++pagerPosition) % headViews.size();

                            headPager.setCurrentItem(++pagerPosition);
                        }
                    });
                    Message message = handler.obtainMessage(0);
                    handler.sendMessageDelayed(message, 2000);
                }
            };
            Message message = handler.obtainMessage(0);
            handler.sendMessageDelayed(message, 2000);
        }
    }


}
