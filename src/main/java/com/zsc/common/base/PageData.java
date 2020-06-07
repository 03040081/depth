package com.zsc.common.base;

import java.util.List;

public class PageData<T> {


    private long total;// 记录的总条数
    private List<T> list;

    public PageData() {
    }

    public PageData(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
