package com.whl.weekendmanager.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:标签模块的实体类
 * 2016/3/2
 */
public class LabelBean extends BaseBean {


    private String group_name;
    private List<TAGBean> tagList;

    public List<TAGBean> getTagList() {
        return tagList;
    }

    public void setTagList(List<TAGBean> tagList) {
        this.tagList = tagList;
    }

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {
        group_name = jsonObject.getString("group_name");
        tagList = new LinkedList<TAGBean>();
        JSONArray tagArray = jsonObject.getJSONArray("tag_list");
        TAGBean tagBean;
        for (int i = 0; i < tagArray.length(); i++) {
            tagBean = new TAGBean();
            JSONObject json = tagArray.getJSONObject(i);
            tagBean.parseJSON(json);
            tagList.add(tagBean);
        }

    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_name() {
        return group_name;
    }

    public class TAGBean extends BaseBean {


        private int tag_id;
        private String tag_name;

        @Override
        public void parseJSON(JSONObject jsonObject) throws JSONException {
            tag_id = jsonObject.getInt("tag_id");
            tag_name = jsonObject.getString("tag_name");

        }


        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public int getTag_id() {
            return tag_id;
        }

        public String getTag_name() {
            return tag_name;
        }
    }
}
