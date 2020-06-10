package com.o0live0o.app.appearance;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.service.NetHelper;
import com.o0live0o.app.appearance.service.WebServiceHelper;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.blankj.utilcode.util.TimeUtils.date2String;
import static com.blankj.utilcode.util.TimeUtils.getNowDate;

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

        String web_url = pref.getString("web_url",getString(R.string.web_url));
        String stationNo = pref.getString("station_no",getString(R.string.web_station_no));
        String web_key = pref.getString("web_key",getString(R.string.web_key));
        String web_jkxlh = pref.getString("web_jkxlh",getString(R.string.web_jcxlh));
        String web_org= pref.getString("web_org",getString(R.string.web_org));

        FinalData.setCheckC1(pref.getBoolean("chkc1",true));
        FinalData.setCheckDC(pref.getBoolean("chkdc",true));
        FinalData.setCheckF1(pref.getBoolean("chkf1",true));
        FinalData.setF1_To_DC(pref.getBoolean("chkf1_to_dc",true));
        FinalData.setDcStationNo(pref.getString("c1_no","1"));

        FinalData.setStationNo(stationNo);
        FinalData.setWebserviceUrl(web_url);
        FinalData.setWebservicekey(web_key);
        FinalData.setJkxlh(web_jkxlh);
        String s = date2String(getNowDate());
        String s1 = NetworkUtils.getIPAddress(true);

        SSMSHelper.GetInstance().init(
                dataBase,
                server,
                user,
                pwd,context);

        WebServiceHelper.getInstance().init(web_url,web_org);
    }
}
