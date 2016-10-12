package com.example.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.startserviceanother.AppServiceInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
private Intent serviceIntent;
private EditText etInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etInput = (EditText) findViewById(R.id.etInput);
        serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("com.example.startserviceanother","com.example.startserviceanother.MyService"));
        findViewById(R.id.btnStart).setOnClickListener(this);
        findViewById(R.id.btnStop).setOnClickListener(this);
        findViewById(R.id.btnBindStart).setOnClickListener(this);
        findViewById(R.id.btnUnBindStop).setOnClickListener(this);
        findViewById(R.id.btnSync).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStart:
                //以下三行就是启动另一个APP Service的流程
//                Intent intent = new Intent();
//                intent.setComponent(new ComponentName("com.example.startserviceanother","com.example.startserviceanother.MyService"));
//                startService(intent);
                startService(serviceIntent);
                break;
            case R.id.btnStop:
                stopService(serviceIntent);
                break;
            case R.id.btnBindStart:
                bindService(serviceIntent,this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnBindStop:
                unbindService(this);
                binder = null;
                break;
            case R.id.btnSync:
                if (binder != null){
                    try {
                        binder.setData(etInput.getText().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        System.out.println("Bind Service绑定服务");
        System.out.println(service);
        binder = AppServiceInterface.Stub.asInterface(service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
    private AppServiceInterface binder = null;
}
