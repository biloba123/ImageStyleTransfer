package com.lvqingyang.imagestyletransfer.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.view.SettingItem;

public class SettingActivity extends BaseActivity {


    private SettingItem mCameraSi;
    private SettingItem mFeedbackSi;

    public static void start(Context context) {
        Intent starter = new Intent(context, SettingActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    protected void initView() {
        initToolbar(R.string.setting,true);
        mCameraSi = (SettingItem) findViewById(R.id.camera_si);
        mFeedbackSi = (SettingItem) findViewById(R.id.feedback_si);
    }

    @Override
    protected void setListener() {
        mFeedbackSi.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackActivity.start(SettingActivity.this);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
