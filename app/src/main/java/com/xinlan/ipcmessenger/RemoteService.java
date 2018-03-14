package com.xinlan.ipcmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import java.util.logging.Logger;

/**
 * Created by panyi on 2018/3/14.
 */

public class RemoteService extends Service {
    private Messenger mMessenger;

    private RemoteHandler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new RemoteHandler(Looper.getMainLooper());
        mMessenger = new Messenger(mHandler);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger != null ? mMessenger.getBinder() : null;
    }

    private final class RemoteHandler extends Handler {
        public RemoteHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = (Bundle)msg.obj;
            String val = (String)bundle.getSerializable("value");
            System.out.println("receive msg = " + msg.what);
            System.out.println("receive msg obj = " + val);
        }
    }//end inner class
}//end class
