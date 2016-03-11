package com.whl.weekendmanager.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.activity.MapActivity;
import com.whl.weekendmanager.bean.ArticleBean;
import com.whl.weekendmanager.bean.NearBean;
import com.whl.weekendmanager.util.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearInfoFragment extends android.support.v4.app.Fragment {
    NearBean.PoiInfo poiInfo;

    public NearInfoFragment() {
        // Required empty public constructor
    }

    AMap aMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_info, container, false);
        MapView mapView = (MapView) view.findViewById(R.id.mv_map_view);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        poiInfo = (NearBean.PoiInfo) getArguments().getSerializable("poi");
        lat = Utils.str2double(poiInfo.getPoi_lat());
        lng = Utils.str2double(poiInfo.getPoi_lng());
        initView(view);
        return view;
    }

    private void initView(View view) {
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_layout);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
//                name = getIntent().getStringExtra("name");
//                lat = getIntent().getDoubleExtra("lat", 39.908692);
//                lng = getIntent().getDoubleExtra("lng", 116.397477);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("name", poiInfo.getPoi_name());
                startActivity(intent);
            }
        });
        TextView nameTV = (TextView) view.findViewById(R.id.tv_name);
        nameTV.setText(poiInfo.getPoi_name());
        TextView distanceTV = (TextView) view.findViewById(R.id.tv_distance);
        distanceTV.setText(poiInfo.getDistance() + "米");
        TextView addressTV = (TextView) view.findViewById(R.id.tv_address);
        addressTV.setText(poiInfo.getPoi_address());
        TextView timeTV = (TextView) view.findViewById(R.id.tv_time);
        timeTV.setText(poiInfo.getPoi_hour());
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions().position(
                latLng).title(poiInfo.getPoi_name()).visible(true);


        aMap.addMarker(markerOptions);
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
                intent.putExtra("name", poiInfo.getPoi_name());
                startActivity(intent);
            }
        });
//        aMap.getMapScreenMarkers().get(0).showInfoWindow();
        CameraUpdate cameraUpdate = CameraUpdateFactory.changeLatLng(latLng);
        aMap.moveCamera(cameraUpdate);

    }

    double lat;
    double lng;


}
