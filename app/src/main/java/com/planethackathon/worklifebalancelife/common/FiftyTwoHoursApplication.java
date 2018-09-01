package com.planethackathon.worklifebalancelife.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by minseok on 2018. 9. 1..
 * hackathon_sns.
 */
public class FiftyTwoHoursApplication extends Application {
    static SettingManager mSettingManager = null;
    static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
    }

    public static SettingManager getSettingManager() {
        if (mSettingManager == null) {
            mSettingManager = new SettingManager(mContext);
        }

        if (!SettingManager.isLoaded) {
            mSettingManager.loadSettingValue();
        }

        return mSettingManager;
    }
}
