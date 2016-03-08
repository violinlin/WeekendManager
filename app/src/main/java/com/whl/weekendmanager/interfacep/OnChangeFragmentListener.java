package com.whl.weekendmanager.interfacep;

import com.whl.weekendmanager.bean.NearBean;

import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:切换列表和地图fragment的接口
 * 2016/3/6
 */
public interface OnChangeFragmentListener {
    void changeFragment(List<NearBean>beans);
}