package com.lvqingyang.floodsdetectassistant.tool;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.lvqingyang.floodsdetectassistant.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 *　　┏┓　　  ┏┓+ +
 *　┏┛┻━ ━ ━┛┻┓ + +
 *　┃　　　　　　  ┃
 *　┃　　　━　　    ┃ ++ + + +
 *     ████━████     ┃+
 *　┃　　　　　　  ┃ +
 *　┃　　　┻　　  ┃
 *　┃　　　　　　  ┃ + +
 *　┗━┓　　　┏━┛
 *　　　┃　　　┃　　　　　　　　　　　
 *　　　┃　　　┃ + + + +
 *　　　┃　　　┃
 *　　　┃　　　┃ +  神兽保佑
 *　　　┃　　　┃    代码无bug！　
 *　　　┃　　　┃　　+　　　　　　　　　
 *　　　┃　 　　┗━━━┓ + +
 *　　　┃ 　　　　　　　┣┓
 *　　　┃ 　　　　　　　┏┛
 *　　　┗┓┓┏━┳┓┏┛ + + + +
 *　　　　┃┫┫　┃┫┫
 *　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━神兽出没━━━━━━
 * Author：LvQingYang
 * Date：2017/3/15
 * Email：biloba12345@gamil.com
 * Info：封装对话框
 */



public class MySweetAlertDialog {
   private SweetAlertDialog mSweetAlertDialog;
   private Activity mActivity;
   private static final String TAG = "MySweetAlertDialog";

   public MySweetAlertDialog(Activity activity){
           Log.d(TAG, "MySweetAlertDialog: ");
           mSweetAlertDialog=new SweetAlertDialog(activity);
           mActivity=activity;
   }

   public void loading(String title){
       if (mSweetAlertDialog != null) {
           Log.d(TAG, "loading: ");
           mSweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
           mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
           mSweetAlertDialog.setTitleText(title==null?mActivity.getString(R.string.loading):title)
                   .setCanceledOnTouchOutside(false);
           mSweetAlertDialog.setCancelable(false);
           mSweetAlertDialog.show();
       }
   }

   public void success(){
       if (mSweetAlertDialog != null&&mSweetAlertDialog.isShowing()) {
           Log.d(TAG, "success: ");
           mSweetAlertDialog.dismissWithAnimation();
       }
   }

   public void error(String content, SweetAlertDialog.OnSweetClickListener listener){
       if (mSweetAlertDialog != null) {
           mSweetAlertDialog.setTitleText(mActivity.getString(R.string.Error))
                   .setContentText(content==null?mActivity.getString(R.string.check):content)
                   .setConfirmText(mActivity.getString(R.string.back))
                   .setConfirmClickListener(listener==null?new SweetAlertDialog.OnSweetClickListener() {
                       @Override
                       public void onClick(SweetAlertDialog sDialog) {
                           sDialog.dismissWithAnimation();
                           if (mActivity != null) {
                               mActivity.finish();
                           }
                       }
                   }:listener)
                   .changeAlertType(SweetAlertDialog.ERROR_TYPE);
           mSweetAlertDialog.setCanceledOnTouchOutside(false);
           //如果没在显示则直接显示错误对话框
           if (!mSweetAlertDialog.isShowing()) {
               mSweetAlertDialog.show();
           }
       }
   }

   public SweetAlertDialog getSweetAlertDialog(){
       return mSweetAlertDialog;
   }

}
