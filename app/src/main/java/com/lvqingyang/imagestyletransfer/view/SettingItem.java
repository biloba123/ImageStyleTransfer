package com.lvqingyang.imagestyletransfer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lvqingyang.imagestyletransfer.R;

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
 * Date：2017/6/16
 * Email：biloba12345@gamil.com
 * Info：
 */

public class SettingItem extends FrameLayout {

    private final TextView mTypeTv;
    private final TextView mTitleTv;
    private final TextView mInfoTv;
    private final CheckBox mCheckBox;
    private final View mRl;

    public SettingItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_setting,this);
        mTypeTv = (TextView) findViewById(R.id.type_tv);
        mTitleTv = (TextView)findViewById(R.id.title_tv);
        mInfoTv = (TextView) findViewById(R.id.info_tv);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox);
        mRl = findViewById(R.id.rl);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SettingItem,
                0, 0);

        try {
            if (!a.getBoolean(R.styleable.SettingItem_showType,true)) {
                mTypeTv.setVisibility(GONE);
            }
            if (!a.getBoolean(R.styleable.SettingItem_showInfo,false)) {
                mInfoTv.setVisibility(GONE);
            }
            if (!a.getBoolean(R.styleable.SettingItem_showCb,false)) {
                mCheckBox.setVisibility(GONE);
            }
            mTypeTv.setText(a.getString(R.styleable.SettingItem_type));
            mTitleTv.setText(a.getString(R.styleable.SettingItem_title));
            mInfoTv.setText(a.getString(R.styleable.SettingItem_info));
        } finally {
            a.recycle();
        }
    }

    public SettingItem setTitle(String title){
        mTitleTv.setText(title);
        return this;
    }

    public SettingItem setType(String typ){
        mTypeTv.setText(typ);
        return this;
    }

    public SettingItem setInfo(String info){
        mInfoTv.setText(info);
        return this;
    }

    public SettingItem setClickListener(OnClickListener listener){
        mRl.setOnClickListener(listener);
        return this;
    }

    public SettingItem setCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener){
        mCheckBox.setOnCheckedChangeListener(listener);
        return this;
    }

}
