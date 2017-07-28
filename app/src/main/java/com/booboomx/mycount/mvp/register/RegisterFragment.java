package com.booboomx.mycount.mvp.register;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.booboomx.mycount.R;
import com.booboomx.mycount.base.BaseFragment;
import com.booboomx.mycount.data.User;
import com.booboomx.mycount.utils.ProgressUtils;
import com.booboomx.mycount.utils.RegexUtils;
import com.booboomx.mycount.utils.ToastUtils;
import com.booboomx.mycount.utils.UiUtils;
import com.booboomx.mycount.widget2.VerifyPhoneDialog;
import com.booboomx.mycount.data.Error;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterFragment extends BaseFragment implements RegisterContract.View {

    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.til_phone)
    TextInputLayout mTilPhone;
    @BindView(R.id.til_password)
    TextInputLayout mTilPassword;
    @BindView(R.id.btn_register)
    AppCompatButton mBtnRegister;

    private EditText mEdPhone, mEdPassword;
    private RegisterContract.Presenter mPresenter;
    private VerifyPhoneDialog mVerifyPhoneDialog;
    private String phone, password;
    public static final String TAG = RegisterFragment.class.getSimpleName();


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }


    @Override
    protected void onCreateFragment(Bundle savedInstanceState) {

        mEdPhone = mTilPhone.getEditText();
        mEdPassword = mTilPassword.getEditText();

        mEdPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                phone = s.toString().trim();
                if (!RegexUtils.checkPhone(phone)) {
                    mTilPhone.setError(UiUtils.getString(R.string.hint_right_phone));
                } else {
                    mTilPhone.setErrorEnabled(false);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                password = s.toString().trim();
                if (!RegexUtils.checkPassword(password)) {
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
        return R.layout.fragment_register;
    }

    @Override
    protected void onLazyLoadData() {

    }


    @OnClick(R.id.btn_register)
    public void onClick() {

        register();


    }

    private void register() {

        if (!RegexUtils.checkPhone(phone)) {
            ToastUtils.show(mContext, UiUtils.getString(R.string.hint_right_password));

        } else if (!RegexUtils.checkPassword(password)) {
            ToastUtils.show(mContext, UiUtils.getString(R.string.hint_right_password));

        } else {
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            mPresenter.register(user);

        }


    }


    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            ProgressUtils.show(mContext);
        } else {
            ProgressUtils.dismiss();
        }

    }

    @Override
    public void showRegisterSuccess() {

        ToastUtils.show(mContext, UiUtils.getString(R.string.toast_send_code));
        mVerifyPhoneDialog = new VerifyPhoneDialog(mContext);
        mVerifyPhoneDialog.show(getChildFragmentManager(), "dialog");
        mVerifyPhoneDialog.setCallBack(new VerifyPhoneDialog.OnVerifyPhoneCallBack() {
            @Override
            public void onVerifySuccess(String msg) {
                Log.i(TAG, "onVerifySuccess: -->" + msg);
                mPresenter.verifyPhone(msg);
            }

            @Override
            public void onVerifyFail(String msg) {
                ToastUtils.show(mContext, msg);

            }
        });

    }

    @Override
    public void showRegisterFail(Error e) {
        Log.i(TAG, "showRegisterFail: " + e.getMessage());
        ToastUtils.show(mContext, e.getMessage());

    }

    @Override
    public void showVerifyPhoneSuccess() {
        ToastUtils.show(mContext, UiUtils.getString(R.string.toast_register_success));
        mVerifyPhoneDialog.dismiss();
        finish();

    }

    @Override
    public void showVerifyPhoneFail(Error e) {
        Log.i(TAG, "showVerifyPhoneFail: " + e.getMessage());
        ToastUtils.show(mContext, e.getMessage());
    }
}
