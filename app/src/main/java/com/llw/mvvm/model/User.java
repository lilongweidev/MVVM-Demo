package com.llw.mvvm.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.llw.mvvm.BR;

/**
 * @author llw
 * @description User
 * @date 2021/10/26 9:46
 */
public class User extends BaseObservable {

    public String account;
    public String pwd;

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);//只通知改变的参数
    }

    @Bindable
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        notifyPropertyChanged(BR.pwd);//只通知改变的参数
    }

    public User(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }
}
