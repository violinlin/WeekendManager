package com.whl.weekendmanager.bean;

import com.whl.weekendmanager.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:社区模块中用户文章实体类
 * 2016/2/27
 */

public class ArticleBean extends BaseBean {
    /**
     * article_id	26707
     * author_info	Object
     * city_id	11
     * cmpoi_id	0
     * comment_count	0
     * created_at	1456483269
     * is_like	0
     * like_count	3
     * newcontent	Array
     * poi_info	Object
     * selected_at	1456543554
     * tag_list	Array
     * upoi_id	1122
     * user_id	204886
     */
    private int article_id;
    private AuthorBean authorInfo;
    private long created_at;
    private int like_count;
    private List<ContentBean> contentList;
    private List<TagBean> tagList;
    private PoiInfoBean poiInfoBean;
    private int type = Constant.TYPE_NORMAL_VIEW;//默认类型为normal

    public PoiInfoBean getPoiInfoBean() {
        return poiInfoBean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPoiInfoBean(PoiInfoBean poiInfoBean) {

        this.poiInfoBean = poiInfoBean;
    }

    public void parseJSON(JSONObject jsonObject) throws JSONException {
        article_id = jsonObject.getInt("article_id");
        JSONObject authorJSON = jsonObject.optJSONObject("author_info");
        if (authorJSON != null) {
            authorInfo = new AuthorBean();
            authorInfo.parseJSON(authorJSON);

        }
        created_at = jsonObject.getLong("created_at");
        like_count = jsonObject.getInt("like_count");
        contentList = new LinkedList<ContentBean>();
        JSONArray contentArrayJSON = jsonObject.getJSONArray("newcontent");
        ContentBean contentBean;
        for (int i = 0; i < contentArrayJSON.length(); i++) {
            contentBean = new ContentBean();
            contentBean.parseJSON(contentArrayJSON.getJSONObject(i));
            contentList.add(contentBean);
        }
        JSONArray tagArrayJOSN = jsonObject.optJSONArray("tag_list");
        if (tagArrayJOSN != null) {

            TagBean tagBean;
            tagList = new LinkedList<TagBean>();
            for (int i = 0; i < tagArrayJOSN.length(); i++) {
                tagBean = new TagBean();
                tagBean.parseJSON(tagArrayJOSN.getJSONObject(i));
                tagList.add(tagBean);
            }
        }

        JSONObject poiJSON = jsonObject.optJSONObject("poi_info");
        if (poiJSON != null) {
            poiInfoBean = new PoiInfoBean();
            poiInfoBean.parseJSON(poiJSON);

        }


    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public AuthorBean getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(AuthorBean authorInfo) {
        this.authorInfo = authorInfo;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public List<ContentBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentBean> contentList) {
        this.contentList = contentList;
    }

    public List<TagBean> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagBean> tagList) {
        this.tagList = tagList;
    }

    /**
     * 文章作者类
     */
    public class AuthorBean {
        /**
         * avatar : http://img.chengmi.com/cm/10baa3b5-9b77-4f4a-98c4-604051a3d3e0
         * city_id : 115
         * city_name : 朝阳区
         * created_at : 1452934344
         * email : seth311@sina.com
         * gender : 0
         * is_exp_user : 1
         * province_id : 11
         * province_name : 北京
         * role : 300
         * user_describe : 我想我怀念的不止是你，还有在那段时光里穿
         * user_id : 59251
         * user_name : 小鱼酱
         */

        private String avatar;
        private long created_at;
        private int user_id;
        private String user_name;

        public void parseJSON(JSONObject jsonObject) {
            try {
                avatar = jsonObject.getString("avatar");
                created_at = jsonObject.getLong("created_at");
                user_id = jsonObject.getInt("user_id");
                user_name = jsonObject.getString("user_name");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public long getCreated_at() {
            return created_at;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getUser_name() {
            return user_name;
        }
    }

    /**
     * 标签类
     */
    public class TagBean {
        /**
         * image :
         * section_count : 158
         * tag_color : 754c24
         * tag_describe : 家醇香店铺
         * tag_id : 147
         * tag_introduce : 搜罗京城的休闲咖啡馆
         * tag_name : 咖啡店
         * tag_type : 4
         * view_count : 5845
         */

        private String image;
        private int section_count;
        private String tag_color;
        private String tag_describe;
        private int tag_id;
        private String tag_introduce;
        private String tag_name;
        private int tag_type;
        private int view_count;

        public void parseJSON(JSONObject jsonObject) throws JSONException {
            tag_color = jsonObject.getString("tag_color");
            tag_name = jsonObject.getString("tag_name");
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setSection_count(int section_count) {
            this.section_count = section_count;
        }

        public void setTag_color(String tag_color) {
            this.tag_color = tag_color;
        }

        public void setTag_describe(String tag_describe) {
            this.tag_describe = tag_describe;
        }

        public void setTag_id(int tag_id) {
            this.tag_id = tag_id;
        }

        public void setTag_introduce(String tag_introduce) {
            this.tag_introduce = tag_introduce;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public void setTag_type(int tag_type) {
            this.tag_type = tag_type;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public String getImage() {
            return image;
        }

        public int getSection_count() {
            return section_count;
        }

        public String getTag_color() {
            return tag_color;
        }

        public String getTag_describe() {
            return tag_describe;
        }

        public int getTag_id() {
            return tag_id;
        }

        public String getTag_introduce() {
            return tag_introduce;
        }

        public String getTag_name() {
            return tag_name;
        }

        public int getTag_type() {
            return tag_type;
        }

        public int getView_count() {
            return view_count;
        }
    }

    /**
     * 文章内容类
     */
    public class ContentBean {
        /**
         * ch : 你不可错过的饼店
         * pic : http://img.chengmi.com/cm/0ecf2ced-8a56-4908-a08d-657479f56b18
         */

        private String ch;
        private String pic;
        private int type;

        public int getViewType() {
            return viewType;
        }

        public void setViewType(int viewType) {
            this.viewType = viewType;
        }

        private int viewType=0;
        public void parseJSON(JSONObject jsonObject) {
            ch = jsonObject.optString("ch");
            pic = jsonObject.optString("pic");
            if (ch != null && !ch.equals("")) {
                type = Constant.TYPE_CONTENTCH;
            } else if (pic != null && !pic.equals("")) {
                type = Constant.TYPE_CONTENTPIC;
            }

        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setCh(String ch) {
            this.ch = ch;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCh() {
            return ch;
        }

        public String getPic() {
            return pic;
        }
    }

    /**
     * 地点实体类
     */

    public class PoiInfoBean {
        /**
         * brand_id	7627
         * city_id	11
         * poi_address	三里屯酒吧街北路81号
         * poi_area	朝阳区
         * poi_hour
         * poi_id	63597
         * poi_lat	39.936137
         * poi_lng	116.455177
         * poi_name	那里花园
         * poi_tel	Array
         */

        private String poi_address;
        private String poi_name;

        public String getPoi_name() {
            return poi_name;
        }

        public void setPoi_name(String poi_name) {
            this.poi_name = poi_name;
        }

        private String poi_hour;
        private int poi_id;
        private double poi_lat;
        private double poi_lng;

        public void parseJSON(JSONObject jsonObject) {
            try {
                poi_address = jsonObject.optString("poi_address");
                poi_hour = jsonObject.optString("poi_hour");
                poi_lat = jsonObject.optDouble("poi_lat");
                poi_lng = jsonObject.optDouble("poi_lng");
                poi_name = jsonObject.optString("poi_name");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void setPoi_address(String poi_address) {
            this.poi_address = poi_address;
        }

        public void setPoi_hour(String poi_hour) {
            this.poi_hour = poi_hour;
        }

        public void setPoi_id(int poi_id) {
            this.poi_id = poi_id;
        }

        public void setPoi_lat(double poi_lat) {
            this.poi_lat = poi_lat;
        }

        public void setPoi_lng(double poi_lng) {
            this.poi_lng = poi_lng;
        }


        public String getPoi_address() {
            return poi_address;
        }

        public String getPoi_hour() {
            return poi_hour;
        }

        public int getPoi_id() {
            return poi_id;
        }

        public double getPoi_lat() {
            return poi_lat;
        }

        public double getPoi_lng() {
            return poi_lng;
        }
    }


}
