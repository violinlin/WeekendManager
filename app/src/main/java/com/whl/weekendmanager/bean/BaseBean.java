package com.whl.weekendmanager.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:
 * 2016/3/2
 */
public abstract class BaseBean {
    abstract public void parseJSON(JSONObject jsonObject)throws JSONException;

    ;
}
