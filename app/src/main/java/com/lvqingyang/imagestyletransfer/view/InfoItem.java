package com.lvqingyang.imagestyletransfer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvqingyang.imagestyletransfer.R;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;

/**
 * 　　┏┓　　  ┏┓+ +
 * 　┏┛┻━ ━ ━┛┻┓ + +
 * 　┃　　　             ┃
 * 　┃　　　━　　   ┃ ++ + + +
 * ████━████     ┃+
 * 　┃　　　　　　  ┃ +
 * 　┃　　　┻　　  ┃
 * 　┃　　　　　　  ┃ + +
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃
 * 　　　┃　　　┃ + + + +
 * 　　　┃　　　┃
 * 　　　┃　　　┃ +  神兽保佑
 * 　　　┃　　　┃    代码无bug！
 * 　　　┃　　　┃　　+
 * 　　　┃　 　　┗━━━┓ + +
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━神兽出没━━━━━━
 * Author：LvQingYang
 * Date：2017/7/12
 * Email：biloba12345@gamil.com
 * Info：
 */

public class InfoItem extends FrameLayout {
    private  IconicsImageView mIconIv;
    private  TextView mNameTv;
    private  TextView mContentTv;
    private  TextView mHintTv;
    private View mView;

    public InfoItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_info,this);
        mView = (RelativeLayout) findViewById(R.id.rl);
        mIconIv = (IconicsImageView) findViewById(R.id.icon_iv);
        mNameTv = (TextView) findViewById(R.id.name_text);
        mContentTv = (TextView) findViewById(R.id.content_text);
        mHintTv = (TextView) findViewById(R.id.hint_text);
        ImageView imageView = (ImageView) findViewById(R.id.arrow);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CardItem,
                0, 0);

        try {
            if (!a.getBoolean(R.styleable.CardItem_showContent,false)) {
                mContentTv.setVisibility(GONE);
            }
            if (!a.getBoolean(R.styleable.CardItem_showHint,false)) {
                mHintTv.setVisibility(GONE);
            }
            if (!a.getBoolean(R.styleable.CardItem_showArrow,true)) {
                imageView.setVisibility(GONE);
            }
            mNameTv.setText(a.getString(R.styleable.CardItem_name));
            mContentTv.setText(a.getString(R.styleable.CardItem_content));
            mHintTv.setText(a.getString(R.styleable.CardItem_hint));
        } finally {
            a.recycle();
        }
    }

    public InfoItem setContent(String content){
        mContentTv.setText(content);
        return this;
    }

    public String getContent(){
        return mContentTv.getText().toString();
    }

    public InfoItem setHint(String hint){
        mHintTv.setText(hint);
        return this;
    }

    public InfoItem setIcon(IconicsDrawable icon){
        mIconIv.setIcon(icon);
        return this;
    }

    public void setClickListener(OnClickListener listener){
        mView.setOnClickListener(listener);
    }
}
