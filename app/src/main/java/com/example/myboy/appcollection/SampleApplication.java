package com.example.myboy.appcollection;

import android.content.Context;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import androidx.multidex.MultiDex;

public class SampleApplication extends TinkerApplication {
    /**
     * 为了规避64K问题   解决办法三种 1、设置Application为MultiDexApplication  2、自定义的Application继承MultiDexApplication 3、就是下面这种
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.myboy.appcollection.cardgame.activity.SampleApplicationLike",
            "com.tencent.tinker.loader.TinkerLoader", true);
    }
}
