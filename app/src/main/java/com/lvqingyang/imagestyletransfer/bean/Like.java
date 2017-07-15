package com.lvqingyang.imagestyletransfer.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

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
 * Date：2017/7/14
 * Email：biloba12345@gamil.com
 * Info：
 */

public class Like extends BmobObject {
    private User user;
    private String imgId;

    public Like(String imgId) {
        this.user = BmobUser.getCurrentUser(User.class);
        this.imgId=imgId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    //查询该用户各图片点赞情况
    public static void getUserLikes(List<Picture> pictures, FindListener<Like> lis){
        User user=BmobUser.getCurrentUser(User.class);
        BmobQuery<Like> eq=new BmobQuery<>();
        eq.addWhereEqualTo("user",user);

        String[] imgIds=new String[pictures.size()];
        for (int i = 0; i < pictures.size(); i++) {
            imgIds[1]=pictures.get(i).getObjectId();
        }
        BmobQuery<Like> containIn=new BmobQuery<>();
        containIn.addWhereContainedIn("imgId", Arrays.asList(imgIds));

        List<BmobQuery<Like>> andQuerys=new ArrayList<>();
        andQuerys.add(eq);
        andQuerys.add(containIn);

        BmobQuery<Like> query=new BmobQuery<>();
        query.and(andQuerys);
        query.findObjects(lis);
    }
}
