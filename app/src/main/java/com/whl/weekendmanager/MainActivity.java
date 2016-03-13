package com.whl.weekendmanager;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.whl.weekendmanager.adapter.MainVPAdapter;
import com.whl.weekendmanager.fragment.AreaFragment;
import com.whl.weekendmanager.fragment.BaseFragment;
import com.whl.weekendmanager.fragment.CommunityFragment;
import com.whl.weekendmanager.fragment.LabelFragment;
import com.whl.weekendmanager.fragment.NearFragment;
import com.whl.weekendmanager.kit.ToolBar;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity implements AMapLocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        startLocation();

    }

    /**
     * 初始化视图控件
     */
    ToolBar toolbar;

    private void initView() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        List<BaseFragment> datas = new LinkedList<>();
        datas.add(new CommunityFragment());
        datas.add(new NearFragment(false));
        datas.add(new AreaFragment());
        datas.add(new LabelFragment());
        viewPager.setAdapter(new MainVPAdapter(getSupportFragmentManager(), datas));
        toolbar = (ToolBar) findViewById(R.id.too_bar);
        toolbar.backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.leftTV.setText("周末管家");
        toolbar.centerTV.setText("北京");
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

    private AMapLocationClient mLocationClient;

    public void startLocation() {

        //声明AMapLocationClient类对象
//         AMapLocationClient mLocationClient = null;
//声明定位回调监听器
//         AMapLocationListener mLocationListener =
//初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(this);
        //声明mLocationOption对象
        AMapLocationClientOption mLocationOption = null;
//初始化定位参数
        mLocationOption = new AMapLocationClientOption();
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
//设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
//设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
//设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
//设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                String address = aMapLocation.getAddress();
                toolbar.centerTV.setText(aMapLocation.getCity());
                Toast.makeText(getApplicationContext(), "您的位置：" + address, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.onDestroy();
    }
}
