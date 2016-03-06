package com.whl.weekendmanager.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:附近模块tag的实体类
 * 2016/3/5
 */
public class NearTagBean extends BaseBean{

    private String tag_color;
    private int tag_id;
    private String tag_name;

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {
        tag_color=jsonObject.getString("tag_color");
        tag_id=jsonObject.getInt("tag_id");
        tag_name=jsonObject.getString("tag_name");
    }

    public void setTag_color(String tag_color) {
        this.tag_color = tag_color;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTag_color() {
        return tag_color;
    }

    public int getTag_id() {
        return tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }
}
