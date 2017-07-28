package com.booboomx.mycount.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by booboomx on 17/7/20.
 */

public class ActivityUtils {

    private static Fragment mCurFragment = null;

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * 切换 Fragment
     */
    public static void switchFragment(@NonNull FragmentManager fragmentManager, Fragment targetFragment, int frameId) {
        switchFragment(fragmentManager, targetFragment, frameId,  0, 0);
    }

    /**
     * 带动画切换 Fragment
     */
    public static void switchFragment(@NonNull FragmentManager fragmentManager
            , @NotNull Fragment targetFragment, int frameId, int enter, int exit) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(enter != 0 && exit != 0) transaction.setCustomAnimations(enter, exit);
        if (!targetFragment.isAdded()) {
            if (mCurFragment != null) transaction.hide(mCurFragment);
            transaction.add(frameId, targetFragment).commit();
        } else {
            if (mCurFragment != null) transaction.hide(mCurFragment);
            transaction.show(targetFragment).commit();
        }
        mCurFragment = targetFragment;
    }



}
