package com.lvqingyang.imagestyletransfer.Login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvqingyang.imagestyletransfer.base.BaseFragment;
import com.lvqingyang.imagestyletransfer.bean.User;
import com.lvqingyang.imagestyletransfer.tool.Utilize;
import com.lvqingyang.mylibrary.R;

import org.lasque.tusdk.core.TuSdk;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


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

public class RegisterFragment extends BaseFragment {

    private TextView logintext;
    private EditText nicket;
    private EditText emailet;
    private EditText pwdet;
    private LinearLayout login;
    private TextView registertext;

    private OnRegisterListener mListener;


    public static RegisterFragment newInstance() {
        Bundle args = new Bundle();
        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        return view;
    }

    @Override
    protected void initView(View view) {
        this.registertext = (TextView) view.findViewById(R.id.register_text);
        this.login = (LinearLayout) view.findViewById(R.id.login);
        this.pwdet = (EditText) view.findViewById(R.id.pwd_et);
        this.emailet = (EditText) view.findViewById(R.id.email_et);
        this.nicket = (EditText) view.findViewById(R.id.nick_et);
        this.logintext = (TextView) view.findViewById(R.id.login_text);
    }

    @Override
    protected void setListener() {
        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLogin();
            }
        });

        //注册
        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    if (checkInternet()) {
                        TuSdk.messageHub().setStatus(getActivity(), "注册中...");
                        String userName=nicket.getText().toString(),
                                email=emailet.getText().toString(),
                                pwd=pwdet.getText().toString();
                        User.signup(userName, email, pwd, new SaveListener<User>() {
                                    @Override
                                    public void done(User o, BmobException e) {
                                        cancelLoadingDialog();
                                        if (e==null) {
                                            TuSdk.messageHub().showSuccess(getActivity(), R.string.register_succ);
                                        }else{
                                            e.printStackTrace();
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
        if (nicket.getText().toString().isEmpty()) {
            showInfoToast(R.string.empty_nick);
            return false;
        }

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


    public  interface OnRegisterListener{
        void onLogin();
    }

    //    与Activity交互
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterListener) {
            mListener = (OnRegisterListener) context;
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
