package com.lvqingyang.imagestyletransfer.User;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvqingyang.imagestyletransfer.Community.PictureFragment;
import com.lvqingyang.imagestyletransfer.Login.LoginActivity;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.BlurBitmapUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private de.hdodenhof.circleimageview.CircleImageView headcv;
    private android.widget.TextView nicktv;
    private android.support.v4.view.ViewPager vp;
    private android.support.design.widget.TabLayout tl;
    private de.hdodenhof.circleimageview.CircleImageView headimg;
    private android.widget.TextView nickname;
    private android.widget.TextView sign;
    private android.widget.LinearLayout loginlayout;
    private android.support.design.widget.AppBarLayout appbarlayout;
    private android.support.design.widget.CollapsingToolbarLayout ctl;
    private static final int REQUEST_LOGIN = 436;
    private User userInfo;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_user);
    }

    @Override
    protected void initView() {
        this.ctl = (CollapsingToolbarLayout) findViewById(R.id.ctl);
        this.vp = (ViewPager) findViewById(R.id.vp);
        this.appbarlayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        this.tl = (TabLayout) findViewById(R.id.tl);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.loginlayout = (LinearLayout) findViewById(R.id.login_layout);
        this.sign = (TextView) findViewById(R.id.sign);
        this.nickname = (TextView) findViewById(R.id.nick_name);
        this.headimg = (CircleImageView) findViewById(R.id.head_img);

        initToolbar(R.string.my,true);
        appbarlayout.setBackground(new BitmapDrawable(getResources(),
                BlurBitmapUtil.blurBitmap(this,
                        BitmapFactory.decodeResource(getResources(), R.drawable.communtiy_person),20)));
    }

    @Override
    protected void setListener() {
        appbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -loginlayout.getHeight() / 2) {
                    loginlayout.setVisibility(View.INVISIBLE);
                    ctl.setTitle(userInfo==null?"未登录":userInfo.getUsername());
                } else {
                    loginlayout.setVisibility(View.VISIBLE);
                    ctl.setTitle("");
                }
            }
        });

        loginlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo == null) {
                    startActivityForResult(new Intent(UserActivity.this, LoginActivity.class),REQUEST_LOGIN);
                }else {
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        showUserInfo();

        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tl.setupWithViewPager(vp);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    //ViewPager适配器
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            addTabs(mFragmentList,mFragmentTitleList);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //添加tab
    private void addTabs(List<Fragment> fragments, List<String> titles){
        String stringArray[]=getResources().getStringArray(R.array.tabs);
        fragments.add(PictureFragment.newInstance(0));
        titles.add(stringArray[0]);
        fragments.add(PictureFragment.newInstance(1));
        titles.add(stringArray[1]);
    }

    private void showUserInfo(){
        userInfo = BmobUser.getCurrentUser(User.class);
        if (userInfo != null) {
            if (userInfo.getHeadPortrait() != null) {
                Glide.with(this)
                        .load(userInfo.getHeadPortrait().getFileUrl())
                        .load(headimg);
            }
            nickname.setText(userInfo.getUsername());
            String s=userInfo.getSign();
            if (s==null||s.isEmpty()) {
                sign.setText(R.string.no_sign);
            }else {
                sign.setText(s);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_LOGIN) {
            if (resultCode==RESULT_OK) {
                showUserInfo();
            }
        }
    }
}
