package com.o0live0o.app.dbutils;

import android.os.Parcel;
import android.os.Parcelable;

public class DbResult implements Parcelable {

    private int code;
    private String msg;
    private int count;
    private boolean succ;

    public DbResult(){
    }

    protected DbResult(Parcel in) {
        code = in.readInt();
        msg = in.readString();
        count = in.readInt();
        succ = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(code);
        dest.writeString(msg);
        dest.writeInt(count);
        dest.writeByte((byte) (succ ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DbResult> CREATOR = new Creator<DbResult>() {
        @Override
        public DbResult createFromParcel(Parcel in) {
            return new DbResult(in);
        }

        @Override
        public DbResult[] newArray(int size) {
            return new DbResult[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

    public boolean isSucc() {
        return succ;
    }

    public void setSucc(boolean succ) {
        this.succ = succ;
    }
}
