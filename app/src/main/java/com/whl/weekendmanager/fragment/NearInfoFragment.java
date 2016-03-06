package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.MapView;
import com.whl.weekendmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearInfoFragment extends android.support.v4.app.Fragment {


    public NearInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_near_info, container, false);
        MapView mapView= (MapView) view.findViewById(R.id.mv_map_view);
        mapView.onCreate(savedInstanceState);
        return view;
    }


}
