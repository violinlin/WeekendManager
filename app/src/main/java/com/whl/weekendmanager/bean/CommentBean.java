package com.whl.weekendmanager.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:
 * 2016/3/6
 */
public class CommentBean extends BaseBean {

    private String content;
    private int poi_id;
    private String poi_name;
    private int section_id;
    private UserBean userBean;

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {
        content = jsonObject.getString("content");
        poi_id = jsonObject.getInt("poi_id");
        poi_name = jsonObject.getString("poi_name");
        section_id = jsonObject.getInt("section_id");
        userBean = new UserBean();
        JSONObject userJSON = jsonObject.getJSONObject("user");
        userBean.parseJSON(userJSON);

    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPoi_id(int poi_id) {
        this.poi_id = poi_id;
    }

    public void setPoi_name(String poi_name) {
        this.poi_name = poi_name;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getContent() {
        return content;
    }

    public int getPoi_id() {
        return poi_id;
    }

    public String getPoi_name() {
        return poi_name;
    }

    public int getSection_id() {
        return section_id;
    }

    public class UserBean extends BaseBean {

        /**
         * avatar : http://img.chengmi.com/avatar/4094ea1e-fb36-4ff8-b94e-ab917c9ab303
         * city_id : 112
         * city_name : 西城区
         * created_at : 1428024569
         * email : email
         * gender : 1
         * is_exp_user : 0
         * province_id : 11
         * province_name : 北京
         * role : 0
         * user_describe : describe
         * user_id : 148561
         * user_name : 粉笔头儿_
         */

        private String avatar;
        private String user_name;

        @Override
        public void parseJSON(JSONObject jsonObject) throws JSONException {
            avatar = jsonObject.getString("avatar");
            user_name = jsonObject.getString("user_name");
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getUser_name() {
            return user_name;
        }
    }
}
