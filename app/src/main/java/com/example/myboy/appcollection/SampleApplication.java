package com.example.myboy.appcollection;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class SampleApplication extends TinkerApplication {
    public SampleApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.example.myboy.appcollection.cardgame.activity.SampleApplicationLike",
            "com.tencent.tinker.loader.TinkerLoader", true);
    }
}
