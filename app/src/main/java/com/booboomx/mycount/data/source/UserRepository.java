package com.booboomx.mycount.data.source;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.data.Error;
/**
 * Created by booboomx on 17/7/24.
 */

public class UserRepository implements UserDataSource {


    /**
     * 用户注册，注册成功后会发送验证码给用户，此时验证完成后才算注册完成。
     *
     * @param user
     * @param callback
     */
    @Override
    public void register(User user, final RegisterCallback callback) {
        AVUser avUser = new AVUser();
        String phone = user.getPhone();
        String password = user.getPassword();
        avUser.setUsername(phone);
        avUser.setPassword(password);
        avUser.setMobilePhoneNumber(phone);
        avUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.registerSuccess();
                } else {
                    callback.registerFail(new Error(e));
                }

            }
        });


    }

    /**
     * 登录
     *
     * @param user
     * @param callback
     */
    @Override
    public void login(User user, final LoginCallback callback) {

        AVUser.loginByMobilePhoneNumberInBackground(user.getPhone(), user.getPassword(), new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    callback.loginSuccess();
                } else {
                    e.printStackTrace();
                    callback.loginFail(new Error(e));
                }
            }
        });

    }

    /**
     * 根据手机号 请求验证码
     *
     * @param phone
     * @param callback
     */
    @Override
    public void requestPhoneVerify(String phone, final SendVerifyCodeCallback callback) {

        AVUser.requestMobilePhoneVerifyInBackground(phone, new com.avos.avoscloud.RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.sendVerifyCodeSuccess();
                } else {
                    e.printStackTrace();
                    callback.sendVerifyCodeFail(new Error(e));
                }
            }
        });


    }

    /**
     * 验证手机号
     *
     * @param code
     * @param callback
     */
    @Override
    public void verifyPhone(String code, final VerifyPhoneCallback callback) {
        AVUser.verifyMobilePhoneInBackground(code, new AVMobilePhoneVerifyCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.verifySuccess();
                } else {
                    e.printStackTrace();
                    callback.verifyFail(new Error(e));
                }
            }
        });


    }

    /**
     * 发送手机号码 重置密码的验证码
     * @param phone
     * @param callback
     */
    @Override
    public void requestPasswordResetBySmsCode(String phone, final RequestMobileCodeCallback callback) {

        AVUser.requestPasswordResetBySmsCodeInBackground(phone, new com.avos.avoscloud.RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    callback.requestSuccess();
                }else {
                    e.printStackTrace();
                    callback.requestFail(new Error(e));
                }
            }
        });


    }

    @Override
    public void resetPasswordBySmsCode(String code, String newPassword, final UpdatePasswordCallback callback) {

        AVUser.resetPasswordBySmsCodeInBackground(code, newPassword, new com.avos.avoscloud.UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    callback.updateSuccess();
                }else {
                    e.printStackTrace();
                    callback.updateFail(new Error(e));

                }
            }
        });

    }

    @Override
    public void saveUserInfo(User user, final SaveUserInfoCallback callback) {

        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){

                    callback.saveSuccess();
                }else {

                    e.printStackTrace();
                    callback.saveFail(new Error(e));

                }
            }
        });

    }
}
