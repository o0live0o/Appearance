package com.o0live0o.app.appearance.log;

import android.os.Parcel;
import android.os.Parcelable;

public class LogBean implements Parcelable {
    public String msg;
    public int level;

    public LogBean(int level, String msg) {
        this.level = level;
        this.msg = msg;
    }

    protected LogBean(Parcel in) {
        msg = in.readString();
        level = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeInt(level);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LogBean> CREATOR = new Creator<LogBean>() {
        @Override
        public LogBean createFromParcel(Parcel in) {
            return new LogBean(in);
        }

        @Override
        public LogBean[] newArray(int size) {
            return new LogBean[size];
        }
    };
}