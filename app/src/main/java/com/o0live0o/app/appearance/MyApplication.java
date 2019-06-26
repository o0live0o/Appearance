package com.o0live0o.app.appearance;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyApplication extends Application {

    private static Context context;

    private SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        init();
    }

    public static Context getContext() {
        return context;
    }

    private void init() {

        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.context);

        String dataBase = pref.getString("database", this.getString(R.string.db_name));
        String user = pref.getString("db_user",  this.getString(R.string.db_user));
        String pwd = pref.getString("db_pwd",  this.getString(R.string.db_pwd));
        String server = pref.getString("db_server",  this.getString(R.string.db_ip));

        FinalData.setCheckC1(pref.getBoolean("chkc1",true));
        FinalData.setCheckDC(pref.getBoolean("chkdc",true));
        FinalData.setCheckF1(pref.getBoolean("chkf1",true));
        FinalData.setF1_To_DC(pref.getBoolean("chkf1_to_dc",true));
        FinalData.setDcStationNo(pref.getString("c1_no","1"));

        SSMSHelper.GetInstance().init(
                dataBase,
                server,
                user,
                pwd,context);
    }
}
