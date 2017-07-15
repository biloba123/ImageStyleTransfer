package com.lvqingyang.imagestyletransfer.Community;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.PicType;
import com.lvqingyang.imagestyletransfer.bean.PostPicture;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.SolidRVBaseAdapter;

import org.lasque.tusdk.core.TuSdk;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostActivity extends BaseActivity {

    private static final String KEY_IMAGE_FILE = "IMAGE_FILE";
    private android.widget.ImageView iv;
    private String mImagePath;
    private de.hdodenhof.circleimageview.CircleImageView headimg;
    private android.widget.TextView usernametv;
    private android.widget.EditText titleet;
    private android.support.v7.widget.RecyclerView typerv;
    private android.widget.Button postbtn;
    private User userInfo;
    private int mPicType=-1;
    private static final String TAG = "PostActivity";

    public static void start(Context context, String path) {
        Intent starter = new Intent(context, PostActivity.class);
        starter.putExtra(KEY_IMAGE_FILE,path);
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_post);
    }

    @Override
    protected void initView() {
        initToolbar(R.string.post_to_community,true);
        this.postbtn = (Button) findViewById(R.id.post_btn);
        this.typerv = (RecyclerView) findViewById(R.id.type_rv);
        this.titleet = (EditText) findViewById(R.id.title_et);
        this.usernametv = (TextView) findViewById(R.id.username_tv);
        this.headimg = (CircleImageView) findViewById(R.id.head_img);
        this.iv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    protected void setListener() {
        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPicType!=-1) {
                    if (checkInternet()) {
                        final PostPicture pic=new PostPicture();
                        String title=titleet.getText().toString();
                        pic.setTitle(title.isEmpty()?"无题":title);
                        pic.setType(mPicType);
                        pic.setPoster(userInfo);
                        TuSdk.messageHub().setStatus(PostActivity.this, R.string.posting);
                        final BmobFile bmobFile = new BmobFile(new File(mImagePath));
                        bmobFile.uploadblock(new UploadFileListener() {

                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    //bmobFile.getFileUrl()--返回的上传文件的完整地址
                                    Log.d(TAG, "上传文件成功:" + bmobFile.getFileUrl());
                                    pic.setImgUrl(bmobFile.getFileUrl());
                                    pic.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String objectId, BmobException e) {
                                            if(e==null){
                                                TuSdk.messageHub().showSuccess(PostActivity.this, R.string.post_succ_);
                                                PostSuccActivity.start(PostActivity.this, mImagePath);
                                                finish();
                                            }else{
                                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                                TuSdk.messageHub().showError(PostActivity.this, R.string.post_fail);
                                            }
                                        }
                                    });

                                }else{
                                    TuSdk.messageHub().showError(PostActivity.this, R.string.post_fail);
                                }

                            }

                            @Override
                            public void onProgress(Integer value) {
                                // 返回的上传进度（百分比）
                            }
                        });

                    }

                }else {
                    TuSdk.messageHub().showToast(PostActivity.this, R.string.choose_type);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mImagePath= getIntent().getStringExtra(KEY_IMAGE_FILE);
        userInfo = BmobUser.getCurrentUser(User.class);
    }

    @Override
    protected void setData() {
        Glide.with(this)
                .load(mImagePath)
                .into(iv);

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
            usernametv.setText(userInfo.getUsername());
        }

        typerv.setLayoutManager(new GridLayoutManager(this, 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        setAdapter();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void setAdapter(){
        int[] imgIds={
                R.drawable.communtiy_nature,
                R.drawable.communtiy_person,
                R.drawable.communtiy_food,
                R.drawable.communtiy_pet,
                R.drawable.communtiy_building,
                R.drawable.communtiy_life,
                R.drawable.communtiy_street,
                R.drawable.communtiy_thing,
        };
        String[] titles=getResources().getStringArray(R.array.titles);

        List<PicType> types=new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            types.add(new PicType(imgIds[i],titles[i+1]));
        }

        typerv.setAdapter(new SolidRVBaseAdapter<PicType>(this, types) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, PicType bean) {
                holder.setText(R.id.type_tv,bean.getName());
                holder.setImage(R.id.bg_iv,bean.getImgId());
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_pic_type;
            }

            @Override
            protected void onItemClick(View view, int position, PicType bean) {
                super.onItemClick(view, position, bean);
                if (mPicType!=-1) {
                    View v= typerv.getChildAt(mPicType);
                    ((TextView)v.findViewById(R.id.type_tv))
                            .setBackgroundColor(Color.parseColor("#50000000"));
                }
                mPicType=position;
                ((TextView)view.findViewById(R.id.type_tv))
                        .setBackground(getResources().getDrawable(R.drawable.pic_type_bg));
            }
        });
    }
}
