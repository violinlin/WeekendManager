package com.whl.weekendmanager.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:地区实体类
 * 2016/3/2
 */
public class AreaBean extends BaseBean{

    /**
     * iconurl : http://img.chengmi.com/index/e8d6e659-19dc-4374-9a44-658ff96ecbbb.png
     * section_count : 64
     * tag_color : f98b1b
     * tag_describe : 个玩法
     * tag_id : 502
     * tag_introduce : 朝阳大悦城周边的好去处
     * tag_name : 朝阳大悦城周边
     * tag_type : 3
     */

    private String iconurl;
    private int tag_id;
    private String tag_name;

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {
        iconurl=jsonObject.getString("iconurl");
        tag_id=jsonObject.getInt("tag_id");
        tag_name=jsonObject.getString("tag_name");
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public void setTag_id(int tag_id) {
        this.tag_id = tag_id;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getIconurl() {
        return iconurl;
    }

    public int getTag_id() {
        return tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }


}
