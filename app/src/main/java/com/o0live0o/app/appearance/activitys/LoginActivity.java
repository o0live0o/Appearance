package com.o0live0o.app.appearance.activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.o0live0o.app.appearance.bean.Vehicle_18C55;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.service.CURDHelper;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.R;
import com.o0live0o.app.appearance.service.WebServiceHelper;
import com.o0live0o.app.appearance.utils.DESTool;
import com.o0live0o.app.appearance.views.InputView;
import com.o0live0o.app.dbutils.DbResult;


import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;




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
        DESTool desTool = new DESTool();
        String s = null;
        try {
            s = desTool.encode("123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        L.d(s);
//        Vehicle_18C55 vehicle_18C55 = new Vehicle_18C55();
//          XStream xStream = new XStream(new DomDriver());
//        xStream.ignoreUnknownElements();
//        xStream.processAnnotations(vehicle_18C55.getClass());
//
//        String xml = xStream.toXML(vehicle_18C55);
//        showDialog(xml);
////
//        try {
//
//            JAXBContext jaxbContext = JAXBContext.newInstance(Vehicle_18C55.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
//            StringWriter sw = new StringWriter();
//            marshaller.marshal(vehicle_18C55,sw);
//            showDialog(sw.toString());
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
        String user =ivUser.getInputStr();
        String pwd = ivPwd.getInputStr();
        if (user.isEmpty())
        {
            showDialog("用户名不能为空！");
            return;
        }
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

    public void onSetWebServiceClick(View view) {
        Intent intent = new Intent(this,WebserviceActivity.class);
        startActivity(intent);
    }


    class LoginTask extends AsyncTask<String,Void, DbResult>{

        @Override
        protected DbResult doInBackground(String... strings) {
            String user = strings[0];
            String pwd = strings[1];
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
                FinalData.setOperator(dbResult.getMsg());
                Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                startActivity(intent);
                finish();
            } else {
                showToast("登陆失败！"+dbResult.getMsg());
            }
        }
    }
}
