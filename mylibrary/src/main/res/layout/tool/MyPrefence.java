package com.lvqingyang.floodsdetectassistant.tool;

import android.content.Context;
import android.content.SharedPreferences;

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
 * Info：封装SharpPrefence
 */



public class MyPrefence {
   private static final String PREFENCE_NAME = "floods";
   private static com.lvqingyang.floodsdetectassistant.tool.MyPrefence mPrefence;
   private SharedPreferences mSharedPreferences;
   private SharedPreferences.Editor mEditor;

    private static final String KEY_IS_LOGIN = "IS_LOGIN";
    private static final String KEY_NAME = "NAME";
    private static final String KEY_TEL = "TEL";
    private static final String KEY_PASS = "PASS";
    private static final String KEY_IS_MALE = "IS_MALE";
    private static final String KEY_ID = "ID";


   private MyPrefence(Context context){
       mSharedPreferences=context.getSharedPreferences(PREFENCE_NAME,Context.MODE_PRIVATE);
       mEditor=mSharedPreferences.edit();
   }

   public synchronized static com.lvqingyang.floodsdetectassistant.tool.MyPrefence getInstance(Context context) {
       if ( mPrefence== null) {
           mPrefence = new com.lvqingyang.floodsdetectassistant.tool.MyPrefence(context);
       }
       return mPrefence;
   }

   public String getString(String tag){
       return  mSharedPreferences.getString(tag,null);
   }

   public void saveString(String tag,String value){
       mEditor.putString(tag,value).apply();
   }

   public int getInt(String tag){
       return mSharedPreferences.getInt(tag,-1);
   }

    public int getInt(String tag,int def){
        return mSharedPreferences.getInt(tag,def);
    }

   public void saveInt(String tag,int value){
       mEditor.putInt(tag,value).apply();
   }

   public boolean getBool(String tag){
       return mSharedPreferences.getBoolean(tag,false);
   }

   public void saveBool(String tag,boolean value){
       mEditor.putBoolean(tag,value).apply();
   }


//
//   //登录成功后保存用户信息
//    public void saveUser(User user){
//        saveBool(KEY_IS_LOGIN,true);
//        saveString(KEY_NAME,user.getNickname());
//        saveString(KEY_PASS,user.getPassword());
//        saveString(KEY_ID,user.getObjectId());
//        saveString(KEY_TEL,user.getTel());
//        if (user.getMale()!=null) {
//            saveInt(KEY_IS_MALE,user.getMale()?1:0);
//        }else{
//            saveInt(KEY_IS_MALE,-1);
//        }
//    }
//
//    //获取用户信息
//    public User getUser(){
//        User u=null;
//        if (isLogined()) {
//            u=new User();
//            u.setNickname(getString(KEY_NAME));
//            u.setTel(getString(KEY_TEL));
//            u.setPassword(getString(KEY_PASS));
//            u.setObjectId(getString(KEY_ID));
//            int male=getInt(KEY_IS_MALE);
//            u.setMale(male==-1?null:(male==1?true:false));
//        }
//        return u;
//    }
//
//    //是否登录
//    public boolean isLogined(){
//        return getBool(KEY_IS_LOGIN);
//    }
//
//    //退出登录
//    public void logOut(){
//        saveBool(KEY_IS_LOGIN,false);
//        mEditor.remove(KEY_ID)
//                .remove(KEY_IS_MALE)
//                .remove(KEY_NAME)
//                .remove(KEY_PASS)
//                .remove(KEY_TEL)
//                .apply();
//    }
}
