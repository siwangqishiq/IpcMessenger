package com.xinlan.ipcmessenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mBtn;

    private Messenger mMessenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);

        mBtn.setEnabled(false);

        startService();
    }

    private void startService(){
        Intent it = new Intent(this,RemoteService.class);
        startService(it);

        this.bindService(it ,new ConListener(), Context.BIND_AUTO_CREATE );
    }

    @Override
    public void onClick(View v) {
        if(mMessenger == null)
            return;
        try {
            Message msg = Message.obtain();
            msg.what = 17;
            Bundle bundle = new Bundle();
            bundle.putSerializable("value" , "孪生素数猜想");
            msg.obj = bundle;
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private final class ConListener implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mBtn.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mBtn.setEnabled(false);
        }
        @Override
        public void onBindingDied(ComponentName name) {
            mMessenger = null;
            mBtn.setEnabled(false);
        }
    }//end inner class


}//end class
