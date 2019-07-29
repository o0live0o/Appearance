package com.o0live0o.app.appearance.activitys;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.bean.ResultBean;
import com.o0live0o.app.appearance.service.WebServiceHelper;
import com.o0live0o.app.appearance.utils.CreateXML;
import com.o0live0o.app.appearance.views.InputWithHeadView;

public class WebserviceActivity extends BaseActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private InputWithHeadView ihWeb_URL;
    private InputWithHeadView ihWeb_Org;
    private InputWithHeadView ihWeb_StationNo;
    private InputWithHeadView ihWeb_Key;
    private InputWithHeadView ihWeb_Jkxlh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webservice);
        init();
    }

    private void init(){
        initNavBar(true,"WebService",false);

        ihWeb_URL = findViewById(R.id.web_url);
        ihWeb_Org = findViewById(R.id.web_org);
        ihWeb_StationNo = findViewById(R.id.web_stationno);
        ihWeb_Key = findViewById(R.id.web_key);
        ihWeb_Jkxlh = findViewById(R.id.web_jkxlh);

        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        String web_url = pref.getString("web_url",getString(R.string.web_url));
        String stationNo = pref.getString("station_no",getString(R.string.web_station_no));
        String web_key = pref.getString("web_key",getString(R.string.web_key));
        String web_jkxlh = pref.getString("web_jkxlh",getString(R.string.web_jcxlh));
        String web_org= pref.getString("web_org",getString(R.string.web_org));

        ihWeb_URL.setInputStr(web_url);
        ihWeb_Org.setInputStr(web_org);
        ihWeb_StationNo.setInputStr(stationNo);
        ihWeb_Key.setInputStr(web_key);
        ihWeb_Jkxlh.setInputStr(web_jkxlh);
    }

    public void onSaveWeb(View view) {
        String web_url = ihWeb_URL.getInputStr();
        String stationNo = ihWeb_StationNo.getInputStr();
        String web_key = ihWeb_Key.getInputStr();
        String web_jkxlh = ihWeb_Jkxlh.getInputStr();
        String web_org = ihWeb_Org.getInputStr();

        editor = pref.edit();
        editor.putString("web_url", web_url);
        editor.putString("web_org", web_org);
        editor.putString("station_no", stationNo);
        editor.putString("web_key", web_key);
        editor.putString("web_jkxlh", web_jkxlh);
        editor.apply();

        WebServiceHelper.getInstance().init(web_url, web_org);
    }

    public void onTest(View view) {
        new SyncTimeTask().execute();
    }


    class SyncTimeTask extends AsyncTask<Void,Void,ResultBean>{

        @Override
        protected ResultBean doInBackground(Void... voids) {
            String xml = CreateXML.create901();
            return WebServiceHelper.getInstance().SendWebservice("911", "queryObjectXml","QueryXmlDoc",xml);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","正在同步……");
        }

        @Override
        protected void onPostExecute(ResultBean resultBean) {
            super.onPostExecute(resultBean);
            hideProgressDialog();
            showDialog(resultBean.isSucc()+":"+resultBean.getMsg());
        }
    }
}
