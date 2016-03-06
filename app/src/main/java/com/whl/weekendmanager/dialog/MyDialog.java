package com.whl.weekendmanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.whl.weekendmanager.R;
import com.whl.weekendmanager.interfacep.MyOnCancelListener;
import com.whl.weekendmanager.interfacep.OnMyItemClickListener;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:自定义提示dialog
 * 2016/3/6
 */
public class MyDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView locationTV;
    private TextView dateTV;
    private ImageView cancelIV;
    private ImageView locatinInfoTv;
    private MyOnCancelListener myOnCancelListener;
    private OnMyItemClickListener onMyItemClickListener;

    public MyDialog(Context context) {
        super(context);
        this.context = context;
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.setContentView(R.layout.dialog_layout);
        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView() {
        locationTV = (TextView) this.findViewById(R.id.dialog_tv_location);
        locationTV.setOnClickListener(this);
        locatinInfoTv = (ImageView) findViewById(R.id.dialog_tv_location_info);
        dateTV = (TextView) findViewById(R.id.dialog_tv_date);
        cancelIV = (ImageView) findViewById(R.id.dialog_iv_cancel);
        cancelIV.setOnClickListener(this);
    }

    public void setLocation(String location) {
        if (location != null) {

            locationTV.setText(location);
        }
    }

    public void setLocationInfo(String locationInfo) {
        if (locationInfo != null) {

            locatinInfoTv.setTag(locationInfo);
        }
    }

    public void setDate(String date) {
        if (date != null) {

            dateTV.setText(date);
        }
    }

    public void setOnLocationClickListener(OnMyItemClickListener onitemclick) {
        this.onMyItemClickListener = onitemclick;
    }

    public void setCancelListener(MyOnCancelListener onCancelListener) {
        if (onCancelListener != null) {

            this.myOnCancelListener = onCancelListener;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_tv_location:
                if (onMyItemClickListener != null) {
                    onMyItemClickListener.onItemClick(v, 0);
                }
                break;
            case R.id.dialog_iv_cancel:
                if (myOnCancelListener != null) {
                    this.myOnCancelListener.cancel();
                }
                break;
        }

    }
}
