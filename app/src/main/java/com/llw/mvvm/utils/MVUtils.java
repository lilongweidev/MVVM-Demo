package com.llw.mvvm.utils;

import android.content.Context;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

import java.util.Collections;
import java.util.Set;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

/**
 * MMKV Utils
 *
 * @author llw
 * @description MVUtils
 */
@Module
@InstallIn(ApplicationComponent.class)
public class MVUtils {

    private static MMKV mmkv;

    @Provides
    @Singleton
    public MVUtils getMVUtils(@ApplicationContext Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.defaultMMKV();
        return new MVUtils();
    }

    /**
     * 写入基本数据类型缓存
     *
     * @param key    键
     * @param object 值
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            mmkv.encode(key, (String) object);
        } else if (object instanceof Integer) {
            mmkv.encode(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mmkv.encode(key, (Boolean) object);
        } else if (object instanceof Float) {
            mmkv.encode(key, (Float) object);
        } else if (object instanceof Long) {
            mmkv.encode(key, (Long) object);
        } else if (object instanceof Double) {
            mmkv.encode(key, (Double) object);
        } else if (object instanceof byte[]) {
            mmkv.encode(key, (byte[]) object);
        } else {
            mmkv.encode(key, object.toString());
        }
    }

    public void putSet(String key, Set<String> sets) {
        mmkv.encode(key, sets);
    }

    public void putParcelable(String key, Parcelable obj) {
        mmkv.encode(key, obj);
    }

    public Integer getInt(String key) {
        return mmkv.decodeInt(key, 0);
    }

    public Integer getInt(String key, int defaultValue) {
        return mmkv.decodeInt(key, defaultValue);
    }

    public Double getDouble(String key) {
        return mmkv.decodeDouble(key, 0.00);
    }

    public Double getDouble(String key, double defaultValue) {
        return mmkv.decodeDouble(key, defaultValue);
    }

    public Long getLong(String key) {
        return mmkv.decodeLong(key, 0L);
    }

    public Long getLong(String key, long defaultValue) {
        return mmkv.decodeLong(key, defaultValue);
    }

    public Boolean getBoolean(String key) {
        return mmkv.decodeBool(key, false);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return mmkv.decodeBool(key, defaultValue);
    }

    public Float getFloat(String key) {
        return mmkv.decodeFloat(key, 0F);
    }

    public Float getFloat(String key, float defaultValue) {
        return mmkv.decodeFloat(key, defaultValue);
    }

    public byte[] getBytes(String key) {
        return mmkv.decodeBytes(key);
    }

    public byte[] getBytes(String key, byte[] defaultValue) {
        return mmkv.decodeBytes(key, defaultValue);
    }

    public String getString(String key) {
        return mmkv.decodeString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return mmkv.decodeString(key, defaultValue);
    }

    public Set<String> getStringSet(String key) {
        return mmkv.decodeStringSet(key, Collections.<String>emptySet());
    }

    public Parcelable getParcelable(String key) {
        return mmkv.decodeParcelable(key, null);
    }

    /**
     * 移除某个key对
     *
     * @param key
     */
    public void removeKey(String key) {
        mmkv.removeValueForKey(key);
    }

    /**
     * 清除所有key
     */
    public void clearAll() {
        mmkv.clearAll();
    }
}
