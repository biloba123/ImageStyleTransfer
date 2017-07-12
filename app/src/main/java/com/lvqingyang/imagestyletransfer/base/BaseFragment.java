package com.lvqingyang.imagestyletransfer.base;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lvqingyang.mylibrary.R;
import com.lvqingyang.imagestyletransfer.tool.MySweetAlertDialog;
import com.lvqingyang.imagestyletransfer.tool.NetWorkUtils;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.SupportFragment;


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
 * Info：碎片基类
 */


public abstract class BaseFragment extends SupportFragment  {
    private static final String TAG = "BaseFragment";
    private AppCompatActivity mAppCompatActivity;
    protected MySweetAlertDialog mDialog;

    protected View rootView;
    public View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        是否保留Fragment
//        setRetainInstance(true);

//        是否有menu，然后重写onCreateOptionsMenu, onOptionsItemSelected
        setHasOptionsMenu(true);
        mAppCompatActivity= (AppCompatActivity) getActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (null != getArguments()) {
            getBundleExtras(getArguments());
        }
        view = initContentView(inflater, container, savedInstanceState);

        return view;
    }

    //懒加载
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        initView(view);
        setListener();
        initData();
        setData();
    }

    // 初始化UI setContentView
    protected abstract View initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                            @Nullable Bundle savedInstanceState);

    // 初始化控件
    protected abstract void initView(View view);


     //设置监听器
    protected abstract void setListener();

    // 数据
    protected abstract void initData();

    //为View设置数据
    protected abstract void setData();


    //初始化toolbar
    public void initToolbar(String title, boolean isDisplayHomeAsUp) {
        Toolbar toolbar= (Toolbar) view.findViewById(R.id.toolbar);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(isDisplayHomeAsUp);
        }
    }


    /**
     * 获取bundle信息
     *
     * @param bundle
     */
    protected abstract void getBundleExtras(Bundle bundle);


    /**
     * 显示默认加载动画 默认加载文字
     */
    protected void showLoadingDialog() {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.loading(null);
    }


    /**
     * 显示默认加载动画 自定义加载文字
     *
     * @param str
     */
    protected void showLoadingDialog(String str) {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.loading(str);
    }

    /**
     * 显示加载动画 自定义加载文字
     *
     * @param strId
     */
    protected void showLoadingDialog(int strId) {
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.loading(getString(strId));
    }


    /**
     * 取消加载动画
     */
    protected void cancelLoadingDialog() {
        if (null != mDialog) {
            mDialog.success();
            mDialog=null;
        }
    }

    /**
     * 显示错误对话框
     *
     * @param listener
     */
    protected void showErrorDialog(SweetAlertDialog.OnSweetClickListener listener){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.error(null,listener);
        mDialog=null;
    }

    /**
     * 显示错误对话框
     *
     * @param content
     * @param listener
     */
    protected void showErrorDialog(String content,SweetAlertDialog.OnSweetClickListener listener){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.error(content,listener);
        mDialog=null;
    }

    /**
     * 显示错误对话框
     *
     * @param contentId
     * @param listener
     */
    protected void showErrorDialog(int contentId,SweetAlertDialog.OnSweetClickListener listener){
        if (null == mDialog) {
            mDialog = new MySweetAlertDialog(mAppCompatActivity);
        }
        mDialog.error(getString(contentId),listener);
        mDialog=null;
    }

    //Toast显示
    protected void showToast(String string) {
        Toasty.normal(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int stringId) {
        Toasty.normal(getActivity(), getString(stringId), Toast.LENGTH_SHORT).show();
    }

    protected void showToastWithIcon(String string, Drawable icon) {
        Toasty.custom(getActivity(), string,icon, Color.WHITE,Toast.LENGTH_SHORT,true).show();
    }

    protected void showToastWithIcon(int  stringId, Drawable icon) {
        Toasty.custom(getActivity(), getString(stringId),icon, Color.WHITE,Toast.LENGTH_SHORT,true).show();
    }

    protected void showInfoToast(String string){
        Toasty.info(getActivity(),string,Toast.LENGTH_SHORT ).show();
    }

    protected void showInfoToast(int  stringId){
        Toasty.info(getActivity(),getString(stringId),Toast.LENGTH_SHORT ).show();
    }

    protected void showSuccToast(String string){
        Toasty.success(getActivity(), string,Toast.LENGTH_SHORT).show();
    }


    protected void showSuccToast(int  stringId){
        Toasty.success(getActivity(), getString(stringId),Toast.LENGTH_SHORT).show();
    }

    protected void showErrorToast(String string){
        Toasty.error(getActivity(), string,Toast.LENGTH_SHORT).show();
    }

    protected void showErrorToast(int  stringId){
        Toasty.error(getActivity(), getString(stringId),Toast.LENGTH_SHORT).show();
    }

    protected void showWarnToast(String string){
        Toasty.warning(getActivity(), string,Toast.LENGTH_SHORT).show();
    }

    protected void showWarnToast(int  stringId){
        Toasty.warning(getActivity(), getString(stringId),Toast.LENGTH_SHORT).show();
    }

    //网络请求
    protected boolean checkInternet(){
        if (NetWorkUtils.isNetworkConnected(getActivity())) {
            return true;
        }
        showWarnToast(R.string.check);
        return false;
    }

    /**
     * startActivity
     *
     * @param clazz 目标Activity
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * @param clazz 目标Activity
     */
    protected void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 获取图标
     */

    public IconicsDrawable getToolbarIcon(IIcon icon) {
        return new IconicsDrawable(getActivity()).icon(icon).color(Color.WHITE).sizeDp(18);
    }

    public IconicsDrawable getIcon(IIcon icon,int color) {
        return new IconicsDrawable(getActivity()).icon(icon).color(color);
    }

    public IconicsDrawable getIcon(IIcon icon,int color,int size) {
        return new IconicsDrawable(getActivity()).icon(icon).color(color).sizeDp(18);
    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
