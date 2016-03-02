package com.whl.weekendmanager.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:广告栏实体类
 * 2016/2/28
 */
public class HeadFlashBean {

    /**
     * article_id : 26125
     * content_type : 2
     * goods_id : 0
     * pic : http://img1.chengmi.com/cm/04361da3-3515-4a6c-b5aa-d3b69843a43a
     * section_id : 0
     * tag_id : 0
     * url :
     * user_id : 0
     */

    private int article_id;
    private int content_type;
    private String pic;

    public void parseJSON(JSONObject jsonObject) throws JSONException {
        article_id = jsonObject.getInt("article_id");
        content_type = jsonObject.getInt("content_type");
        pic = jsonObject.getString("pic");

    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public void setContent_type(int content_type) {
        this.content_type = content_type;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getArticle_id() {
        return article_id;
    }

    public int getContent_type() {
        return content_type;
    }

    public String getPic() {
        return pic;
    }
}
