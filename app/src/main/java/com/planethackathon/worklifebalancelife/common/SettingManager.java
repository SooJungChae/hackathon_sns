package com.planethackathon.worklifebalancelife.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by minseok on 2018. 9. 1..
 * hackathon_sns.
 */
public class SettingManager {

    private static final String SUFFIX_SHAREDPREFERENCE_KEY = "52HOURS_PREFERENCES";

    public static final String KEY_USER_ID = "__key_user_id_";
    public static final String KEY_USER_NAME = "__key_user_name_";
    public static final String KEY_USER_ELAPSED_TIME = "__key_user_elapsed_time_";

    Context mContext;

    static boolean isLoaded = false;

    String userId;
    String userName;
    long userElapsedTime;

    public SettingManager(Context mContext) {
        this.mContext = mContext;
    }

    public void loadSettingValue() {
        SharedPreferences mSharedPref = getSharedPreference(mContext);
        if (mContext != null && mSharedPref != null) {
            this.userId = mSharedPref.getString(KEY_USER_ID, "");
            this.userName = mSharedPref.getString(KEY_USER_NAME, "");
            this.userElapsedTime = mSharedPref.getLong(KEY_USER_ELAPSED_TIME, 0);

            isLoaded = true;
        }
    }

    public boolean setUserId(String userId) {
        this.userId = userId;

        return getSharedPreference(mContext).edit()
                .putString(KEY_USER_ID, userId)
                .commit();
    }

    public String getUserId() { return userId; }

    public boolean setUserName(String userName) {
        this.userName = userName;

        return getSharedPreference(mContext).edit()
                .putString(KEY_USER_NAME, userName)
                .commit();
    }

    public String getUserName() { return userName; }

    public boolean setUserElapsedTime(long userElapsedTime) {
        this.userElapsedTime = userElapsedTime;

        return getSharedPreference(mContext).edit()
                .putLong(KEY_USER_ELAPSED_TIME, userElapsedTime)
                .commit();
    }

    public Long getUserElapsedTime() { return userElapsedTime; }

    private static SharedPreferences getSharedPreference(@NonNull Context context) {
        return context.getSharedPreferences(SUFFIX_SHAREDPREFERENCE_KEY, Context.MODE_PRIVATE);
    }
}
