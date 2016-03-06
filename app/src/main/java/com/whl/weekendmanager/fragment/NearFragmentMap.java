package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.maps2d.MapView;
import com.whl.weekendmanager.R;
import com.whl.weekendmanager.interfacep.OnChangeFragmentListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearFragmentMap extends Fragment {

    MapView mapView;
    TextView listTV;
    OnChangeFragmentListener onChangeFragmentListener;
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
        listTV= (TextView) view.findViewById(R.id.tv_near_list);
        listTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeFragmentListener.changeFragment();
            }
        });
        return view;
    }

    public void setOnChangeFragmentListener(OnChangeFragmentListener onChangeFragmentListener){
        this.onChangeFragmentListener=onChangeFragmentListener;
    }


}
