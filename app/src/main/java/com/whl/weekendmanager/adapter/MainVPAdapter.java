package com.whl.weekendmanager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.whl.weekendmanager.fragment.BaseFragment;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:首页ViewPager的adapter
 * 2016/2/27
 */
public class MainVPAdapter extends FragmentPagerAdapter {
    List<BaseFragment> datas;

    public MainVPAdapter(FragmentManager fm, List<BaseFragment> datas) {
        super(fm);
        this.datas = datas;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }


}
