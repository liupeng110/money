package com.andlp.money;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.andlp.money.util.CrashUtil;
import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qihoo360.replugin.RePluginApplication;

import org.xutils.x;

import c.b.BP;
import cn.bmob.v3.Bmob;
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
        CrashUtil.getInstance().init(this);
        HermesEventBus.getDefault().init(this);
        x.Ext.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter() { @Override public boolean isLoggable(int priority, String tag) { return BuildConfig.DEBUG; } });
        Fragmentation.builder().stackViewMode(Fragmentation.BUBBLE).debug(BuildConfig.DEBUG) .install();
        Bmob.initialize(this, "bf4a880981a5abf1e9a45067dd2dcbc0");//初始sdk
        BP.init("bf4a880981a5abf1e9a45067dd2dcbc0");
        MobSDK.init(this,"1f033a797d7c3","bf4a880981a5abf1e9a45067dd2dcbc0");

        log();
    }

private void log(){
    Logger.w("log测试");
    Logger.i("log测试");
    Logger.e("log测试");
}





}
