package com.example.multicameralibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;


public class Pref {
    private static Pref uniqInstance;
    private static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    private static String[] log_key, log_ikey;

    @SuppressLint("CommitPrefEdits")
    public static Pref getIn(Context context) {
        if (uniqInstance == null) {
            uniqInstance = new Pref();
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        editor = pref.edit();
        return uniqInstance;
    }

    public boolean isFCMCalled() {
        return pref.getBoolean("isFCMCalled", false);
    }

    public void setFCMCalled(boolean FCMCalled) {
        editor.putBoolean("isFCMCalled", FCMCalled);
        editor.apply();
    }

    public String getFcm_token() {
        return pref.getString("fcm_token", "");
    }

    public void setFcm_token(String fcm_token) {
        editor.putString("fcm_token", fcm_token);
        editor.apply();
    }

    public void saveUserDetails(ArrayList<String> values) {
        for (int i = 0; i < log_key.length; i++) {
            editor.putString(log_key[i], values.get(i));
        }
        editor.apply();
    }

    public void deleteUserDetails() {
        for (String aLog_key : log_key) {
            editor.putString(aLog_key, "");
        }
        editor.apply();
    }


    public void saveUseriDetails(ArrayList<Integer> values) {
        for (int j = 0; j < log_ikey.length; j++) {
            editor.putInt(log_ikey[j], values.get(j));
        }
        editor.apply();
    }


    public void deleteUseriDetails() {
        for (String aLog_ikey : log_ikey) {
            editor.putInt(aLog_ikey, 0);
        }
        editor.apply();
    }

    /*Save Individual values*/
    public String getSharedPref_val(String txt) {
        return pref.getString(txt, "");
    }

    public int getPrefintValue(String name) {
        return pref.getInt(name, 0);
    }


    public void setDash_flag(boolean dash_flag) {
        editor.putBoolean("dash_flag", dash_flag);
        editor.apply();
    }

    public boolean isDash_flag() {
        return pref.getBoolean("dash_flag", false);
    }

    public int getDash_id() {
        return pref.getInt("dash_id", 0);
    }

    public void setDash_id(int dash_id) {
        editor.putInt("dash_id", dash_id);
        editor.apply();
    }


    public String getMobile() {
        return pref.getString("mobile", "");
    }

    public void setMobile(String app_code) {
        editor.putString("mobile", app_code);
        editor.apply();
    }


    public void firstTimeAskingPermission(String permission, boolean isFirstTime) {
        editor.putBoolean(permission, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeAskingPermission(String permission) {
        return pref.getBoolean(permission, true);
    }

    public String getLatitude() {
        return pref.getString("latitude", "");
    }

    public void setLatitude(String latitude) {
        editor.putString("latitude", latitude);
        editor.apply();
    }

    public String getLongitude() {
        return pref.getString("longitude", "");
    }

    public void setLongitude(String longitude) {
        editor.putString("longitude", longitude);
        editor.apply();
    }


    public String getApp_code() {
        return pref.getString("app_code", "");
    }

    public void setApp_code(String app_code) {
        editor.putString("app_code", app_code);
        editor.apply();
    }

    public void setCrt(String access) {
        editor.putString("crt", access);
        editor.apply();
    }

    public String getCrt() {
        return pref.getString("crt", "");
    }


    public boolean getFirstTime() {
        return pref.getBoolean("firsttime", false);
    }

    public void setFirstTime(boolean access) {
        editor.putBoolean("firsttime", access);
        editor.apply();
    }


    public void saveDeviceId(String id) {
        editor.putString("device_id", id);
        editor.apply();
    }

    public String getDeviceId() {
        return pref.getString("device_id", "");
    }


    public void saveOrientationFlag(boolean flag) {
        editor.putBoolean("orientation_flag", flag);
        editor.apply();
    }

    public boolean getOrientationFlag(String flag) {
        return pref.getBoolean(flag, false);
    }


    public void setSync_done(String sync_done) {
        editor.putString("sync_done", sync_done);
        editor.apply();
    }

    public String getSync_done() {
        return pref.getString("sync_done", "");
    }

    public void setUpdatedDate(String date) {
        editor.putString("caller_db_updated_date", date);
        editor.apply();
    }

    public String getUpdatedDate() {
        return pref.getString("caller_db_updated_date", "");
    }


    public void saveCameraFlash(String flash) {
        editor.putString("flash", flash);
        editor.apply();
    }


    public String getCameraFlash() {
        return pref.getString("flash", "");
    }

    public void setRemember(boolean remember) {
        editor.putBoolean("remember", remember);
        editor.apply();
    }

    public boolean getRemember() {
        return pref.getBoolean("remember", false);
    }

    public String getPhone_number() {
        return pref.getString("phone_number", "");
    }

    public void setPhone_number(String phone_number) {
        editor.putString("phone_number", phone_number);
        editor.apply();
    }


    public int getRole_id() {
        return pref.getInt(log_ikey[0], 0);
    }

    public int getDealer_group_id() {
        return pref.getInt(log_ikey[1], 0);
    }


    public int getNewversion_notice() {
        return pref.getInt("newversion_notice", 3);
    }

    public void setNewversion_notice(int newversion_notice) {
        editor.putInt("newversion_notice", newversion_notice);
        editor.apply();
    }

    public void setDyn_key(String dyn_key) {
        editor.putString("dyn_key", dyn_key);
        editor.apply();
    }

    public void setEncryption(String encryption) {
        editor.putString("encryption", encryption);
        editor.apply();
    }


    public String getUser_name() {
        return pref.getString(log_key[0], "");
    }

    public String getUser_id() {
        return pref.getString(log_key[1], "");
    }

    public String getDyn_key() {
        return pref.getString(log_key[2], "");
    }

    public String getEncryption() {
        return pref.getString(log_key[3], "");
    }

    public String getDealer_grp_name() {
        return pref.getString(log_key[4], "");
    }

    public String getDealer_brn_name() {
        return pref.getString(log_key[5], "");
    }

    public String getDealer_state_id() {
        return pref.getString(log_key[6], "");
    }

    public String getDealer_city_id() {
        return pref.getString(log_key[7], "");
    }

    public String getMg_id() {
        return pref.getString(log_key[8], "");
    }


    public String getDamagePath() {
        return pref.getString("damageuri", "");
    }

    public void setDamagePath(String damageuri) {
        editor.putString("damageuri", damageuri);
        editor.apply();
    }


    public int getExtraCount() {
        return pref.getInt("extraCount", 0);
    }

    public void setExtraCount(int extraCount) {
        editor.putInt("extraCount", extraCount);
        editor.apply();
    }

    public boolean isFromSplash() {
        return pref.getBoolean("splash", false);
    }

    public void setFromSplash(boolean splash) {
        editor.putBoolean("splash", splash);
        editor.apply();
    }
}
