package com.whl.weekendmanager.activity;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.adapter.HandPickVPAdapter;
import com.whl.weekendmanager.fragment.CommunityFragment;
import com.whl.weekendmanager.fragment.NearCommentFragment;
import com.whl.weekendmanager.fragment.NearFragmentNormal;
import com.whl.weekendmanager.fragment.NearInfoFragment;
import com.whl.weekendmanager.fragment.NearRecommendFragment;

import java.util.LinkedList;
import java.util.List;

public class NearRecommendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_activity_near);
        initHeadView();
        initView();
    }


    private void initHeadView() {
        String name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id", 0);
        String icon = getIntent().getStringExtra("icon");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView bgIV = (ImageView) findViewById(R.id.iv_bg);
        Picasso.with(this).load(icon).into(bgIV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#ffffff"));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.parseColor("#000000"));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NearRecommendActivity.this.finish();
            }
        });
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        List<Fragment> fragments = new LinkedList<>();
        fragments.add(new NearRecommendFragment());
        fragments.add(new NearInfoFragment());
        fragments.add(new NearCommentFragment());
        HandPickVPAdapter adapter = new HandPickVPAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


}
