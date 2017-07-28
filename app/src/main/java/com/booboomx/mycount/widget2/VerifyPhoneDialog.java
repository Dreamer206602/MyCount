package com.booboomx.mycount.widget2;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.booboomx.mycount.R;
import com.booboomx.mycount.utils.RegexUtils;
import com.booboomx.mycount.utils.UiUtils;

/**
 * Created by booboomx on 17/7/20.
 */
@SuppressLint("ValidFragment")
public class VerifyPhoneDialog extends AppCompatDialogFragment {

    private View mContentView;
    private EditText mEtCode;


    public VerifyPhoneDialog(Context context) {

        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_verify_phone, null);

        TextInputLayout tilCode = (TextInputLayout) mContentView.findViewById(R.id.til_code);


        if (tilCode.getEditText() == null)
            return;
        mEtCode = tilCode.getEditText();


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(UiUtils.getString(R.string.verify_phone))
                .setCancelable(false)
                .setView(mContentView, 0, 50, 0, 0)
                .setNegativeButton(UiUtils.getString(R.string.dialog_cancel), null)
                .setPositiveButton(UiUtils.getString(R.string.dialog_finish), null)
                .create();


        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                Button button=((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);


                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mCallBack == null) {
                            return;
                        }
                        String code=mEtCode.getText().toString();

                        if(!RegexUtils.checkCode(code)){
                            mCallBack.onVerifyFail(UiUtils.getString(R.string.hint_right_code));
                        }else {
                            mCallBack.onVerifySuccess(code);
                        }


                    }
                });

            }
        });


        return alertDialog;
    }


    public interface OnVerifyPhoneCallBack {
        void onVerifySuccess(String msg);
        void onVerifyFail(String msg);
    }


    public OnVerifyPhoneCallBack mCallBack;

    public void setCallBack(OnVerifyPhoneCallBack callBack) {
        mCallBack = callBack;
    }
}
