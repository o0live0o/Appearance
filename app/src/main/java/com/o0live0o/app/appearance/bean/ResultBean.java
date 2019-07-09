package com.o0live0o.app.appearance.bean;

public class ResultBean {
    private boolean isSucc;
    private String msg;

    public ResultBean()
    {
        isSucc = false;
        msg = "NULL";
    }

    public boolean isSucc() {
        return isSucc;
    }

    public void setSucc(boolean succ) {
        isSucc = succ;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
