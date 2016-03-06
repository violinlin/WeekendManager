package com.whl.weekendmanager.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.whl.weekendmanager.R;
import com.whl.weekendmanager.adapter.HandPickVPAdapter;
import com.whl.weekendmanager.fragment.CommunityFragment;
import com.whl.weekendmanager.fragment.NearFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * 精选Activity根据地点或者标签推荐热门地点
 */
public class HandPickActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hand_pick_activity);

        initHeadView();
        initView();
    }

    private void initHeadView() {
        String name = getIntent().getStringExtra("name");
        int id = getIntent().getIntExtra("id", 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandPickActivity.this.finish();
            }
        });
    }

    /**
     * 初始化布局
     */
    private void initView() {
        TabLayout tabLayout= (TabLayout) findViewById(R.id.tab_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.vp_view_pager);
        List<Fragment> fragments = new LinkedList<>();
        fragments.add(new CommunityFragment(false));
        fragments.add(new NearFragment());
        HandPickVPAdapter adapter = new HandPickVPAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
