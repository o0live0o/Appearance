package com.o0live0o.app.dbutils;

import android.content.Context;
import android.net.MacAddress;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SSMSHelper {

    private final static String JTDS_DRIVER = "net.sourceforge.jtds.jdbc.Driver";

    private static String DName;
    private static String DIP;
    private static String DUser;
    private static String DPwd;
    private static String DInstance;

    private static Connection mConn;
    private PreparedStatement preparedStatement;

    private Context mContext;

    private final static SSMSHelper ssmsHelper = new SSMSHelper();

    private SSMSHelper() {
    }

    public static SSMSHelper GetInstance() {
        return ssmsHelper;
    }


    public void init(String dbname, String ip, String user, String pwd,String instance, Context context) {
        DName = dbname;
        DIP = ip;
        DUser = user;
        DPwd = pwd;
        DInstance = instance;
        mContext = context;
        //connectDB();
    }

    /*
     *根据查询条件返回数据集的json字符串
     */
    public DbResult search(String sql) {
        DbResult result = new DbResult();
        JSONArray jsonArray = new JSONArray();
        try {
            Class.forName(JTDS_DRIVER);
            String connectURL = "jdbc:jtds:sqlserver://"+DIP+":1433;databaseName="+DName;
            if (DInstance != null && !DInstance.isEmpty()){
                connectURL += ";instance="+DInstance;
            }
            mConn = DriverManager.getConnection(connectURL, DUser, DPwd);
            preparedStatement = mConn.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                    String colName = resultSetMetaData.getColumnLabel(i + 1);
                    String val = resultSet.getString(colName);
                    jsonObject.put(colName, val);
                }
                jsonArray.put(jsonObject);
            }
            result.setCode(1);
            result.setMsg(jsonArray.toString());
            result.setSucc(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(0);
            result.setMsg(e.getMessage());
            result.setSucc(false);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (mConn != null)
                    mConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public DbResult insertAndUpdateWithPara(String sql, List<Object> params) {
        DbResult result = new DbResult();
        int count = 0;
        try {
            Class.forName(JTDS_DRIVER);
            String connectURL = "jdbc:jtds:sqlserver://"+DIP+":1433;databaseName="+DName;
            if (DInstance != null && !DInstance.isEmpty()){
                connectURL += ";instance="+DInstance;
            }
            mConn = DriverManager.getConnection(connectURL, DUser, DPwd);

            preparedStatement = mConn.prepareStatement(sql);
            if (params != null && !params.equals("")) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }

            count = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            count = -1;
            result.setMsg(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                mConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        result.setSucc(count > 0 ? true : false);
        result.setCount(count);
        return result;
    }

    public boolean exist(String sql, List<Object> params) {
        int count = 0;
        try {
            Class.forName(JTDS_DRIVER);
            String connectURL = "jdbc:jtds:sqlserver://"+DIP+":1433;databaseName="+DName;
            if (DInstance != null && !DInstance.isEmpty()){
                connectURL += ";instance="+DInstance;
            }
            mConn = DriverManager.getConnection(connectURL, DUser, DPwd);

            preparedStatement = mConn.prepareStatement(sql);
            if (params != null && !params.equals("")) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            ResultSet judge = preparedStatement.executeQuery();
            judge.next();
            count = judge.getInt("ct");
        } catch (Exception e) {
            e.printStackTrace();
            count = -1;
            showToast(e.getMessage());
        } finally {
            try {
                preparedStatement.close();
                mConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                showToast(e.getMessage());
            }
        }
        return count > 0 ? true : false;
    }

    public Map<String, String> searchSet(String sql, List<Object> params) {
        ResultSet resultSet = null;
        Map<String, String> map = new HashMap<>();
        try {
            Class.forName(JTDS_DRIVER);
            String connectURL = "jdbc:jtds:sqlserver://"+DIP+":1433;databaseName="+DName;
            if (DInstance != null && !DInstance.isEmpty()){
                connectURL += ";instance="+DInstance;
            }
            mConn = DriverManager.getConnection(connectURL, DUser, DPwd);
            preparedStatement = mConn.prepareStatement(sql);
            if (params != null && !params.equals("")) {
                for (int i = 0; i < params.size(); i++) {
                    preparedStatement.setObject(i + 1, params.get(i));
                }
            }
            resultSet = preparedStatement.executeQuery();
            return rowToMap(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error",e.getMessage());
            //showToast(e.getMessage());
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();
                if (mConn != null)
                    mConn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    private HashMap rowToMap(ResultSet rs) {
        try {
            if (rs.next()) {
                ResultSetMetaData rsm = rs.getMetaData();
                int size = rsm.getColumnCount();                           //每行列数
                HashMap row = new HashMap();
                for (int j = 1; j <= size; j++) {
                    row.put(rsm.getColumnLabel(j), rs.getObject(j));
                }
                return row;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void connectDB() {
        try {
            Class.forName(JTDS_DRIVER);
            String connectURL = "jdbc:jtds:sqlserver://"+DIP+":1433;databaseName="+DName;
            if (DInstance != null && !DInstance.isEmpty()){
                connectURL += ";instance="+DInstance;
            }
            mConn = DriverManager.getConnection(connectURL, DUser, DPwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Log.d("SSMSHelper", ex.getMessage());
        }
    }

    private void showToast(final String s){
        new Thread(){
            @Override
            public void run(){
                Looper.prepare();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext,s,Toast.LENGTH_LONG).show();
                    }
                });
                Looper.loop();
            }
        }.start();
    }


}

