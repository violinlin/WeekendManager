package com.whl.weekendmanager;

import android.app.Application;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.whl.weekendmanager.netcontrol.NetControl;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:项目的Application
 * 2016/3/2
 */
public class MyApplication extends Application implements AMapLocationListener {
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    @Override
    public void onCreate() {
        super.onCreate();
//        startLocation();
    }


    public void startLocation() {

        //声明AMapLocationClient类对象
//         AMapLocationClient mLocationClient = null;
//声明定位回调监听器
//         AMapLocationListener mLocationListener =
//初始化定位
        AMapLocationClient mLocationClient = new AMapLocationClient(getApplicationContext());
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
                Toast.makeText(getApplicationContext(), "您的位置：" + address, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "定位失败", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
