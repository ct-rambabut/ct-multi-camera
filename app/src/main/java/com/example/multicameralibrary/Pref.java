package com.example.multicameralibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;


public class Pref {
    private static Pref uniqInstance;
    private static SharedPreferences pref;
    public static SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public static Pref getIn(Context context) {
        if (uniqInstance == null) {
            uniqInstance = new Pref();
            pref = PreferenceManager.getDefaultSharedPreferences(context);
        }
        editor = pref.edit();
        return uniqInstance;
    }

    public void saveCameraFlash(String flash) {
        editor.putString("flash", flash);
        editor.apply();
    }


    public String getCameraFlash() {
        return pref.getString("flash", "");
    }

    public void setCamShowWaterMark(boolean value) {
        editor.putBoolean("waterMark", value);
        editor.apply();
    }

    public boolean getCamShowWaterMark() {
        return pref.getBoolean("waterMark", true);
    }

    public void setCamShowAddress(boolean value) {
        editor.putBoolean("address", value);
        editor.apply();
    }

    public boolean getCamShowAddress() {
        return pref.getBoolean("address", false);
    }

    public void setCamShowLatLng(boolean value) {
        editor.putBoolean("latLng", value);
        editor.apply();
    }

    public boolean getCamShowLatLng() {
        return pref.getBoolean("latLng", false);
    }

    public void setCamShowTime(boolean value) {
        editor.putBoolean("time", value);
        editor.apply();
    }

    public boolean getCamShowTime() {
        return pref.getBoolean("time", true);
    }

    public void setCamShowName(boolean value) {
        editor.putBoolean("name", value);
        editor.apply();
    }

    public boolean getCamShowName() {
        return pref.getBoolean("name", false);
    }

    public void setCamShowGuideBox(boolean value) {
        editor.putBoolean("guideBox", value);
        editor.apply();
    }

    public boolean getCamShowGuideBox() {
        return pref.getBoolean("guideBox", false);
    }

    public void setCamShowImageLabel(boolean value) {
        editor.putBoolean("imageLabel", value);
        editor.apply();
    }

    public boolean getCamShowImageLabel() {
        return pref.getBoolean("imageLabel", false);
    }

    public void setCamShowHelperImage(boolean value) {
        editor.putBoolean("helperImage", value);
        editor.apply();
    }

    public boolean getCamShowHelperImage() {
        return pref.getBoolean("helperImage", false);
    }

    public void setCamShowAt(String value) {
        editor.putString("at", value);
        editor.apply();
    }

    public String getCamShowAt() {
        return pref.getString("at", "");
    }

    public void setCamAspectRatio(String value) {
        editor.putString("aspectRatio", value);
        editor.apply();
    }

    public String getCamAspectRatio() {
        return pref.getString("aspectRatio", "Full");
    }

    public void setCamType(String value) {
        editor.putString("type", value);
        editor.apply();
    }

    public String getCamType() {
        return pref.getString("type", "");
    }

    public void setCamOriControl(String value) {
        editor.putString("oriControl", value);
        editor.apply();
    }

    public String getCamOriControl() {
        return pref.getString("oriControl", "");
    }

    public void setCamWatermarkGravityPosition(Integer value) {
        editor.putInt("watermarkPosition", value);
        editor.apply();
    }

    public int getCamWatermarkGravityPosition() {
        return pref.getInt("watermarkPosition", Gravity.BOTTOM | Gravity.LEFT);
    }

    public void setCamWatermarkValue(String value) {
        editor.putString("watermarkValue", value);
        editor.apply();
    }

    public String getCamWatermarkValue() {
        return pref.getString("watermarkValue", "");
    }

    public void setCamDescPosition(Integer value) {
        editor.putInt("descPosition", value);
        editor.apply();
    }

    public Integer getCamDescPosition() {
        return pref.getInt("descPosition", Gravity.BOTTOM | Gravity.RIGHT);
    }

    public void setCamDescValue(String value) {
        editor.putString("descValue", value);
        editor.apply();
    }

    public String getCamDescValue() {
        return pref.getString("descValue", "");
    }


    public void clearPref() {
        editor.clear().apply();
    }

}
