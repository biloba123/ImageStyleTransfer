package com.lvqingyang.imagestyletransfer.User;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.view.InfoItem;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;

import org.lasque.tusdk.TuSdkGeeV1;
import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.TuSdkResult;
import org.lasque.tusdk.core.utils.TLog;
import org.lasque.tusdk.impl.activity.TuFragment;
import org.lasque.tusdk.impl.components.TuAvatarComponent;
import org.lasque.tusdk.modules.components.TuSdkComponent;

import java.io.File;
import java.util.Arrays;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserInfoActivity extends BaseActivity {


    private de.hdodenhof.circleimageview.CircleImageView headimg;
    private android.widget.Button postheadbtn;
    private User userInfo;
    private static final String TAG = "UserInfoActivity";
    private boolean mIsReset;
    private com.lvqingyang.imagestyletransfer.view.InfoItem accountitem;
    private com.lvqingyang.imagestyletransfer.view.InfoItem pwditem;
    private com.lvqingyang.imagestyletransfer.view.InfoItem nickitem;
    private Button mLogoutBtn;

    public static void start(Context context) {
        Intent starter = new Intent(context, UserInfoActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_user_info);
    }

    @Override
    protected void initView() {
        initToolbar(R.string.user_info,true);
        this.postheadbtn = (Button) findViewById(R.id.post_head_btn);
        this.headimg = (CircleImageView) findViewById(R.id.head_img);
        this.nickitem = (InfoItem) findViewById(R.id.nick_item);
        this.pwditem = (InfoItem) findViewById(R.id.pwd_item);
        this.accountitem = (InfoItem) findViewById(R.id.account_item);
        mLogoutBtn = (Button) findViewById(R.id.logout_btn);

        accountitem.setIcon(getIcon(GoogleMaterial.Icon.gmd_contact_mail, Color.parseColor("#b3b3b3")));
        nickitem.setIcon(getIcon(GoogleMaterial.Icon.gmd_account_circle, Color.parseColor("#b3b3b3")));
        pwditem.setIcon(getIcon(GoogleMaterial.Icon.gmd_visibility, Color.parseColor("#b3b3b3")));
    }

    @Override
    protected void setListener() {
        //修改昵称
        nickitem.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetNickDialog();
            }
        });
        //修改密码
        pwditem.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetPwdDialog();
            }
        });

        postheadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAvatar();
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsReset=true;
                BmobUser.logOut();   //清除缓存用户对象
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        userInfo = BmobUser.getCurrentUser(User.class);
    }

    @Override
    protected void setData() {
        showUserInfo();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void showUserInfo(){
        if (userInfo != null) {
            if (userInfo.getAvater()!=null) {
                Glide.with(this)
                        .load(userInfo.getAvater())
                        .into(headimg);
            }else {
                Glide.with(this)
                        .load(R.drawable.communtiy_person)
                        .into(headimg);
            }
            accountitem.setContent(userInfo.getEmail());
            nickitem.setContent(userInfo.getUsername());
        }
    }

    private void showResetNickDialog(){
        View v= LayoutInflater.from(this)
                .inflate(R.layout.dialog_reset_nick,null);
        final EditText nameEt = (EditText) v.findViewById(R.id.name);
        nameEt.setText(nickitem.getContent());
        new AlertDialog.Builder(this)
                .setView(v)
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BmobUser bmobUser = BmobUser.getCurrentUser();
                        bmobUser.setUsername(nameEt.getText().toString());
                        bmobUser.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    mIsReset=true;
                                    TuSdk.messageHub().showSuccess(UserInfoActivity.this, R.string.reset_nick_succ);
                                    nickitem.setContent(nameEt.getText().toString());
                                }else{
                                    Log.d(TAG, "更新用户信息失败:" + e.getMessage());
                                    TuSdk.messageHub().showError(UserInfoActivity.this, getString(R.string.reset_nick_fail)
                                            +"："+e.getMessage());
                                }
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.cancel,null)
                .show();
    }

    private void showResetPwdDialog(){
        View v= LayoutInflater.from(this)
                .inflate(R.layout.dialog_reset_pwd,null);
        final EditText curPwdEt= (EditText) v.findViewById(R.id.current_pwd);
        final EditText newPwdEt= (EditText) v.findViewById(R.id.new_pwd);
        new AlertDialog.Builder(this)
                .setView(v)
                .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cur=curPwdEt.getText().toString();
                        userInfo.updateCurrentUserPassword(cur,
                                newPwdEt.getText().toString() , new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            TuSdk.messageHub().showSuccess(UserInfoActivity.this, R.string.reset_pwd_succ);
                                        }else{
                                            Log.d(TAG, "done: "+e.getMessage());
                                            TuSdk.messageHub().showError(UserInfoActivity.this, getString(R.string.reset_pwd_fail)
                                                    +"："+e.getMessage());
                                        }
                                    }

                                });
                    }
                })
                .setNegativeButton(android.R.string.cancel,null)
                .show();
    }

    private void showEditAvatar(){
        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuAvatarComponent.html
        TuAvatarComponent component = TuSdkGeeV1.avatarCommponent(this, new TuSdkComponent.TuSdkComponentDelegate()
        {
            @Override
            public void onComponentFinished(TuSdkResult result, Error error, TuFragment lastFragment)
            {
                TLog.d("onAvatarComponentReaded: %s | %s", result, error);
                String r=result.toString();
                final String imgPath=r.substring(r.indexOf("path:")+6,r.indexOf(", name:"));

                if (checkInternet()) {
                    TuSdk.messageHub().setStatus(UserInfoActivity.this, R.string.post_avater);
                    final BmobFile bmobFile = new BmobFile(new File(imgPath));
                    bmobFile.uploadblock(new UploadFileListener() {

                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                User newUser=new User();
                                newUser.setAvater(bmobFile.getFileUrl());
                                final User user = BmobUser.getCurrentUser(User.class);
                                newUser.update(user.getObjectId(),new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            mIsReset=true;
                                            TuSdk.messageHub().showSuccess(UserInfoActivity.this,R.string.post_avater_succ);
                                            //加载新头像
                                            Glide.with(UserInfoActivity.this)
                                                    .load(imgPath)
                                                    .into(headimg);
                                            //删除旧头像
                                            deleteOldAvater(user.getAvater());
                                            //更新本地数据
                                            User.fetchUserInfo();
                                        }else{
                                            Log.d(TAG, "更新用户信息失败:" + e.getMessage()+"  "+e.getErrorCode());
                                            TuSdk.messageHub().showError(UserInfoActivity.this, R.string.post_avater_fail);
                                        }
                                    }
                                });
                            }else{
                                Log.d(TAG, "上传文件失败：" + e.getMessage());
                                TuSdk.messageHub().showError(UserInfoActivity.this, R.string.post_avater_fail);
                            }

                        }

                        @Override
                        public void onProgress(Integer value) {
                            // 返回的上传进度（百分比）
                        }
                    });
                }

            }
        });

        // 组件选项配置
        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/TuAvatarComponentOption.html
        // component.componentOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/album/TuAlbumListOption.html
        // component.componentOption().albumListOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/album/TuPhotoListOption.html
        // component.componentOption().photoListOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/camera/TuCameraOption.html
        // component.componentOption().cameraOption()

        // @see-http://tusdk.com/docs/android/api/org/lasque/tusdk/impl/components/edit/TuEditTurnAndCutOption.html
        // component.componentOption().editTurnAndCutOption()

        // 需要显示的滤镜名称列表 (如果为空将显示所有自定义滤镜, 可选)
        String[] filters = {};
        component.componentOption().cameraOption().setFilterGroup(Arrays.asList(filters));

        component
                // 在组件执行完成后自动关闭组件
                .setAutoDismissWhenCompleted(true)
                // 显示组件
                .showComponent();
    }

        private void deleteOldAvater(String url){
            if (url != null) {
                BmobFile file = new BmobFile();
                file.setUrl(url);//此url是上传文件成功之后通过bmobFile.getUrl()方法获取的。
                file.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.d(TAG, "文件删除成功");
                        }else{
                            Log.d(TAG, "文件删除失败："+e.getErrorCode()+","+e.getMessage());
                        }
                    }
                });
            }
        }

    @Override
    public void finish() {
        if (mIsReset) {
            setResult(RESULT_OK);
        }
        super.finish();
    }
}
