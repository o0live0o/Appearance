package com.o0live0o.app.appearance;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.o0live0o.app.dbutils.SSMSHelper;

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

        String dataBase = pref.getString("database", "IVS30");
        String user = pref.getString("db_user", "sa");
        String pwd = pref.getString("db_pwd", "123456");
        String server = pref.getString("db_server", "192.168.2.233");

        SSMSHelper.GetInstance().init(
                dataBase,
                server,
                user,
                pwd);
    }
}
