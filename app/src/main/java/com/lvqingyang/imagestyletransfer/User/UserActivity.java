package com.lvqingyang.imagestyletransfer.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lvqingyang.imagestyletransfer.Community.PictureFragment;
import com.lvqingyang.imagestyletransfer.Community.PostPictureFragment;
import com.lvqingyang.imagestyletransfer.Login.LoginActivity;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.BlurBitmapUtil;

import org.lasque.tusdk.core.TuSdk;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends BaseActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private android.support.v4.view.ViewPager vp;
    private android.support.design.widget.TabLayout tl;
    private de.hdodenhof.circleimageview.CircleImageView headimg;
    private android.widget.TextView nickname;
    private android.widget.TextView sign;
    private android.widget.LinearLayout loginlayout;
    private android.support.design.widget.AppBarLayout appbarlayout;
    private android.support.design.widget.CollapsingToolbarLayout ctl;
    private static final int REQUEST_LOGIN = 436;
    private static final int REQUEST_INFO = 832;
    private static final String TAG = "UserActivity";
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

        headimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo != null) {
                    startActivityForResult(new Intent(UserActivity.this, UserInfoActivity.class),REQUEST_INFO);
                }
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInfo != null) {
                    showEditSignDialog();
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
    class ViewPagerAdapter extends FragmentStatePagerAdapter {
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
        fragments.add(PictureFragment.newInstance(PictureFragment.TYPE_POSTED));
        titles.add(stringArray[0]);
        fragments.add(PostPictureFragment.newInstance());
        titles.add(stringArray[1]);
    }

    private void showUserInfo(){
        userInfo = BmobUser.getCurrentUser(User.class);
        if (userInfo != null) {
            if (userInfo.getAvater()!=null) {
                Log.d(TAG, "showUserInfo: "+userInfo.getAvater());
                Glide.with(this)
                        .load(userInfo.getAvater())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                                Bitmap bitmap=BlurBitmapUtil.drawableToBitmap(drawable);
                                appbarlayout.setBackground(new BitmapDrawable(
                                        BlurBitmapUtil.blurBitmap(UserActivity.this,
                                                Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),(int)(bitmap.getHeight()*0.8)),3f)));
                                return false;
                            }
                        })
                        .into(headimg);
            }else {
                loadDefaultHead();
            }
            nickname.setText(userInfo.getUsername());
            String s=userInfo.getSign();
            if (s==null||s.isEmpty()) {
                sign.setText(R.string.no_sign);
            }else {
                sign.setText(s);
            }
        }else {
            nickname.setText(R.string.unlogin);
            sign.setText("");
            loadDefaultHead();
        }
    }

    private void loadDefaultHead(){
        Glide.with(this)
                .load(R.drawable.communtiy_person)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                        Bitmap bitmap=BlurBitmapUtil.drawableToBitmap(drawable);
                        appbarlayout.setBackground(new BitmapDrawable(
                                BlurBitmapUtil.blurBitmap(UserActivity.this,
                                        Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),(int)(bitmap.getHeight()*0.8)),3f)));
                        return false;
                    }
                })
                .into(headimg);
    }

    private void showEditSignDialog(){
        View view=getLayoutInflater().inflate(R.layout.dialog_edit_sign,null);
        //inite view and show data
        final EditText signEt= (EditText) view.findViewById(R.id.sign);
        new AlertDialog.Builder(this)
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User bmobUser = BmobUser.getCurrentUser(User.class);
                        User newUser=new User();
                        newUser.setSign(signEt.getText().toString());
                        newUser.update(bmobUser.getObjectId(),new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    sign.setText(signEt.getText().toString());
                                    //更新本地数据
                                    User.fetchUserInfo();
                                    TuSdk.messageHub().showSuccess(UserActivity.this, R.string.reset_sign_succ);
                                }else{
                                    Log.d(TAG, "更新用户信息失败:" + e.getMessage());
                                    TuSdk.messageHub().showError(UserActivity.this, R.string.reset_sign_fail);
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.cancel,null)
                .create()
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_LOGIN) {
            if (resultCode==RESULT_OK) {
                showUserInfo();
            }
        }else if (requestCode==REQUEST_INFO) {
            if (resultCode==RESULT_OK) {
                showUserInfo();
            }
        }
    }
}
