package com.lvqingyang.imagestyletransfer.base;

import org.lasque.tusdk.core.TuSdkApplication;

/**
 * Created by ASUS on 2017/6/8.
 */

public class MyApplication extends TuSdkApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化SDK
        this.setEnableLog(true);
        this.initPreLoader(this.getApplicationContext(), "c2732abd7195dd3b-01-sfv0r1");
    }

}
