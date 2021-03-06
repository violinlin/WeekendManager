package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.whl.weekendmanager.R;
import com.whl.weekendmanager.bean.NearBean;
import com.whl.weekendmanager.interfacep.OnChangeFragmentListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearFragment extends BaseFragment {


    public NearFragment() {
        // Required empty public constructor
    }

    private boolean canRefresh = true;

    public NearFragment(boolean canRefresh) {
        this.canRefresh = canRefresh;

    }

    FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.near_fragment, container, false);
        frameLayout = (FrameLayout) view.findViewById(R.id.fragment);

        return view;
    }

    NearFragmentNormal fragmentNormal = new NearFragmentNormal(canRefresh);
    NearFragmentMap fragmentMap = new NearFragmentMap();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment, fragmentNormal).commit();
        fragmentNormal.setOnChangeFragmentListener(new OnChangeFragmentListener() {
            @Override
            public void changeFragment(List<NearBean> beans) {

                getChildFragmentManager().beginTransaction().replace(R.id.fragment, fragmentMap).commit();
                fragmentMap.setPOIInfo(beans);

            }

        });
        fragmentMap.setOnChangeFragmentListener(new OnChangeFragmentListener() {
            @Override
            public void changeFragment(List<NearBean> beans) {
                getChildFragmentManager().beginTransaction().replace(R.id.fragment, fragmentNormal).commit();
            }
        });
    }

}
