package com.booboomx.mycount.utils;

import com.avos.avoscloud.AVUser;
import com.booboomx.mycount.R;
import com.booboomx.mycount.data.User;

/**
 * Created by booboomx on 17/7/27.
 */

public class UserUtils {

    private UserUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 校验用户是否已经登录。
     * @return true: 已登录 false: 未登录
     */
    public static boolean checkLogin(){
        return null != AVUser.getCurrentUser(User.class);
    }

    /**
     * 获取当前登录 User 对象。
     * @return User 对象
     */
    public static User getUser(){
        return AVUser.getCurrentUser(User.class);
    }

    /**
     * 获取当前登录用户 id。
     * @return String 用户 id
     */
    public static String getUid(){
        User user = AVUser.getCurrentUser(User.class);
        return user != null ? user.getObjectId() : "";
    }

    /**
     * 根据类型获取性别字符串。
     * @param sex 性别类型，0：未设置、1：男、2：女。
     * @return
     */
    public static String getSexText(int sex){
        if(sex == 1){
            return UiUtils.getString(R.string.man);
        }else if(sex == 2){
            return UiUtils.getString(R.string.woman);
        }else{
            return UiUtils.getString(R.string.not_set);
        }
    }
}
