package com.lvqingyang.imagestyletransfer;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lvqingyang.imagestyletransfer.Community.CommunityActivity;
import com.lvqingyang.imagestyletransfer.Setting.SettingActivity;
import com.lvqingyang.imagestyletransfer.User.UserActivity;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.tool.ComponentHelper;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.seles.tusdk.FilterManager;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mCameraIv;
    private ImageView mPhotoIv;
    private ImageView mCommunityIv;
    private ImageView mSettingIv;
    private ImageView mUserIv;
    private static final String TAG = "MainActivity";

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        // 异步方式初始化滤镜管理器
        // 需要等待滤镜管理器初始化完成，才能使用所有功能
        TuSdk.messageHub().setStatus(this, R.string.lsq_initing);
        TuSdk.checkFilterManager(mFilterManagerDelegate);

        mCameraIv = (ImageView) findViewById(R.id.camera_iv);
        mPhotoIv = (ImageView) findViewById(R.id.photo_iv);
        mCommunityIv = (ImageView) findViewById(R.id.communtiy_iv);
        mSettingIv = (ImageView) findViewById(R.id.setting_iv);
        mUserIv = (ImageView) findViewById(R.id.user_iv);
    }

    @Override
    protected void setListener() {
        mCameraIv.setOnClickListener(this);
        mPhotoIv.setOnClickListener(this);
        mCommunityIv.setOnClickListener(this);
        mUserIv.setOnClickListener(this);
        mSettingIv.setOnClickListener(this);
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

    private FilterManager.FilterManagerDelegate mFilterManagerDelegate = new FilterManager.FilterManagerDelegate()
    {
        @Override
        public void onFilterManagerInited(FilterManager manager)
        {
            TuSdk.messageHub().showSuccess(MainActivity.this, R.string.lsq_inited);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_iv:
                ComponentHelper.showPhotoEdit(this);
                break;
            case R.id.camera_iv:
                ComponentHelper.showCamera(this);
                break;
            case R.id.communtiy_iv:
                CommunityActivity.start(this);
                break;
            case R.id.user_iv:
                UserActivity.start(this);
                break;
            case R.id.setting_iv:
                SettingActivity.start(this);
                break;
        }
    }
}
