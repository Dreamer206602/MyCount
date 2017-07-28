package com.booboomx.mycount.data;

import android.os.Parcel;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.booboomx.mycount.constants.Api;

/**
 * Created by booboomx on 17/7/24.
 */

public class User extends AVUser {

    public String phone;
    public String password;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public void setPassword(String password) {
        this.password = password;
    }



    /** 年龄 */
    private int age = 0;
    /** 性别，0：未设置、1：男 、2：女 */
    private int sex = 0;

    public User(){
        super();
    }

    public User(Parcel in){
        super(in);
    }

    public static final Creator CREATOR = AVObjectCreator.instance;

    public void setAge(int age){
        put(Api.AGE, age);
        this.age = age;
    }

    public int getAge(){
        age = getInt(Api.AGE);
        return age;
    }

    public void setSex(int sex){
        put(Api.SEX, sex);
        this.sex = sex;
    }

    public int getSex(){
        sex = getInt(Api.SEX);
        return sex;
    }

    public void setAvatar(AVFile avatar){
        put(Api.AVATAR, avatar);
    }

    public AVFile getAvatar(){
        return getAVFile(Api.AVATAR);
    }


    public String getPassword() {
        return password;
    }

}
