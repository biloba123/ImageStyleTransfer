package com.lvqingyang.imagestyletransfer.Community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;

public class PostSuccActivity extends BaseActivity {


    private android.widget.ImageView iv;
    private android.widget.Button backbtn;
    private static final String KEY_PATH = "KEY_PATH";

    public static void start(Context context,String imgPath) {
        Intent starter = new Intent(context, PostSuccActivity.class);
        starter.putExtra(KEY_PATH, imgPath);
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_post_succ);
    }

    @Override
    protected void initView() {
        this.backbtn = (Button) findViewById(R.id.back_btn);
        this.iv = (ImageView) findViewById(R.id.iv);
    }

    @Override
    protected void setListener() {
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        Glide.with(this)
                .load(getIntent().getStringExtra(KEY_PATH))
                .into(iv);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
