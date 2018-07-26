package com.hzy.serviceActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.hzy.util.SharedPreferencesHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Created by hzy on 2018/7/22.
 */

public class LocationService extends Service {
    private static final String TAG = "LocationService";


    //声明AMapLocationClient类对象
    AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        //Logger.d("LocationService" );
        Log.i(TAG, "start LocationService!");

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "StartCommand LocationService!");
        //Logger.d("StartCommand LocationService" );
        getPosition();
        //LocationService.this.getApplicationContext();
        //startActivity(intent);
        return super.onStartCommand(intent, flags, startId);

    }





    public void getPosition(){
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        //Logger.d("开始定位" );
    }
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener(){

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if(amapLocation==null){
                Log.i(TAG, "amapLocation is null!");
                return;
            }
            if(amapLocation.getErrorCode()!=0){
                Log.i(TAG, "amapLocation has exception errorCode:"+amapLocation.getErrorCode());
                return;
            }
            Logger.d("dingwei:"+amapLocation.getAddress() );

            Double longitude = amapLocation.getLongitude();//获取经度
            Double latitude = amapLocation.getLatitude();//获取纬度
            //Logger.d("longitude的值为："+longitude );
            //Logger.d("latitude的值为："+latitude );
            SharedPreferencesHelper.getInstance(LocationService.this).putDoubbleValue("mylongitude",longitude);
            SharedPreferencesHelper.getInstance(LocationService.this).putDoubbleValue("mylatitude",latitude);
            SharedPreferencesHelper.getInstance(LocationService.this).putStringValue("myAddress",amapLocation.getAddress());

            // String longitudestr = String.valueOf(longitude);
            //String latitudestr = String.valueOf(latitude);
           /* SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());
            String timestr = df.format(date);
            Log.i(TAG, "longitude,latitude:"+longitude+","+latitude);
            Log.i(TAG, "time:"+timestr);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("longitude", longitudestr);
            data.putString("latitude", latitudestr);
            data.putString("timestr", timestr);
            msg.setData(data);
            msg.what = 0x1;
            netHandler.sendMessage(msg);*/
        }

    };



    private String getMac() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str;) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }




}
