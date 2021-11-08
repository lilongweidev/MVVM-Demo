package com.llw.mvvm.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * 本地缓存工具类
 * @author llw
 */
public class SPUtils {

    private static final String NAME = "data_config";

    private static ContextWrapper mWrapper = null;
    private static SharedPreferences mPreferences = null;

    public static void init(Context context) {
        if(mWrapper == null){
            mWrapper = new ContextWrapper(context);
        }
        if(mPreferences == null){
            mPreferences = mWrapper.getSharedPreferences(NAME, ContextWrapper.MODE_PRIVATE);
        }
    }

    /**
     * putInt
     * @param key 键
     * @param value 缓存值
     */
    public static void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    /**
     * getInt
     * @param key 键
     * @param defValue 默认值
     * @return 结果
     */
    public static int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    /**
     * putString
     * @param key 键
     * @param value 缓存值
     */
    public static void putString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    /**
     * getString
     * @param key 键
     * @param defValue 默认值
     * @return 结果
     */
    public static String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    /**
     * putBoolean
     * @param key 键
     * @param value 缓存值
     */
    public static void putBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    /**
     * getBoolean
     * @param key 键
     * @param defValue 默认值
     * @return 结果
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    /**
     * putFloat
     * @param key 键
     * @param value 缓存值
     */
    public static void putFloat(String key, float value) {
        mPreferences.edit().putFloat(key, value).apply();
    }

    /**
     * getFloat
     * @param key 键
     * @param defValue 默认值
     * @return 结果
     */
    public static float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    /**
     * putLong
     * @param key 键
     * @param value 缓存值
     */
    public static void putLong(String key, long value) {
        mPreferences.edit().putLong(key,value).apply();
    }

    /**
     * getLong
     * @param key 值
     * @param defValue 默认值
     * @return 结果
     */
    public static long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    /**
     * putStringSet
     * @param key 键
     * @param value 缓存值
     */
    public static void putStringSet(String key, Set<String> value) {
        mPreferences.edit().putStringSet(key,value).apply();
    }

    /**
     * getStringSet
     * @param key 值
     * @param defValue 默认值
     * @return 结果
     */
    public static Set<String> getStringSet(String key, Set<String> defValue) {
        return mPreferences.getStringSet(key, defValue);
    }

    /**
     * 清除缓存
     * @param key 键
     */
    public static void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }
}
