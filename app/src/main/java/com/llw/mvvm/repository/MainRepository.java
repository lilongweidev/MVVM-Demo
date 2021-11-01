package com.llw.mvvm.repository;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.llw.mvvm.api.ApiService;
import com.llw.mvvm.model.BiYingResponse;
import com.llw.mvvm.network.BaseObserver;
import com.llw.mvvm.network.NetworkApi;
import com.llw.mvvm.network.utils.KLog;

/**
 * Main存储库 用于对数据进行处理
 * @author llw
 */
public class MainRepository {

    @SuppressLint("CheckResult")
    public MutableLiveData<BiYingResponse> getBiYing() {
        final MutableLiveData<BiYingResponse> biyingImage = new MutableLiveData<>();
        ApiService apiService = NetworkApi.createService(ApiService.class);
        apiService.biying().compose(NetworkApi.applySchedulers(new BaseObserver<BiYingResponse>() {
            @Override
            public void onSuccess(BiYingResponse biYingImgResponse) {
                KLog.d(new Gson().toJson(biYingImgResponse));
                biyingImage.setValue(biYingImgResponse);
            }

            @Override
            public void onFailure(Throwable e) {
                KLog.e("BiYing Error: " + e.toString());
            }
        }));
        return biyingImage;
    }
}
