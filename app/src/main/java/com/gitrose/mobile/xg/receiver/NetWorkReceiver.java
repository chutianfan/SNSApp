package com.gitrose.mobile.xg.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.gitrose.mobile.RequestService;
//import shouji.gexing.framework.utils.NetworkUtils;

public class NetWorkReceiver extends BroadcastReceiver {
    public final String CONNECTIVITY_CHANGE_ACTION;

    public NetWorkReceiver() {
        this.CONNECTIVITY_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    }

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
//        if (action != null && action.equals("android.net.conn.CONNECTIVITY_CHANGE") && NetworkUtils.isWifi(context)) {
//            context.startService(new Intent(context, RequestService.class));
//        }
    }
}
