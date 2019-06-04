package com.o0live0o.app.appearance.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.o0live0o.app.appearance.CURDHelper;
import com.o0live0o.app.appearance.FinalData;
import com.o0live0o.app.appearance.MyApplication;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.views.InputView;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends  BaseActivity{

    private InputView ivUser;
    private InputView ivPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initNavBar(false, this.getString(R.string.login_title),false);
        init();
    }

    public void onLoginClick(View view) {
        String user =ivUser.getInputStr();
        String pwd = ivPwd.getInputStr();
        new LoginTask().execute(new String[]{user,pwd});
    }

    public void onSettingClick(View view) {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }

    private void init(){
        ivUser = findViewById(R.id.login_name);
        ivPwd = findViewById(R.id.login__pwd);
    }

    class LoginTask extends AsyncTask<String,Void, DbResult>{

        @Override
        protected DbResult doInBackground(String... strings) {
            String user = strings[0];
            String pwd = strings[1];
            FinalData.setOperator(user);
            return CURDHelper.login(user,pwd);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("","登陆中……");
        }

        @Override
        protected void onPostExecute(DbResult dbResult) {
            super.onPostExecute(dbResult);
            hideProgressDialog();
            if (dbResult.isSucc()) {
                showToast("登陆成功！");
                Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                startActivity(intent);
                finish();
            } else {
                showToast("登陆失败！"+dbResult.getMsg());
            }
        }
    }
}
