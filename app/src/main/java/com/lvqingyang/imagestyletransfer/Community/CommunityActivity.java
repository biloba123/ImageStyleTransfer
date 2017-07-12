package com.lvqingyang.imagestyletransfer.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.adapter.MyPagerAdapter;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuAlbumComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent;

import java.util.ArrayList;
import java.util.List;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

public class CommunityActivity extends BaseActivity {

    private android.support.v4.view.ViewPager mViewPager;
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray;
    private List<Fragment> mFragments;
    private String[] mTitles;

    public static void start(Context context) {
        Intent starter = new Intent(context, CommunityActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_community);
        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mViewPager = (ViewPager) findViewById(R.id.vp);
    }

    @Override
    protected void initView() {
        initToolbar(R.string.community,true);

        //构建写好的fragment加入到viewpager中
        initFragment();
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragments,mTitles));
        //头部的图片数组
        mImageArray = new int[]{
                R.drawable.communtiy_chiose,
                R.drawable.communtiy_nature,
                R.drawable.communtiy_person,
                R.drawable.communtiy_food,
                R.drawable.communtiy_pet,
                R.drawable.communtiy_building,
                R.drawable.communtiy_life,
                R.drawable.communtiy_street,
                R.drawable.communtiy_thing,
        };

        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTitle(getString(R.string.community))
                .setImageArray(mImageArray)
                .setupWithViewPager(mViewPager);
        mCoordinatorTabLayout.setBackEnable(true);

    }

    @Override
    protected void setListener() {

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

    private void initFragment(){
        mFragments=new ArrayList<>();
        mTitles=getResources().getStringArray(R.array.titles);
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(PictureFragment.newInstance(i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_community,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }else if (item.getItemId()== R.id.item_post) {
            choosePicture();
        }
        return super.onOptionsItemSelected(item);
    }

    private void choosePicture(){
        TuAlbumComponent comp = TuSdkGeeV1.albumCommponent(this, new TuSdkComponent.TuSdkComponentDelegate()
        {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
            {
                // if (lastFragment != null)
                // lastFragment.dismissActivityWithAnim();
                TLog.d("onAlbumCommponentReaded: %s | %s", result, error);
            }
        });

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuAlbumComponentOption.html
        // comp.componentOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/album/TuAlbumListOption.html
        // comp.componentOption().albumListOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/album/TuPhotoListOption.html
        // comp.componentOption().photoListOption()

        // 设置选择照片的尺寸限制 默认：CGSize(8000,8000)
//		comp.componentOption().photoListOption().setMaxSelectionImageSize(new TuSdkSize(8000,8000));

        // 在组件执行完成后自动关闭组件
        comp.setAutoDismissWhenCompleted(true)
                // 显示组件
                .showComponent();
    }


}
