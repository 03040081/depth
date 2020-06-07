package com.zsc.common.base;

import com.alibaba.fastjson.JSON;

public class PageBean<T> {
    private int code;
    private String msg;
    private PageData<T> data;

    public PageBean() {}

    public PageBean(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PageData<T> getData() {
        return data;
    }

    public void setData(PageData<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
