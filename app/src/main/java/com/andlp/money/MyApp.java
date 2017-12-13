package com.andlp.money;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qihoo360.replugin.RePluginApplication;

import org.xutils.x;

import me.yokeyword.fragmentation.Fragmentation;
import xiaofei.library.hermeseventbus.HermesEventBus;

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
        x.Ext.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter() { @Override public boolean isLoggable(int priority, String tag) { return BuildConfig.DEBUG; } });
        HermesEventBus.getDefault().init(this);
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).debug(BuildConfig.DEBUG) .install();

//        Bmob.initialize(this, "");//初始
        log();
    }

private void log(){
    Logger.w("log测试");
    Logger.i("log测试");
    Logger.e("log测试");
}





}
