package com.o0live0o.app.appearance.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class L {

    public static final String TAG = "Exrerior";

    private static int logMaxCount = 100;

    public static LinkedList<String> logList = new LinkedList<>();

    public static void d(String s) {
        Log.d("Exrerior", s);
        if (logList.size() > logMaxCount) {
            logList.removeLast();
        }

        //logList.addFirst("[" + getTime() + "]_" + s);
    }

    public static void i(String s){
        if (logList.size() > logMaxCount) {
            logList.removeLast();
        }
        logList.addFirst("[" + getTime() + "]_" + s);
    }

    private static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

}

