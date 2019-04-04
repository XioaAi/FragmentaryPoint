package com.zxd.xiaoabapp.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.zxd.xiaoabapp.IMyAidlInterface;
import com.zxd.xiaoabapp.R;
import com.zxd.xiaoabapp.progress_aidl.AIDLService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AIDLActivity extends AppCompatActivity {

    IMyAidlInterface iMyAidlInterface;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, AIDLService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
    }

    @OnClick(R.id.button3)
    public void onViewClicked() {
        if(iMyAidlInterface!=null){
            String name = null;
            try {
                name = iMyAidlInterface.getName("aidl - 刺激");
                Toast.makeText(this, "name=" + name, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
