package com.llw.mvvm.utils;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

/**
 * 用于在不支持 @AndroidEntryPoint 的类中使用
 * @author llw
 */
@EntryPoint
@InstallIn(ApplicationComponent.class)
public interface MVUtilsEntryPoint {
    MVUtils getMVUtils();
}
