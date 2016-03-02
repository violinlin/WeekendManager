package com.whl.weekendmanager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.whl.weekendmanager.adapter.MainVPAdapter;
import com.whl.weekendmanager.fragment.AreaFragment;
import com.whl.weekendmanager.fragment.BaseFragment;
import com.whl.weekendmanager.fragment.CommunityFragment;
import com.whl.weekendmanager.fragment.LabelFragment;
import com.whl.weekendmanager.fragment.NearFragment;
import com.whl.weekendmanager.netcontrol.NetControl;
import com.whl.weekendmanager.util.Utils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        ViewPager viewPager= (ViewPager) findViewById(R.id.viewpager);
        List<BaseFragment> datas=new LinkedList<>();
        datas.add(new CommunityFragment());
        datas.add(new NearFragment());
        datas.add(new AreaFragment());
        datas.add(new LabelFragment());
        viewPager.setAdapter(new MainVPAdapter(getSupportFragmentManager(),datas));

    }

    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public void click(View view) {

        final String url = "http://apiv30.chengmi.com/v29/index/main";
        final String json = "{\"app_id\":\"200003\",\"params\":\"eyJhY2Nlc3NfdG9rZW4iOiIiLCJjdXJsbmciOiIxMTQuMDY0MzQ5" +
                "IiwiY3VybGF0IjoiMjIuNTQw\\nNDgzIiwiY2l0eV9pZCI6MTEsImN1cnBhZ2UiOjEsInBlcnBhZ2UiOjIwLCJ2ZXJzaW9uIjoidjI5\\nOCJ9\\n\",\"verify\":\"8f5730ea3afe4b2b7f272e8f9f557b9e\"}";

//        NetControl.postAsyn("v29/index/forum", new NetControl.StringCallback() {//index/forum HTTP/1.1
//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.d("whl", request.toString());
//            }
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("whl", response.toString());
//            }
//        }, Utils.buildJosonParam("access_token", "", "curlng", "116.370245", "curlat", "40.036975", "city_id", 11, "curpage", 1, "perpage", 20));

    }

    public void base64click(View view) {
        String str = "eyJhY2Nlc3NfdG9rZW4iOiIiLCJjdXJsbmciOiIxMTYuMzcwMjQ1IiwiY3VybGF0IjoiNDAuMDM2\n" +
                "    OTc1IiwiY2l0eV9pZCI6MTEsImN1cnBhZ2UiOjEsInBlcnBhZ2UiOjIwfQ==";
        byte[] params = Base64.decode(str.getBytes(), Base64.DEFAULT);
        Log.d("whl", new String(params));

    }
}
