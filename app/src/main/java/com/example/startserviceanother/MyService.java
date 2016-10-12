package com.example.startserviceanother;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new AppServiceInterface.Stub() {
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

            }

            @Override
            public void setData(String data) throws RemoteException {
                MyService.this.data = data;
            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("正在启动--->OnStart");
        new Thread(new Runnable() {
            @Override
            public void run() {
                running = true;
                while (running){
                    System.out.println(data);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("已经销毁--->OnDestroy");
        running = false;
    }
    private String data = "默认数据";
    private boolean running = false;
}
