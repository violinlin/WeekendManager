package com.whl.weekendmanager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whl.weekendmanager.adapter.MainVPAdapter;
import com.whl.weekendmanager.fragment.AreaFragment;
import com.whl.weekendmanager.fragment.BaseFragment;
import com.whl.weekendmanager.fragment.CommunityFragment;
import com.whl.weekendmanager.fragment.LabelFragment;
import com.whl.weekendmanager.fragment.NearFragment;
import com.whl.weekendmanager.fragment.NearFragmentNormal;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<BaseFragment> datas = new LinkedList<>();
        datas.add(new CommunityFragment());
        datas.add(new NearFragment());
        datas.add(new AreaFragment());
        datas.add(new LabelFragment());
        viewPager.setAdapter(new MainVPAdapter(getSupportFragmentManager(), datas));
        ImageView backIV = (ImageView) findViewById(R.id.iv_back);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        final RadioButton indexRB = (RadioButton) findViewById(R.id.rb_index);
        final RadioButton nearRB = (RadioButton) findViewById(R.id.rb_near);
        final RadioButton meRB = (RadioButton) findViewById(R.id.rb_me);
        final RadioButton newsRB = (RadioButton) findViewById(R.id.rb_news);
        indexRB.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_index:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rb_near:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.rb_me:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.rb_news:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                    radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
