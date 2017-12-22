package com.andlp.money.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.andlp.money.R;
import com.andlp.money.ui.ma.Shell;
import com.orhanobut.logger.Logger;


import org.xutils.x;

//import c.b.BP;
//import c.b.PListener;
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



//        String[] commands = new String[] { "mount -o rw,remount /sdcard", "cp /mnt/sdcard/aaa.ape /mnt/sdcard/copy.ape" };//挂在复制文件

        x.task().run(() -> {
            String[] commands = new String[] {"echo  666"};
            Shell.CommandResult result = Shell.execCommand(commands, false);
            Logger.i(result.successMsg);
            Logger.i(result.errorMsg);
        });

    }


    public void get(View view){

    }
    public void send(View view){

    }
    public void vali(View view){


    }

    //pay
    public void pay(String name,String des,double money,int payType){
//        BP.pay("商品名称", "商品描述", 0.02, BP.PayType_Alipay, new PListener() {
//            @Override public void orderId(String s) { Logger.i("结果："+s); }
//            @Override public void succeed() { Logger.i("结果succeed"); }
//            @Override public void fail(int i, String s) {  Logger.i(i+"结果：fail,"+s);  }
//        });
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
