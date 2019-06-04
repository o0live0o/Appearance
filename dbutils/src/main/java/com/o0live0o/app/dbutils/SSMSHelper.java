package com.o0live0o.app.dbutils;

import android.util.Log;

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

    private static Connection mConn;
    private PreparedStatement preparedStatement;

    private final static SSMSHelper ssmsHelper = new SSMSHelper();

    private SSMSHelper(){ }

    public static SSMSHelper GetInstance(){
        return ssmsHelper;
    }

    private void connectDB(){
        try {
            Class.forName(JTDS_DRIVER);
            mConn = DriverManager.getConnection("jdbc:jtds:sqlserver://"+DIP+":1433/" + DName, DUser, DPwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (Exception ex){
            Log.d("SSMSHelper",ex.getMessage());
        }
    }

    public void CloseDB() {
        try {
            if (mConn != null)
                mConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void init(String dbname,String ip,String user,String pwd){
        DName = dbname;
        DIP = ip;
        DUser = user;
        DPwd = pwd;
        //connectDB();
    }

    public DbResult search(String sql) {
        DbResult result = new DbResult();
        JSONArray jsonArray = new JSONArray();
        try {
            Class.forName(JTDS_DRIVER);
            mConn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + DIP + ":1433/" + DName, DUser, DPwd);
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


    public DbResult insertAndUpdate(String sql) {
        DbResult result = new DbResult();
        int count = 0;
        try {
            Class.forName(JTDS_DRIVER);
            mConn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + DIP + ":1433/" + DName, DUser, DPwd);
            preparedStatement = mConn.prepareStatement(sql);
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
        result.setCount(count);
        result.setSucc(count > 0 ? true : false);
        return result;
    }

    public DbResult insertAndUpdateWithPara(String sql, List<Object> params){
        DbResult result = new DbResult();
        int count = 0;
        try {
            Class.forName(JTDS_DRIVER);
            mConn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + DIP + ":1433/" + DName, DUser, DPwd);

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

    public void insert(){

    }

    public DbResult exist(String sql){
        DbResult result = new DbResult();
        int count = 0;
        try {
            Class.forName(JTDS_DRIVER);
            mConn = DriverManager.getConnection("jdbc:jtds:sqlserver://" + DIP + ":1433/" + DName, DUser, DPwd);
            preparedStatement = mConn.prepareStatement(sql);
            ResultSet judge = preparedStatement.executeQuery();
            judge.next();
            count = judge.getInt("ct");
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage());
            count = -1;
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
}
