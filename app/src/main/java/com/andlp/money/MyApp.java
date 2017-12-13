package com.andlp.money;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.qihoo360.replugin.RePluginApplication;

/**
 * 717219917@qq.com      2017/12/13  15:48
 */

public class MyApp extends RePluginApplication {

    @Override protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    } //多 dex 支持  不影响继承其他application
    @Override public void onCreate() {
        super.onCreate();


    }



}
