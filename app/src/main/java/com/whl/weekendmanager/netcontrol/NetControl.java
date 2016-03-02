package com.whl.weekendmanager.netcontrol;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whl.weekendmanager.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:网络请求工具类
 * 2016/2/25
 */
public class NetControl {
    private static NetControl mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private static final String TAG = "OkHttpClient";
    private static final String HOST = "http://apiv30.chengmi.com/";

    private NetControl() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());

    }

    //单例模式
    public static NetControl getInstance() {
        if (mInstance == null) {
            synchronized (NetControl.class) {
                if (mInstance == null) {
                    mInstance = new NetControl();
                }
            }
        }
        return mInstance;
    }

    private void _postAsyn(String action, final StringCallback callBack, String parms) {
        String url = HOST + action;//拼接请求url
        Log.d("whl", url);
        Request request = buildPostRequest(url, parms);
        deliveryResult(callBack, request);
    }

    public static void postAsyn(String action, final StringCallback stringCallback, String parms) {
        getInstance()._postAsyn(action, stringCallback, parms);
    }

    public static void postAsynParam(String action, StringCallback stringCallback, String parms) {
        getInstance()._postAsynParam(action, stringCallback, parms);
    }

    private void _postAsynParam(String action, StringCallback stringCallback, String parms) {
        String url = HOST + action;
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, parms);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        deliveryResult(stringCallback, request);
    }

    /**
     * 请求结果的返回和分发
     *
     * @param callback
     * @param request
     */
    private void deliveryResult(final StringCallback callback, Request request) {
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();
                    sendSuccessStringCallback(string, callback);
                } catch (IOException e) {
                    sendFailedStringCallback(response.request(), e, callback);
                }

            }
        });
    }

    private void sendFailedStringCallback(final Request request, final IOException e, final StringCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onFailure(request, e);
            }
        });
    }

    private void sendSuccessStringCallback(final String string, final StringCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(string);
                        callback.onResponse(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private Request buildPostRequest(String url, String params) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, buildJsonParam(params));
        Log.d("whl_net", url);
        Log.d("whl_net", params.toString());
        Log.d("whl_net", buildJsonParam(params));
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }


    /**
     * 自定义的接口回掉接口
     */
    public interface StringCallback {
        void onFailure(Request request, IOException e);

        void onResponse(JSONObject responseJSON) throws JSONException;
    }

    /**
     * 将请求参数拼接成json
     *
     * @ param    参数字符串
     */
    private String buildJsonParam(String param) {
        String params = Base64.encodeToString(param.getBytes(), Base64.DEFAULT);
        String verify = md5((new StringBuilder()).append("200003a1bcdb76fc27edc37184785b333ab").append(params).toString());
        return Utils.buildJosonParam("app_id", "200003", "params", params, "verify", verify);
    }

    /**
     * 生成MD5签名,用于请求参数的完整性
     *
     * @param s
     * @return
     */
    public static String md5(String s) {
        byte bs[];
        StringBuilder sb;
        int i;
        try {
            bs = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException nosuchalgorithmexception) {
            throw new RuntimeException("Huh, MD5 should be supported?", nosuchalgorithmexception);
        } catch (UnsupportedEncodingException unsupportedencodingexception) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", unsupportedencodingexception);
        }
        sb = new StringBuilder(2 * bs.length);
        i = bs.length;
        for (int j = 0; j < i; j++) {
            byte byte0 = bs[j];
            if ((byte0 & 0xff) < 16)
                sb.append("0");
            sb.append(Integer.toHexString(byte0 & 0xff));
        }
        return sb.toString();
    }


}
