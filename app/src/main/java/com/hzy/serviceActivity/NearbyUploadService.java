package com.hzy.serviceActivity;

import android.app.Activity;
import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.nearby.NearbySearch;
import com.amap.api.services.nearby.NearbySearchResult;
import com.amap.api.services.nearby.UploadInfo;
import com.amap.api.services.nearby.UploadInfoCallback;
import com.hzy.util.SharedPreferencesHelper;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by hzy on 2018/7/22.
 */

public class NearbyUploadService implements NearbySearch.NearbyListener {
    private LatLonPoint mStartPoint  ;//起点，39.942295,
    public void aaa(Context context, Activity activity){
        Logger.addLogAdapter(new AndroidLogAdapter());
        Double mylongitude= SharedPreferencesHelper.getInstance(context).getDoubleValue("mylongitude");
        Double mylatitude= SharedPreferencesHelper.getInstance(context).getDoubleValue("mylatitude");
        if(mylongitude!=0&&mylatitude!=0) {
            mStartPoint = new LatLonPoint(mylatitude, mylongitude);//起点，39.942295,
            Logger.d("信息上传" );
//附近派单初始化操作。
            NearbySearch mNearbySearch = NearbySearch.getInstance(context.getApplicationContext());
//信息上传
            mNearbySearch.startUploadNearbyInfoAuto(new UploadInfoCallback() {
                //设置自动上传数据和上传的间隔时间
                @Override
                public UploadInfo OnUploadInfoCallback() {
                    UploadInfo loadInfo = new UploadInfo();
                    loadInfo.setCoordType(NearbySearch.AMAP);

                    //位置信息
                    loadInfo.setPoint(mStartPoint);
                    //用户id信息
                    loadInfo.setUserID("90");
                    // Logger.d("信息上传");
                    return loadInfo;
                }
            }, 2000);


            //构造上传位置信息
            UploadInfo loadInfo = new UploadInfo();
//设置上传位置的坐标系支持AMap坐标数据与GPS数据
            loadInfo.setCoordType(NearbySearch.AMAP);
//设置上传数据位置,位置的获取推荐使用高德定位sdk进行获取
            loadInfo.setPoint(mStartPoint);
//设置上传用户id
            loadInfo.setUserID("用户的id");
//调用异步上传接口
            mNearbySearch.uploadNearbyInfoAsyn(loadInfo);
        }
    }


    @Override
    public void onUserInfoCleared(int i) {

    }

    @Override
    public void onNearbyInfoSearched(NearbySearchResult nearbySearchResult, int i) {

    }

    @Override
    public void onNearbyInfoUploaded(int i) {

    }
}
