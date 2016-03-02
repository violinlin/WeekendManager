package com.whl.weekendmanager.util;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:各种工具类的集合
 * 2016/2/27
 */
public class Utils {
    /**
     * 将请求参数拼接成字符串
     *
     * @param param
     * @return
     */
    public static String buildJosonParam(Object... param) {
        JSONObject jsonObject = new JSONObject();
        if (param!=null){
            for (int i=0;i<param.length-1;i+=2){
                try {
                    jsonObject.put((String) param[i],param[i+1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject.toString();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
