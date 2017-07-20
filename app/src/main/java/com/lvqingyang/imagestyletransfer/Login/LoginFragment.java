package com.lvqingyang.imagestyletransfer.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lvqingyang.imagestyletransfer.base.BaseFragment;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.Utilize;
import com.lvqingyang.mylibrary.R;

import org.lasque.tusdk.core.TuSdk;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 　　┏┓　　  ┏┓+ +
 * 　┏┛┻━ ━ ━┛┻┓ + +
 * 　┃　　　　　　  ┃
 * 　┃　　　━　　    ┃ ++ + + +
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
 * Date：2017/4/12
 * Email：biloba12345@gamil.com
 * Info：
 */

public class LoginFragment extends BaseFragment {

    private TextView registertext;
    private EditText emailet;
    private EditText pwdet;
    private TextView logintext;

    private OnLoginListener mListener;



    public static LoginFragment newInstance() {
        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    @Override
    protected void initView(View view) {
        this.logintext = (TextView) view.findViewById(R.id.login_text);
        this.pwdet = (EditText) view.findViewById(R.id.pwd_et);
        this.emailet = (EditText) view.findViewById(R.id.email_et);
        this.registertext = (TextView) view.findViewById(R.id.register_text);
    }

    @Override
    protected void setListener() {
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRegister();
            }
        });

        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    if (checkInternet()) {
                        TuSdk.messageHub().setStatus(getActivity(), "登录中...");
                        BmobUser.loginByAccount(emailet.getText().toString(), pwdet.getText().toString(), new LogInListener<User>() {

                                    @Override
                                    public void done(User user, BmobException e) {
                                        if(user!=null){
                                            TuSdk.messageHub().showSuccess(getActivity(), R.string.login_succ);
                                            mListener.logined();
                                        }else {
                                            TuSdk.messageHub().showError(getActivity(), e.getMessage());
                                        }
                                    }
                                });
                    }
                }
            }
        });



    }




    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }


    //检查输入
    private boolean checkInput(){
        if (emailet.getText().toString().isEmpty()) {
            showInfoToast(R.string.empty_email);
            return false;
        }

        if (pwdet.getText().toString().isEmpty()) {
            showInfoToast(R.string.empty_pwd);
            return false;
        }

        if (!Utilize.emailFormat(emailet.getText().toString())) {
            showInfoToast(R.string.error_email);
            return false;
        }

        return true;
    }

    public  interface OnLoginListener{
        void onRegister();
        void logined();
    }

    //    与Activity交互
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginListener) {
            mListener = (OnLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddStudentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
