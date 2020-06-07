package com.zsc.common.base;

import java.util.HashSet;
import java.util.Set;

public class ResultCode {

    private static final Set<ResultCode> RESULT_CODE_SET;

    public static final ResultCode SUCCESS;

    public static final ResultCode SYS_ERROR;

    private Integer code;

    private String msg;

    static {
        RESULT_CODE_SET = new HashSet<>();
        SUCCESS = new ResultCode(0, "成功");
        SYS_ERROR = new ResultCode(9999, "系统异常");
    }

    public ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        vaild();
    }

    public ResultCode(Integer code) {
        this.code = code;
        vaild();
    }

    private void vaild() {
        synchronized (RESULT_CODE_SET) {
            if (RESULT_CODE_SET.contains(this)) {
                throw new RuntimeException("the " + this.code + " code had existed.");
            } else {
                RESULT_CODE_SET.add(this);
            }
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
