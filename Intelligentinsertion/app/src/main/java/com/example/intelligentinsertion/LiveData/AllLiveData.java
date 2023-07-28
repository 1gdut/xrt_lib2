package com.example.intelligentinsertion.LiveData;

import androidx.lifecycle.MutableLiveData;

public class AllLiveData {
    //注册时的问题
    public static MutableLiveData<String> getQuestion = new MutableLiveData<>();
    //注册时问题密码返回的
    public static MutableLiveData<Integer> getQaCode = new MutableLiveData<>();
    //改密码时的
    public static MutableLiveData<String> getChangePwdCode = new MutableLiveData<>();
    //登录返回的
    public static MutableLiveData<String> loginHeader = new MutableLiveData<>();
    //注册返回的
    public static MutableLiveData<String> registerHeader = new MutableLiveData<>();
    //登陆后显示的用户名
    public static MutableLiveData<String> userName = new MutableLiveData<>();

    //忘记密码时的
    public static MutableLiveData<String> forgetQuestion = new MutableLiveData<>();

}
