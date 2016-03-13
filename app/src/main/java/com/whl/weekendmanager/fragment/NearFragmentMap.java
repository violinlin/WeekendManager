package com.whl.weekendmanager.fragment;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.NearBean;
import com.whl.weekendmanager.interfacep.OnChangeFragmentListener;
import com.whl.weekendmanager.util.Utils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearFragmentMap extends Fragment implements LocationSource,
        AMapLocationListener {

    MapView mapView;
    TextView listTV;
    AMap aMap;
    OnChangeFragmentListener onChangeFragmentListener;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;

    public NearFragmentMap() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.near_fragment_map_fragment, container, false);
        mapView = (MapView) view.findViewById(R.id.mv_near_map);

        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        listTV = (TextView) view.findViewById(R.id.tv_near_list);
        listTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeFragmentListener.changeFragment(null);
            }
        });
        addMarkers();
        setUpMap();
        return view;
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    public void setOnChangeFragmentListener(OnChangeFragmentListener onChangeFragmentListener) {
        this.onChangeFragmentListener = onChangeFragmentListener;
    }

    public void addMarkers() {
        boolean first = true;
        for (NearBean bean : datas) {
            String title = bean.getPoiInfo().getPoi_name();
            double lat = Utils.str2double(bean.getPoiInfo().getPoi_lat());
            double lng = Utils.str2double(bean.getPoiInfo().getPoi_lng());
            if (first) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14));
                first = false;
            }
            MarkerOptions mp = new MarkerOptions().position(new LatLng(lat, lng)).title(title);
            aMap.addMarker(mp);
        }

    }

    //设置地址信息
    private List<NearBean> datas;

    public void setPOIInfo(List<NearBean> nearBeans) {
        this.datas = nearBeans;

    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getContext());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
            mLocationOption.setOnceLocation(true);//只请求一次
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;

    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {
                String address = aMapLocation.getAddress();
//                aMapLocation.get
                Toast.makeText(getContext(), "您的位置：" + address, Toast.LENGTH_SHORT).show();
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                Toast.makeText(getContext(), "定位失败", Toast.LENGTH_SHORT).show();
//                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
//                Log.e("AmapErr",errText);
            }
        }
    }
}
