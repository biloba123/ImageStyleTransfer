package com.lvqingyang.imagestyletransfer.base;

import com.lvqingyang.imagestyletransfer.bean.User;

import cn.bmob.v3.BmobObject;

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
 * Date：2017/7/11
 * Email：biloba12345@gamil.com
 * Info：
 */

public class Picture extends BmobObject{
    public static final int TYPE_NATURE = 214;
    public static final int TYPE_PERSON = 566;
    public static final int TYPE_FOOD = 334;
    public static final int TYPE_PET = 559;
    public static final int TYPE_BUILDING = 221;
    public static final int  TYPE_LIFT= 107;
    public static final int TYPE_STREET = 278;
    public static final int TYPE_THING = 989;

    private Integer type;
    private String title;
    private Integer like;
    private User poster;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }
}
