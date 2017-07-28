package com.booboomx.mycount.data.source;

import com.booboomx.mycount.data.User;
import com.booboomx.mycount.data.Error;

/**
 * Created by booboomx on 17/7/24.
 */

public interface UserDataSource {

    /** 注册 */
    void register(User user, RegisterCallback callback);

    /** 登录 */
    void login(User user, LoginCallback callback);

    /** 通过手机号获取验证码 */
    void requestPhoneVerify(String phone, SendVerifyCodeCallback callback);

    /** 验证手机号 */
    void verifyPhone(String code, VerifyPhoneCallback callback);

    /** 通过手机号发送验证码来重置密码 */
    void requestPasswordResetBySmsCode(String phone, RequestMobileCodeCallback callback);

    /** 通过手机号和验证码重置密码 */
    void resetPasswordBySmsCode(String phone, String code, UpdatePasswordCallback callback);

    /** 保存用户信息 */
    void saveUserInfo(User user, SaveUserInfoCallback callback);

    interface RegisterCallback{
        void registerSuccess();
        void registerFail(Error e);
    }

    interface LoginCallback{
        void loginSuccess();
        void loginFail(Error e);
    }

    interface VerifyPhoneCallback{
        void verifySuccess();
        void verifyFail(Error e);
    }

    interface SendVerifyCodeCallback {
        void sendVerifyCodeSuccess();
        void sendVerifyCodeFail(Error e);
    }

    interface RequestMobileCodeCallback {
        void requestSuccess();
        void requestFail(Error e);
    }

    interface UpdatePasswordCallback {
        void updateSuccess();
        void updateFail(Error e);
    }

    interface SaveUserInfoCallback{
        void saveSuccess();
        void saveFail(Error e);
    }

}
