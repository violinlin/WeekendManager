package com.whl.weekendmanager.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whl.weekendmanager.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LabelFragment extends BaseFragment {


    public LabelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.label_fragment_, container, false);
    }


}
