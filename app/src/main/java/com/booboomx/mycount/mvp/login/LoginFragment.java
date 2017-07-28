package com.booboomx.mycount.mvp.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.booboomx.mycount.R;
import com.booboomx.mycount.base.BaseFragment;
import com.booboomx.mycount.data.Error;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.mvp.register.RegisterActivity;
import com.booboomx.mycount.utils.ProgressUtils;
import com.booboomx.mycount.utils.RegexUtils;
import com.booboomx.mycount.utils.ToastUtils;
import com.booboomx.mycount.utils.UiUtils;
import com.booboomx.mycount.utils.UserUtils;
import com.booboomx.mycount.widget2.VerifyPhoneDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 */
public class LoginFragment extends BaseFragment implements View.OnFocusChangeListener, LoginContract.View {

    public static LoginFragment fragment;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.til_phone)
    TextInputLayout mTilPhone;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.tv_forget_password)
    TextView mTvForgetPassword;
    @BindView(R.id.tv_register)
    TextView mTvRegister;

    private EditText mEtPhone;
    private EditText mEtPassword;
    private VerifyPhoneDialog mVerifyPhoneDialog;
    private LoginContract.Presenter mPresenter;


    public static LoginFragment newInstance() {
        fragment = new LoginFragment();
        return fragment;
    }

    @Override
    protected void onCreateFragment(Bundle savedInstanceState) {

        mEtPhone = mTilPhone.getEditText();
        mEtPassword = mTilPassword.getEditText();

        if (mEtPassword == null) return;
        mEtPassword.setOnFocusChangeListener(this);

        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String phone = s.toString();
                if (!RegexUtils.checkPhone(phone)) {

                    mTilPhone.setErrorEnabled(true);
                    mTilPhone.setError(UiUtils.getString(R.string.hint_right_phone));
                } else {
                    mTilPhone.setErrorEnabled(false);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = s.toString();
                if (!RegexUtils.checkPassword(password)) {
                    mTilPassword.setErrorEnabled(true);
                    mTilPassword.setError(UiUtils.getString(R.string.hint_right_password));
                } else {
                    mTilPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public int getFragmentLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void onLazyLoadData() {


    }


    @OnClick({R.id.tv_forget_password, R.id.tv_register, R.id.btn_login})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(mActivity, RegisterActivity.class));
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {

        String phone = mEtPhone.getText().toString();
        String password = mEtPassword.getText().toString();


        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);

        if (mPresenter.checkUserInfo(user)) {
            if (!mTilPhone.isErrorEnabled() && !mTilPassword.isErrorEnabled()) {
                ProgressUtils.show(mContext, UiUtils.getString(R.string.load_login));
                mPresenter.login(user);
            } else {
                ToastUtils.show(mContext, UiUtils.getString(R.string.hint_right_phone_or_password));
            }
        } else {
            ToastUtils.show(mContext, UiUtils.getString(R.string.toast_input_name_or_pwd));
        }

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mIvHead.setImageResource(hasFocus ? R.mipmap.ic_login_pwd : R.mipmap.ic_login_name);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void showLoginSuccess() {

        ToastUtils.show(mContext,UiUtils.getString(R.string.login_success));
        User user = UserUtils.getUser();
        String objectId = user.getObjectId();


    }

    @Override
    public void showLoginFail(Error e) {

        ProgressUtils.dismiss();
        //判断手机号是否通过验证
        if (e.code == 215) {
            //提示验证手机号
            Snackbar.make(mIvHead, UiUtils.getString(R.string.hint_verify_phone), Snackbar.LENGTH_LONG)
                    .setAction(UiUtils.getString(R.string.verify), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String phone = mEtPhone.getText().toString().trim();

                            if (!RegexUtils.checkPhone(phone)) {
                                ToastUtils.show(mContext, UiUtils.getString(R.string.hint_right_phone));
                            } else {
                                ProgressUtils.show(mContext);
                                mPresenter.requestPhoneVerify(phone);
                            }

                            mVerifyPhoneDialog = new VerifyPhoneDialog(mContext);
                            mVerifyPhoneDialog.show(getChildFragmentManager(), "dialog");
                            mVerifyPhoneDialog.setCallBack(new VerifyPhoneDialog.OnVerifyPhoneCallBack() {
                                @Override
                                public void onVerifySuccess(String msg) {
                                    ProgressUtils.show(mContext);
                                    mPresenter.verifyPhone(msg);
                                }

                                @Override
                                public void onVerifyFail(String msg) {

                                    ToastUtils.show(mContext, msg);
                                }
                            });


                        }
                    });


        }

    }

    @Override
    public void showSendVerifyCodeSuccess() {
        ProgressUtils.dismiss();
        ToastUtils.show(mContext, UiUtils.getString(R.string.toast_send_code));
    }

    @Override
    public void showSendVerifyCodeFail(Error e) {
        ProgressUtils.dismiss();
        ToastUtils.show(mContext, e.getMessage());

    }

    @Override
    public void showVerifyPhoneSuccess() {

        ProgressUtils.dismiss();
        ToastUtils.show(mContext, UiUtils.getString(R.string.toast_verify_phone_success));
        mVerifyPhoneDialog.dismiss();
    }

    @Override
    public void showVerifyPhoneFail(Error e) {
        ProgressUtils.dismiss();
        ToastUtils.show(mContext, e.getMessage());

    }

    /**
     * 创建默认的支出，收入分类
     *
     * @param uid
     */
    @Override
    public void createDefaultType(String uid) {

    }
}
