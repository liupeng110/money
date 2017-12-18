package com.andlp.money.ui.ma;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.orhanobut.logger.Logger;

import org.xutils.x;

/**
 * 717219917@qq.com      2017/12/18  15:07
 */

public class SmsUtil {



    public static void  receive(String str){

    }

    public static void getAllSms(){

        ContentResolver cr = x.app().getApplicationContext().getContentResolver();  //访问内容提供者获取短信
        Cursor cursor = cr.query(Uri.parse("content://sms"),  // 全部短信
                new String[]{"address", "date", "body", "type"},
                null, null, null);

        assert cursor != null;
        while(cursor.moveToNext()){
            String address = cursor.getString(0);
            long     date     = cursor.getLong(1);
            String body     = cursor.getString(2);
            String type      = cursor.getString(3);

            Logger.i( address+",body:"+body+",type:"+type+",date:"+date);
        }
        cursor.close();

    }

    public static void getInboxSms(){

    }//未

    public static void getSendSms(){

    }//已

    public static void getDrafrSms(){

    }//草稿

    public static void getContext(){

    }



}
