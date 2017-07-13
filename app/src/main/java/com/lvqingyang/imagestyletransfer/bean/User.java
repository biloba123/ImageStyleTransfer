package com.lvqingyang.imagestyletransfer.bean;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.SaveListener;

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
 * Date：2017/7/6
 * Email：biloba12345@gamil.com
 * Info：
 */

public class User extends BmobUser {
    private String sign;
    private String  avater;
    private static final String TAG = "User";

    /**
     * 更新本地用户信息
     * 适用场景:登录后若web端的用户信息有更新 可以通过该方法拉取最新的用户信息并写到本地缓存(SharedPreferences)中<p>
     * 注意：需要先登录，否则会报9024错误
     *
     */
    public static void fetchUserInfo() {
        BmobUser.fetchUserInfo(new FetchUserInfoListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: succ "+BmobUser.getCurrentUser(User.class).getAvater());
                } else {
                    Log.d(TAG, "done: "+e.getMessage());
                }
            }
        });
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getAvater() {
        return avater;
    }

    public void setAvater(String avater) {
        this.avater = avater;
    }

    public static void signup(String nick, String email, String pwd, SaveListener<User> listener){
        BmobUser user=new User();
        user.setUsername(nick);
        user.setEmail(email);
        user.setPassword(pwd);
        user.signUp(listener);
    }

}
