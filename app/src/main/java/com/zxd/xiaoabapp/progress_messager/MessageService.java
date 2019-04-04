package com.zxd.xiaoabapp.progress_messager;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class MessageService extends Service{

    class InnerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Toast.makeText(getApplicationContext(),"使用Messenger跨进程通信成功",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    Messenger messenger = new Messenger(new InnerHandler());


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
