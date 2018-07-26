package com.hzy.serviceActivity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.hzy.model.OrderInfo;
import com.hzy.model.UserOrder;
import com.hzy.util.AMapUtil;
import com.hzy.util.JsonListUtil;
import com.hzy.util.SharedPreferencesHelper;
import com.hzy.zservice.R;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzy on 2018/7/16.
 */

public class OrderServiceImpl {


    public void onAccessSuccess(String jsonstring, ContextWrapper activity, String orderDate,int num) {
        Logger.d("jsonstring  的值为："+jsonstring);
        Logger.d("orderDate  的值为："+orderDate);
        List<UserOrder> UserOrderList = new ArrayList<>();
        UserOrderList = JsonListUtil.jsonToList(jsonstring, UserOrder.class);
        List<OrderInfo> OrderInfoList = JsonListUtil.jsonToList(orderDate, OrderInfo.class);
        OrderInfo oi  ;
        UserOrder uo  ;
        //OrderInfo oi = JsonListUtil.jsonToObject(jsonstring, OrderInfo.class);
        //UserOrder uo = JsonListUtil.jsonToObject(orderDate, UserOrder.class);
      int  juliwoduoyuan;
        View bannerView;
        TextView tv_orderNO;
        TextView tv_orderStatus;
        TextView tv_orderStart;
        TextView tv_orderEnd;
        TextView tv_juliwoduoyuan;

        TextView tv_distance;
        TextView tv_shijian;
        TextView tv_cost;
        Button btn_qiangdan;
        View oneView = ((LayoutInflater) activity.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE)).inflate(R
                .layout.content_main, null);
        LinearLayout ll=oneView.findViewById(R.id.daiqiangdedingdan);
        for (int i = 0; i < UserOrderList.size(); i++) {


            bannerView = ((LayoutInflater) activity.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE)).inflate(R
                    .layout.content_order_detail, null);
            uo=UserOrderList.get(i);
            oi=OrderInfoList.get(i);
            juliwoduoyuan=  SharedPreferencesHelper.getInstance(activity).getIntValue(String.valueOf(oi.getOrderNo()) );
            tv_juliwoduoyuan = bannerView.findViewById(R.id.juliwo);
            tv_orderNO = bannerView.findViewById(R.id.orderNO);
            tv_orderStatus = bannerView.findViewById(R.id.orderStatus);
            tv_orderStart = bannerView.findViewById(R.id.orderStart);
            tv_orderEnd = bannerView.findViewById(R.id.orderEnd);
            tv_distance = bannerView.findViewById(R.id.distance);
            tv_shijian = bannerView.findViewById(R.id.shijian);
            tv_cost = bannerView.findViewById(R.id.cost);
            btn_qiangdan = bannerView.findViewById(R.id.btnqiangdan);
            tv_juliwoduoyuan.setText(juliwoduoyuan+"km");
           // tv_orderNO.setText(oi.getOrderNo());
            tv_orderStatus.setText("待抢单");
            tv_orderStart.setText(oi.getSendlocation());
            tv_orderEnd.setText(oi.getReceive_location());
            tv_distance.setText(AMapUtil.getFriendlyLength(oi.getDis()));
            tv_shijian.setText("预计(" + AMapUtil.getFriendlyTime(oi.getDur()) + "内送达!");
            tv_cost.setText("金额共计" + oi.getTaxiCost() + "元");





            ll.addView(bannerView);
        }
       /* //OrderInfo OrderInfo  =  JsonListUtil.jsonToObject(jsonstring,  OrderInfo.class);
        Intent intent = new Intent();
       // intent.setClass(activity, OrderDetail.class);
        intent.putExtra("orderDetail", jsonstring);
        intent.putExtra("orderDate", orderDate);
        Logger.d(jsonstring);
        Logger.d(orderDate);
        activity.startActivity(intent);*/



    }

    public String getStr(int[] orderNo) {

        JSONObject Order = new JSONObject();
        Gson gson = new Gson();
        String str = gson.toJson(orderNo);
        Logger.d("str的值为："+str);
        try {
            Order.put("orderNo", str);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Order.toString();
    }

    /* public String getOderStatus( int Status){
         if (0 == Status )  {
            return "待支付";
         } else if (retList.get(i).getStatus() == 1) {
             tv_orderStatus.setText("待抢单");
         } else if (retList.get(i).getStatus() == 2) {
             tv_orderStatus.setText("进行中");
         } else if (retList.get(i).getStatus() == 3) {
             tv_orderStatus.setText("已完成");

         } else {
             tv_orderStatus.setText("状态异常");
         }

     }*/
    /*public String showOrderinfo(String orderDetail, String orderDate, OrderDetail activity, int
            orderNo) {
        //activity.setContentView(R.layout.activity_order_detail);


        if (null != orderDetail && null != orderDate && !"".equals(orderDetail) && !"".equals
                (orderDate)) {

            OrderInfo oi = JsonListUtil.jsonToObject(orderDetail, OrderInfo.class);
            UserOrder uo = JsonListUtil.jsonToObject(orderDate, UserOrder.class);
            TextView tv_orderNO;
            TextView tv_orderStatus;
            TextView tv_orderDate;
            TextView tv_orderStart;
            TextView tv_orderEnd;
            TextView tv_send_address;
            TextView tv_send_name;
            TextView tv_send_phone;
            TextView tv_receivieaddress;
            TextView tv_receive_name;
            TextView tv_receive_phone;
            TextView tv_distance;
            TextView tv_shijian;
            TextView tv_cost;
            Button btn_zhifu;
           *//* tv_orderNO = activity.findViewById(R.id.orderNO);
            tv_orderStatus = activity.findViewById(R.id.orderStatus);
            tv_orderDate = activity.findViewById(R.id.orderDate);
            tv_orderStart = activity.findViewById(R.id.orderStart);
            tv_orderEnd = activity.findViewById(R.id.orderEnd);
            tv_send_address = activity.findViewById(R.id.send_address);
            tv_send_name = activity.findViewById(R.id.send_name);
            tv_send_phone = activity.findViewById(R.id.send_phone);
            tv_receivieaddress = activity.findViewById(R.id.receivieaddress);
            tv_receive_name = activity.findViewById(R.id.receive_name);
            tv_receive_phone = activity.findViewById(R.id.receive_phone);
            tv_distance = activity.findViewById(R.id.distance);
            tv_shijian = activity.findViewById(R.id.shijian);
            tv_cost = activity.findViewById(R.id.cost);
            btn_zhifu = activity.findViewById(R.id.zhifu);
            tv_orderNO.setText(oi.getOrderNo());
            tv_orderStatus.setText(uo.getStatus());
            tv_orderDate.setText(uo.getCommittime());
            tv_orderStart.setText(oi.getSendlocation());
            tv_orderEnd.setText(oi.getReceive_location());
            tv_send_address.setText(oi.getDetail_sendstr());
            tv_send_name.setText(oi.getName_sendstr());
            tv_send_phone.setText(oi.getTel_sendstr());
            tv_receivieaddress.setText(oi.getReceive_addressstr());
            tv_receive_name.setText(oi.getReceive_namestr());
            tv_receive_phone.setText(oi.getReceive_phonestr());
            tv_distance.setText(AMapUtil.getFriendlyLength(oi.getDis()));
            tv_shijian.setText("预计(" + AMapUtil.getFriendlyTime(oi.getDur()) + "内送达!");*//**//*
            tv_cost.setText("金额共计" + oi.getTaxiCost() + "元");
            Logger.d("金额共计" + oi.getTaxiCost() + "元");*//*


        }


        return "";
    }*/
}
