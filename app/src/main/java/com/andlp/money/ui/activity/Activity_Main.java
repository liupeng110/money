package com.andlp.money.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.andlp.money.R;
import com.orhanobut.logger.Logger;

import c.b.BP;
import c.b.PListener;
import c.b.QListener;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class Activity_Main extends AppCompatActivity {
    EditText editText ;
    String phone;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit);
        SMSSDK.registerEventHandler(eventHandler);
    }



    public void txt(View view){
        Logger.i("点击txt--");
//        pay(BP.PayType_QQ);


    }


    public void get(View view){


    }
    public void send(View view){

    }
    public void vali(View view){


    }


    private void sendCode(String phone){
        SMSSDK.getVerificationCode("86", phone, "123456", new OnSendMessageHandler() {
            @Override public boolean onSendMessage(String s, String s1) {
                Logger.i("发送后："+s+",----s1:"+s1);
                return false;
            }
        });
    }
    private void VerificationCode(String phone,String code){
        SMSSDK.submitVerificationCode("86", phone, code);
    }

   //bmob 支付相关  10%
    void query() {
        Logger.i("开始查询 ");
        final String orderId = getOrder();
        BP.query(orderId, new QListener() {

            @Override
            public void succeed(String status) {
                Logger.i("pay status of" + orderId + " is " + status + "\n\n");
            }

            @Override
            public void fail(int code, String reason) {
                Logger.i("query order fail, error code is " + code+ " ,reason is \n" + reason + "\n\n");
            }
        });
    }
    double getPrice() {
        double price = 0.02;
        try {
            price = Double.parseDouble("0.01");
        } catch (NumberFormatException e) {
        }
        return price;
    }     // 默认为0.02
    String getName() {
        return "名字";
    }// 商品详情(可不填)
    String getBody() {
        return "描述";
    }  // 商品详情(可不填)
    String getOrder() {
        return "10086";
    }// 支付订单号(查询时必填)
    void pay(final int payType) {

        final String name = getName();

        // 仍然可以通过这种方式支付，其中true为支付宝，false为微信
        // BP.pay(name, getBody(), getPrice(), false, new PListener());

        BP.pay(name, getBody(), getPrice(), payType, new PListener() {
            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Logger.i(name + "'s pay status is success\n\n");
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                Logger.i(name + "'s orderid is " + orderId + "\n\n");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                Logger.i(name + "'s pay status is fail, error code is \n" + code + " ,reason is " + reason + "\n\n");
            }
        });
    }    //支付类型，BP.PayType_Alipay、BP.PayType_Wechat、BP.PayType_QQ
   //支付相关


    //smss
    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            if (data instanceof Throwable) {
                Throwable throwable = (Throwable)data;
                String msg = throwable.getMessage();
                Logger.i(msg);
            } else {
                if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //验证成功
                    Logger.i("success--"+data);
                }else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//提交验证码
                    Logger.i("submit--"+data);
                }   else if (event == SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE) {
                    Logger.i("voice--"+data);
                }
            }

            }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
