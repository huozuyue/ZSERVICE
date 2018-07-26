package com.hzy.serviceActivity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.nearby.NearbyInfo;
import com.amap.api.services.nearby.NearbySearch;
import com.amap.api.services.nearby.NearbySearchFunctionType;
import com.amap.api.services.nearby.NearbySearchResult;
import com.hzy.util.RetrofitUtil;
import com.hzy.util.SharedPreferencesHelper;
import com.orhanobut.logger.Logger;

public class MyService extends Service implements NearbySearch.NearbyListener {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       // MyService.this.getApplicationContext().
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Logger.d("StartCommand LocationService" );
        nearbySearch();
        return super.onStartCommand(intent, flags, startId);

    }




    private LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，39.942295,



    public void bbb(Context context, Activity activity){
        //获取附近实例（单例模式）

        NearbySearch mNearbySearch = NearbySearch.getInstance(context.getApplicationContext());

        //设置附近监听
        mNearbySearch.addNearbyListener(this);
        //设置搜索条件
        NearbySearch.NearbyQuery query = new NearbySearch.NearbyQuery();
//设置搜索的中心点
        query.setCenterPoint(mStartPoint);
//设置搜索的坐标体系
        query.setCoordType(NearbySearch.AMAP);
//设置搜索半径 单位米 设置检索的半径，下限为0米，默认为3000米，上限为10000米，可选参数。
        query.setRadius(5000);
//设置查询的时间 设置检索的时间范围，默认从当前范围向前推移period长度的时间，单位秒级。
        query.setTimeRange(10000);
//设置查询的方式驾车还是距离
        // query.setType(NearbySearchFunctionType.DRIVING_DISTANCE_SEARCH);
        query.setType(NearbySearchFunctionType.DISTANCE_SEARCH);
//调用异步查询接口
        mNearbySearch.searchNearbyInfoAsyn(query);
        Logger.d("开始搜索");
//获取附近实例，并设置要清除用户的id
        // mNearbySearch.setUserID("test_test_test_1");
//调用异步清除用户接口
        //mNearbySearch.clearUserInfoAsyn();
        //调用销毁功能，在应用的合适生命周期需要销毁附近功能
        // NearbySearch.destroy();
    }

    @Override
    public void onUserInfoCleared(int i) {

    }

    @Override
    public void onNearbyInfoSearched(NearbySearchResult nearbySearchResult, int resultCode) {
//搜索周边附近用户回调处理
        Logger.d("搜索中。。。。。。。。。");
        if(resultCode == 1000){
            if (nearbySearchResult != null
                    && nearbySearchResult.getNearbyInfoList() != null
                    && nearbySearchResult.getNearbyInfoList().size() > 0) {
                NearbyInfo nearbyInfo = nearbySearchResult.getNearbyInfoList().get(0);
                int[] orgnoshuzu =new int[nearbySearchResult.getNearbyInfoList().size()];
                int i=0;
                for(NearbyInfo nearbyIn:nearbySearchResult.getNearbyInfoList()){
                   // nearbyIn.getUserID();
                    Logger.d("附近的订单编号为："+nearbyIn.getUserID()+
                            " getDistance 的值为："+nearbyInfo.getDistance()+ " getDrivingDistance 的值为："
                            + nearbyInfo.getDrivingDistance() + " getTimeStamp 的值为："+ nearbyInfo.getTimeStamp()
                            + " getPoint 的值为："+
                            nearbyInfo.getPoint().toString());
                    SharedPreferencesHelper.getInstance(MyService.this).putIntValue(nearbyInfo.getUserID(),nearbyInfo.getDrivingDistance() );
                    orgnoshuzu[i]=Integer.parseInt(nearbyInfo.getUserID());
                    i++;


                }
                //Logger.d("orgnoshuzu.toString() 的值为："+orgnoshuzu);
                RetrofitUtil.getOrderDetail(this,this,3,orgnoshuzu,"");
                /*mResultText.setText("周边搜索结果为size "+ nearbySearchResult.getNearbyInfoList().size() + "
                        first："+ nearbyInfo.getUserID() + "  " + nearbyInfo.getDistance()+ "  "
                                + nearbyInfo.getDrivingDistance() + "  "+ nearbyInfo.getTimeStamp() + "  "+
                                nearbyInfo.getPoint().toString());*/
              //  SharedPreferencesHelper.getInstance(MyService.this).putDoubbleValue("mylongitude",longitude);


            } else {
                Logger.d("周边搜索结果为空");
                // mResultText.setText("周边搜索结果为空");
            }
        }
        else{
            // mResultText.setText("周边搜索出现异常，异常码为："+resultCode);
            Logger.d("周边搜索出现异常，异常码为："+resultCode);
        }
    }

    @Override
    public void onNearbyInfoUploaded(int i) {

    }

    public void nearbySearch( ){
        //获取附近实例（单例模式）


//调用异步查询接口
       Double mylongitude= SharedPreferencesHelper.getInstance(MyService.this).getDoubleValue("mylongitude");
        Double mylatitude= SharedPreferencesHelper.getInstance(MyService.this).getDoubleValue("mylatitude");
       // Double mylatitude1= SharedPreferencesHelper.getInstance(MyService.this).getDoubleValue("mylatitude1");
//        Logger.d("测试如果定位失败的话mylatitude1的值为："+mylatitude1);
        if(mylongitude!=0&&mylatitude!=0){
            LatLonPoint myPoint = new LatLonPoint(mylatitude, mylongitude);
            NearbySearch mNearbySearch = NearbySearch.getInstance(MyService.this.getApplicationContext());

            //设置附近监听
            mNearbySearch.addNearbyListener(this);
            //设置搜索条件
            NearbySearch.NearbyQuery query = new NearbySearch.NearbyQuery();
//设置搜索的中心点
            query.setCenterPoint(myPoint);
//设置搜索的坐标体系
            query.setCoordType(NearbySearch.AMAP);
//设置搜索半径 单位米 设置检索的半径，下限为0米，默认为3000米，上限为10000米，可选参数。
            query.setRadius(5000);
//设置查询的时间 设置检索的时间范围，默认从当前范围向前推移period长度的时间，单位秒级。
            query.setTimeRange(10000);
//设置查询的方式驾车还是距离
            // query.setType(NearbySearchFunctionType.DRIVING_DISTANCE_SEARCH);
            query.setType(NearbySearchFunctionType.DISTANCE_SEARCH);

            mNearbySearch.searchNearbyInfoAsyn(query);
        }


        Logger.d("开始搜索");
//获取附近实例，并设置要清除用户的id
        // mNearbySearch.setUserID("test_test_test_1");
//调用异步清除用户接口
        //mNearbySearch.clearUserInfoAsyn();
        //调用销毁功能，在应用的合适生命周期需要销毁附近功能
        // NearbySearch.destroy();
    }

}
