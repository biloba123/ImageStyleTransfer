package com.lvqingyang.imagestyletransfer.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lvqingyang.imagestyletransfer.R;
import com.lvqingyang.imagestyletransfer.base.BaseActivity;
import com.lvqingyang.imagestyletransfer.bean.Feedback;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.SolidRVBaseAdapter;

import org.lasque.tusdk.core.TuSdk;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.lvqingyang.imagestyletransfer.R.id.tv;
import static com.lvqingyang.imagestyletransfer.R.string.feedback;

public class FeedbackActivity extends BaseActivity {

    private android.support.v7.widget.RecyclerView typerv;
    private android.widget.EditText contentet;
    private android.widget.Button submitbtn;
    private int mType=-1;

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
        initToolbar(feedback,true);
        this.submitbtn = (Button) findViewById(R.id.submit_btn);
        this.contentet = (EditText) findViewById(R.id.content_et);
        this.typerv = (RecyclerView) findViewById(R.id.type_rv);
    }

    @Override
    protected void setListener() {
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content=contentet.getText().toString();
                if (!content.isEmpty()) {
                    Feedback feedback=new Feedback();
                    feedback.setType(mType);
                    feedback.setContent(content);
                    feedback.setUser(BmobUser.getCurrentUser(User.class));
                    TuSdk.messageHub().setStatus(FeedbackActivity.this, R.string.posting);
                    feedback.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                TuSdk.messageHub().showSuccess(FeedbackActivity.this, R.string.post_succ);
                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                                TuSdk.messageHub().showError(FeedbackActivity.this, R.string.post_fail);
                            }
                        }
                    });
                }else {
                    TuSdk.messageHub().showToast(FeedbackActivity.this, R.string.edit_feedback);
                }
            }
        });
    }

    @Override
    protected void initData() {
        final List<String> types=Arrays.asList(getResources().getStringArray(R.array.type_arr));
        typerv.setLayoutManager(new GridLayoutManager(this,3 ));
        typerv.setAdapter(new SolidRVBaseAdapter<String>(this, types) {
            @Override
            protected void onBindDataToView(SolidCommonViewHolder holder, String type) {
                holder.setText(tv,type);
            }

            @Override
            public int getItemLayoutID(int viewType) {
                return R.layout.item_type;
            }

            @Override
            protected void onItemClick(View view,int position, String bean) {
                super.onItemClick(view,position, bean);
                if (mType!=-1) {
                    TextView t= (TextView) typerv.getChildAt(mType);
                    t.setSelected(false);
                    t.setTextColor(getResources().getColor(R.color.textColor));
                }
                mType=position;
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
