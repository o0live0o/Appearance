package com.o0live0o.app.appearance.activitys;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.o0live0o.app.appearance.L;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.views.InputView;
import com.o0live0o.app.dbutils.SSMSHelper;

public class SettingActivity extends BaseActivity {

    private InputView mIvDatabase;
    private InputView mIvServer;
    private InputView mIvUser;
    private InputView mIvPwd;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    private void init(){
        initNavBar(true,"系统设置",false);
        mIvDatabase = findViewById(R.id.set_database);
        mIvPwd = findViewById(R.id.set_dbpwd);
        mIvUser = findViewById(R.id.set_dbuser);
        mIvServer = findViewById(R.id.set_server);

        pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());

        String dataBase = pref.getString("database", "SXDL");
        String user = pref.getString("db_user", "sa");
        String pwd = pref.getString("db_pwd", "123456");
        String server = pref.getString("db_server", "192.168.2.233");

        mIvDatabase.setInputStr(dataBase);
        mIvUser.setInputStr(user);
        mIvPwd.setInputStr(pwd);
        mIvServer.setInputStr(server);
    }

    public void onSaveSettingClick(View view) {
        try {
            String dataBase = mIvDatabase.getInputStr();
            String user = mIvUser.getInputStr();
            String pwd = mIvPwd.getInputStr();
            String server = mIvServer.getInputStr();

            SSMSHelper.GetInstance().init(
                    dataBase,
                    server,
                    user,
                    pwd);

            editor = pref.edit();
            editor.putString("database",dataBase);
            editor.putString("db_user",user);
            editor.putString("db_pwd",pwd);
            editor.putString("db_server",server);
            editor.apply();

            Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
            finish();
        }catch (Exception ex){
            L.d(ex.getMessage());
        }


    }
}
