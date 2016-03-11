package com.whl.weekendmanager.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.whl.weekendmanager.MainActivity;
import com.whl.weekendmanager.R;

public class SpalashActivity extends Activity {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            startActivity(intent);
            finish();
        }
    };

    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palash_activity_);
        intent=new Intent(this, MainActivity.class);
        handler.sendEmptyMessageDelayed(0, 2000);
    }
}
