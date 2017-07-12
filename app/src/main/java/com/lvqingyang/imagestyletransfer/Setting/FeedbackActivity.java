package com.lvqingyang.imagestyletransfer.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.tool.SolidRVBaseAdapter;

import java.util.Arrays;
import java.util.List;

public class FeedbackActivity extends BaseActivity {

    private android.support.v7.widget.RecyclerView typerv;
    private android.widget.EditText contentet;
    private android.widget.Button submitbtn;

    public static void start(Context context) {
        Intent starter = new Intent(context, FeedbackActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected void initView() {
        initToolbar(R.string.feedback,true);
        this.submitbtn = (Button) findViewById(R.id.submit_btn);
        this.contentet = (EditText) findViewById(R.id.content_et);
        this.typerv = (RecyclerView) findViewById(R.id.type_rv);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        final List<String> types=Arrays.asList(getResources().getStringArray(R.array.type_arr));
        typerv.setLayoutManager(new GridLayoutManager(this,3 ));
        typerv.setAdapter(new SolidRVBaseAdapter<String>(this, types) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, String type) {
                holder.setText(R.id.tv,type);
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_type;
            }

            @Override
            protected void onItemClick(View view,int position, String bean) {
                super.onItemClick(view,position, bean);
                for (int i = 0; i < typerv.getChildCount(); i++) {
                    TextView t= (TextView) typerv.getChildAt(i);
                    t.setSelected(false);
                    t.setTextColor(getResources().getColor(R.color.textColor));
                }
                TextView tv= (TextView) view;
                tv.setSelected(true);
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
            }
        });
    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
}
