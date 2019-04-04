package com.zxd.xiaoabapp.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.progress_messager.MessageService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessengerActivity extends AppCompatActivity {

    boolean isConn = false;
    Messenger messenger;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            isConn = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConn = false;
        }
    };
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, MessageService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isConn){
            unbindService(serviceConnection);
            isConn = false;
        }
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {
        if(isConn){
            Message message = Message.obtain(null,0,0,0);
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
