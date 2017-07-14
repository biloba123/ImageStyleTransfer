package com.lvqingyang.imagestyletransfer.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

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

public class Picture extends BmobObject implements Parcelable {
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
    private String imgUrl;

    public Picture() {
        like=0;
    }

    public Picture(Integer type, String title, String imgUrl) {
        poster = BmobUser.getCurrentUser(User.class);
        like=0;
        this.type = type;
        if (title.isEmpty()) {
            this.title="无题";
        }else {
            this.title = title;
        }
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.type);
        dest.writeString(this.title);
        dest.writeValue(this.like);
        dest.writeSerializable(this.poster);
        dest.writeString(this.imgUrl);
    }

    protected Picture(Parcel in) {
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.like = (Integer) in.readValue(Integer.class.getClassLoader());
        this.poster = (User) in.readSerializable();
        this.imgUrl = in.readString();
    }

    public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel source) {
            return new Picture(source);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };
}
