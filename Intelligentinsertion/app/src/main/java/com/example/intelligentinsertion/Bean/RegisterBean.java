package com.example.intelligentinsertion.Bean;

import com.google.gson.annotations.SerializedName;

public class RegisterBean {
    @SerializedName("code")
    private int code;
    @SerializedName("data")
    private String data;
    @SerializedName("msg")
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
