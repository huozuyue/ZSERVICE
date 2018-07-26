package com.hzy.serviceActivity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class MyServicea extends Service {
    public MyServicea() {
    }



    private DownLoadBinder downLoadBinder=new DownLoadBinder();
    /**
     * 回调
     */
    private Callback callback;
    /**
     * Timer实时更新数据的
     */
    private Timer mTimer=new Timer();
    /**
     *
     */
    private int num;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        System.out.println("=====onBind=====");
        return downLoadBinder;
    }

    /**
     * 内部类继承Binder
     * @author lenovo
     *
     */
    public class DownLoadBinder extends Binder {
        /**
         * 声明方法返回值是MyService本身
         * @return
         */
        public MyServicea getService() {
            return MyServicea.this;
        }
    }
    /**
     * 服务创建的时候调用
     */
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        /*
         * 执行Timer 2000毫秒后执行，5000毫秒执行一次
         */
        mTimer.schedule(task, 0, 1000);
    }

    /**
     * 提供接口回调方法
     * @param callback
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     *
     */
    TimerTask task = new TimerTask(){

        @Override
        public void run() {
            // TODO Auto-generated method stub
            num++;
            if(callback!=null){
                /*
                 * 得到最新数据
                 */
                callback.getNum(num);
            }

        }

    };


    /**
     * 回调接口
     *
     * @author lenovo
     *
     */
    public static interface Callback {
        /**
         * 得到实时更新的数据
         *
         * @return
         */
        void getNum(int num);
    }
    /**
     * 服务销毁的时候调用
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("=========onDestroy======");
        /**
         * 停止Timer
         */
        mTimer.cancel();
        super.onDestroy();
    }
}
