package com.booboomx.mycount.base;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by booboomx on 17/7/17.
 * 【注意事项：】
 *
 * 由于应用当中用到了 leancloud 和 bugly 服务，其 appid、appkey 由于私密原因已经隐藏。
 * 请在 local.properties 文件配置如下信息，配置后即可正常运行项目：
 *
 * leancloud.appid=你在 leancloud 平台申请的 appid
 * leancloud.appkey=你在 leancloud 平台申请的 appkey
 * bugly.appid=你在 bugly 平台申请的 appid
 */

public class BaseApplication extends Application {

    public static BaseApplication mInstance;

    public static Context mContext;

    public static Context getContext() {
        return mContext;
    }


    public  String APP_ID="G0Gn0Ue2OOM2klBg4u9CWsve-gzGzoHsz";
    public  String APP_KEY="hDfOM8tWNzhRVIgG6zQ3hzgj";

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mInstance = new BaseApplication();



        initLeanCloud();
    }

    /**
     * 初始化 leanCloud
     */
    private void initLeanCloud() {

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,APP_ID,APP_KEY);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


}
