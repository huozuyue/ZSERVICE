package com.hzy.zservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hzy.serviceActivity.MyService;
import com.hzy.serviceActivity.MyServicea;

public class testService extends AppCompatActivity implements View.OnClickListener {
    private MyServicea.DownLoadBinder downLoadBinder;
    private MyServicea myService;  //我们自己的service
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);
        Button btn_start = (Button) findViewById(R.id.btn_start);
        Button btn_stop = (Button) findViewById(R.id.btn_stop);
        Button btn_bind = (Button) findViewById(R.id.btn_bind);
        Button btn_unbind = (Button) findViewById(R.id.btn_unbind);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_bind.setOnClickListener(this);
        btn_unbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
        /*
         * 开启服务点击事件
         */
            case R.id.btn_start:
                Intent startIntent = new Intent(this, MyServicea.class);
                startService(startIntent);
                break;
        /*
         * 停止服务点击事件
         */
            case R.id.btn_stop:
                Intent stopIntent = new Intent(this, MyServicea.class);
                stopService(stopIntent);
                break;
        /*
         * 绑定服务点击事件
         */
            case R.id.btn_bind:
                Intent bindIntent = new Intent(this, MyServicea.class);
                bindService(bindIntent, connection, BIND_AUTO_CREATE);
                break;
        /*
         * 解除绑定服务点击事件
         */
            case R.id.btn_unbind:
                unbindService(connection);
                break;

            default:
                break;
        }
    }
    private ServiceConnection connection=new ServiceConnection() {
        /**
         * 服务解除绑定时候调用
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }
        /**
         * 绑定服务的时候调用
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            downLoadBinder = (MyServicea.DownLoadBinder) service;
            MyServicea service2 = downLoadBinder.getService();
            /**
             * 实现回调，得到实时刷新的数据
             */
            service2.setCallback(new MyServicea.Callback() {

                @Override
                public void getNum(int num) {
                    // TODO Auto-generated method stub
                    System.out.println("====num===="+num);
                }
            });
        }
    };
}
