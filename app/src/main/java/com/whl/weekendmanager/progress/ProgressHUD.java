package com.whl.weekendmanager.progress;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.whl.weekendmanager.R;

import java.util.HashMap;
import java.util.Map;

/**
 * ProjectName : com.whl.weekendmanager
 * Created by : whl
 * DES:
 * 2016/3/5
 */
public class ProgressHUD {

    private Dialog dialog;
    private int retaincount;

    private ProgressHUD(Context context) {
        dialog = new Dialog(context);// 创建自定义样式dialog
        dialog.setContentView(View.inflate(context, R.layout.progress_hud_view, null));
        dialog.setCanceledOnTouchOutside(true);
    }

    private static Map<Context, ProgressHUD> progressHUDMap;

    public static synchronized ProgressHUD getInstance(Context context) {
        if (progressHUDMap == null) {
            progressHUDMap = new HashMap<>();
        }
        ProgressHUD progressHUD;
        if (progressHUDMap.containsKey(context)) {
            progressHUD = progressHUDMap.get(context);
        }else {
            progressHUD = new ProgressHUD(context);
            progressHUDMap.put(context, progressHUD);
        }
        return progressHUD;
    }

    public void show() {
        if (dialog.isShowing()) {
            retaincount++;
        } else {
            retaincount = 1;
            dialog.show();
        }
    }

    public void cancel() {
        if (dialog.isShowing()) {
            retaincount--;
            if (retaincount <= 0) {
                dialog.cancel();
            }
        }
    }
}

