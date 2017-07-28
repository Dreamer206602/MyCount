package com.booboomx.mycount.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by booboomx on 17/7/17.
 */

public class AppUtils {


    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context getContext(){
        return UiUtils.getContext();
    }

    /**
     * 获取当前应用包名
     * @return 如: com.airsaid.app
     */
    public static String getPackageName(){
        return UiUtils.getContext().getPackageName();
    }

    /**
     * 获取当前应用版本名称
     * @return 如: 1.0.0
     */
    public static String getAppVersionName(){
        String versionName = "";
        try {
            PackageInfo packageInfo = getContext().getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取当前应用最后一次更新的时间
     * @return 时间戳
     */
    public static long getLastUpdateTime(){
        try {
            PackageInfo packageInfo = getContext().getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            return packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
