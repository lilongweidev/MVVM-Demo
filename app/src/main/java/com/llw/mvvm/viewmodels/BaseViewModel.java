package com.llw.mvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * 基础ViewModel
 * @author llw
 */
public class BaseViewModel extends ViewModel {

    public LiveData<String> failed;
}
