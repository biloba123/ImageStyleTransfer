package com.lvqingyang.imagestyletransfer.Community;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.Picture;
import com.lvqingyang.imagestyletransfer.bean.Report;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.SolidRVBaseAdapter;

import org.lasque.tusdk.core.TuSdk;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PictureDetailActivity extends BaseActivity {


    private android.support.v7.widget.RecyclerView picrv;
    private static final String KEY_PIC_LIST = "PIC_LIST";
    private List<Picture> mPicList=new ArrayList<>();
    private SolidRVBaseAdapter mAdapter;
    private static final String KEY_POSITION = "POSITION";
    private static final String TAG = "PictureDetailActivity";

    public static void start(Context context, List<Picture> pics,int pos) {
        Intent starter = new Intent(context, PictureDetailActivity.class);
        starter.putExtra(KEY_POSITION,pos);
        starter.putParcelableArrayListExtra(KEY_PIC_LIST, (ArrayList<? extends Parcelable>) pics);
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_picture_detail);
    }

    @Override
    protected void initView() {
//        initToolbar("lalala",true);
        this.picrv = (RecyclerView) findViewById(R.id.pic_rv);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        mPicList=getIntent().getParcelableArrayListExtra(KEY_PIC_LIST);
        mAdapter=new SolidRVBaseAdapter<Picture>(this, mPicList) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, final Picture pic) {
                holder.setText(R.id.title_tv,pic.getTitle());
                holder.setImageFromInternet(R.id.iv,pic.getImgUrl());
                holder.setText(R.id.like_count_tv,pic.getLike()+"");
                holder.setImageFromInternet(R.id.head_cv,pic.getPoster().getAvater());
                User userInfo=pic.getPoster();
                if (userInfo.getAvater()!=null) {
                    holder.setImageFromInternet(R.id.head_cv,userInfo.getAvater());
                }else {
                    holder.setImage(R.id.head_cv,R.drawable.communtiy_person);
                }
                holder.setText(R.id.name_tv,userInfo.getUsername());


                //举报
                holder.getView(R.id.report_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showReportDialog(pic);
                    }
                });
                //点赞
                ((LikeButton)holder.getView(R.id.like_btn)).setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {

                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {

                    }
                });

            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_picture_detail;
            }
        };
    }

    @Override
    protected void setData() {
        picrv.setAdapter(mAdapter);
        picrv.setLayoutManager(new LinearLayoutManager(this));
        picrv.scrollToPosition(getIntent().getIntExtra(KEY_POSITION,0));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    private void showReportDialog(final Picture pic){
        final View view=getLayoutInflater().inflate(R.layout.dialog_report,null);
        //inite view and show data
        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.type_rg);
        rg.check(R.id.rb1);
        new AlertDialog.Builder(this)
                .setView(view)
                .setNegativeButton(android.R.string.cancel,null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RadioButton rb=view.findViewById(rg.getCheckedRadioButtonId());
                        Report report=new Report(pic.getImgUrl(), pic.getPoster(),  rb.getText().toString());
                        report.save(new SaveListener<String>() {

                            @Override
                            public void done(String objectId, BmobException e) {
                                if(e==null){
                                    TuSdk.messageHub().showSuccess(PictureDetailActivity.this, R.string.report_succ);
                                }else{
                                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                    TuSdk.messageHub().showError(PictureDetailActivity.this, R.string.report_fail);
                                }
                            }
                        });
                    }
                })
                .create()
                .show();
    }
}
