package com.o0live0o.app.appearance.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.o0live0o.app.appearance.FinalData;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends  BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initNavBar(false, this.getString(R.string.login_title),false);
    }

    public void onLoginClick(View view) {

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//             String sql = "select TOP 1 * from RESULT_VEHICLE_INFO";
//             String strR = SSMSHelper.GetInstance().search(sql);
//             Log.d("JSONStr",strR);
//
//
//            }
//        });
//
//        thread.start();
        FinalData.setOperator("Admin");

        Intent intent = new Intent(this,NavActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSettingClick(View view) {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }
}
