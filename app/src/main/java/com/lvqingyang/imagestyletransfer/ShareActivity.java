package com.lvqingyang.imagestyletransfer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.lvqingyang.imagestyletransfer.Community.PostActivity;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.Platform;
import com.lvqingyang.imagestyletransfer.tool.ShareUtil;
import com.lvqingyang.imagestyletransfer.tool.SolidRVBaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShareActivity extends BaseActivity {

    private android.support.v7.widget.RecyclerView sharerv;
    private static final String KEY_PATH = "PATH";
    private String mPath;
    private List<Platform> mPlatforms = new ArrayList<>();
    private android.widget.LinearLayout postll;
    private android.widget.LinearLayout backll;

    public static void start(Context context, String path) {
        Intent starter = new Intent(context, ShareActivity.class);
        starter.putExtra(KEY_PATH, path);
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_share);

    }

    @Override
    protected void initView() {
        initToolbar(R.string.pic_share, true);
        this.sharerv = (RecyclerView) findViewById(R.id.share_rv);
        this.backll = (LinearLayout) findViewById(R.id.back_ll);
        this.postll = (LinearLayout) findViewById(R.id.post_ll);
    }

    @Override
    protected void setListener() {
        backll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        postll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostActivity.start(ShareActivity.this, mPath);
            }
        });
    }

    @Override
    protected void initData() {
        mPath = getIntent().getStringExtra(KEY_PATH); 
        mPlatforms.add(new Platform(R.drawable.share_wechat, "微信好友", "com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
        mPlatforms.add(new Platform(R.drawable.share_wechat_friend, "微信朋友圈", "com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));
        mPlatforms.add(new Platform(R.drawable.share_qq, "QQ好友", "com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        mPlatforms.add(new Platform(R.drawable.share_qzone, "QQ空间", "com.qzone", "com.qzonex.module.operation.ui.QZonePublishMoodActivity"));
        mPlatforms.add(new Platform(R.drawable.share_sina_weibo, "新浪微博", "com.sina.weibo", "com.sina.weibo.EditActivity"));
        mPlatforms.add(new Platform(R.drawable.share_douban, "豆瓣", "", ""));
        mPlatforms.add(new Platform(R.drawable.share_momo, "陌陌", "", ""));
        mPlatforms.add(new Platform(R.drawable.share_renren, "人人", "", ""));
        mPlatforms.add(new Platform(R.drawable.share_baidu_tieba, "百度贴吧", "", ""));
        mPlatforms.add(new Platform(R.drawable.share_facebook, "facebook", "", ""));
        mPlatforms.add(new Platform(R.drawable.share_twitter, "twitter", "", ""));
        mPlatforms.add(new Platform(R.drawable.share_line, "line", "", ""));
    }

    @Override
    protected void setData() {
        sharerv.setAdapter(new SolidRVBaseAdapter<Platform>(this, mPlatforms) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, Platform bean) {
                holder.setText(R.id.tv, bean.getName());
                holder.setImage(R.id.img, bean.getImgId());
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_platform;
            }

            @Override
            protected void onItemClick(View view, int position, Platform bean) {
                super.onItemClick(view, position, bean);
                ShareUtil.jumpToPlatform(ShareActivity.this, mPath, bean.getPacket(), bean.getActivity());
            }
        });
        sharerv.setLayoutManager(new GridLayoutManager(this, 4));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
