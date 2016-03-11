package com.whl.weekendmanager.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:附近模块的实体类
 * 2016/3/3
 */
public class NearBean extends BaseBean implements Serializable{


    private int fav_count;
    private int section_id;
    private String section_title;
    private PoiInfo poiInfo;
    private String pic;

    @Override
    public void parseJSON(JSONObject jsonObject) throws JSONException {
        fav_count = jsonObject.getInt("fav_count");
        section_id = jsonObject.getInt("section_id");
        section_title = jsonObject.getString("section_title");
        JSONArray picJSONArray = jsonObject.getJSONArray("pic_list");
        String picStr = picJSONArray.toString();
        picStr=picStr.substring(1).replace(']','}');
        picStr="{\"key\":"+picStr;
        JSONObject picJSON=new JSONObject(picStr);
        pic=picJSON.getString("key");
        JSONArray poiJSONArray = jsonObject.getJSONArray("poi_list");
        JSONObject poiJSON = poiJSONArray.getJSONObject(0);
        poiInfo = new PoiInfo();
        poiInfo.parseJSON(poiJSON);
    }

    public PoiInfo getPoiInfo() {
        return poiInfo;
    }

    public void setPoiInfo(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setFav_count(int fav_count) {
        this.fav_count = fav_count;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public void setSection_title(String section_title) {
        this.section_title = section_title;
    }

    public int getFav_count() {
        return fav_count;
    }

    public int getSection_id() {
        return section_id;
    }

    public String getSection_title() {
        return section_title;
    }

    public class PoiInfo extends BaseBean implements Serializable{


        /**
         * brand_id : 7107
         * city_id : 11
         * distance : 360
         * poi_address : 上地十街辉煌国际西6楼103-104室
         * poi_area : 海淀区
         * poi_hour : 午餐 11：30－－14：00下午茶 14：00－－17：30晚餐 17：30－－20：30
         * poi_id : 62514
         * poi_lat : 40.052725
         * poi_lng : 116.300557
         * poi_name : Have Fun 有饭
         */

        private int brand_id;
        private int city_id;
        private int distance;
        private String poi_address;
        private String poi_area;
        private String poi_hour;
        private int poi_id;
        private String poi_lat;
        private String poi_lng;
        private String poi_name;

        @Override
        public void parseJSON(JSONObject jsonObject) throws JSONException {
            brand_id = jsonObject.getInt("brand_id");
            city_id = jsonObject.getInt("city_id");
            distance = jsonObject.getInt("distance");
            poi_address = jsonObject.getString("poi_address");
            poi_area = jsonObject.getString("poi_area");
            poi_hour = jsonObject.getString("poi_hour");
            poi_id = jsonObject.getInt("poi_id");
            poi_lat = jsonObject.getString("poi_lat");
            poi_lng = jsonObject.getString("poi_lng");
            poi_name = jsonObject.getString("poi_name");

        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public void setPoi_address(String poi_address) {
            this.poi_address = poi_address;
        }

        public void setPoi_area(String poi_area) {
            this.poi_area = poi_area;
        }

        public void setPoi_hour(String poi_hour) {
            this.poi_hour = poi_hour;
        }

        public void setPoi_id(int poi_id) {
            this.poi_id = poi_id;
        }

        public void setPoi_lat(String poi_lat) {
            this.poi_lat = poi_lat;
        }

        public void setPoi_lng(String poi_lng) {
            this.poi_lng = poi_lng;
        }

        public void setPoi_name(String poi_name) {
            this.poi_name = poi_name;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public int getCity_id() {
            return city_id;
        }

        public int getDistance() {
            return distance;
        }

        public String getPoi_address() {
            return poi_address;
        }

        public String getPoi_area() {
            return poi_area;
        }

        public String getPoi_hour() {
            return poi_hour;
        }

        public int getPoi_id() {
            return poi_id;
        }

        public String getPoi_lat() {
            return poi_lat;
        }

        public String getPoi_lng() {
            return poi_lng;
        }

        public String getPoi_name() {
            return poi_name;
        }
    }
}
