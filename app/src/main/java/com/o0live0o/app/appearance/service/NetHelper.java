package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.bean.C1Bean;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static com.blankj.utilcode.util.TimeUtils.date2String;
import static com.blankj.utilcode.util.TimeUtils.getNowDate;

public abstract class NetHelper {

    //开始指令
    public static void startTest(CarBean car){
        String s2 = NetHelper.getServerTime();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<writecondition>");
        sb.append("<jylsh>"+car.getTestId()+"</jylsh>");
        sb.append("<jyjgbh>"+FinalData.getStationNo()+"</jyjgbh>");
        sb.append("<jcxdh>"+car.getLineNumber()+"</jcxdh>");
        sb.append("<jycs>"+car.getTestTimes()+"</jycs>");
        sb.append("<hpzl>"+(car.getPlateType().length()>1 ?car.getPlateType(): "0"+car.getPlateType())+"</hpzl>");
        try {
            sb.append("<hphm>"+ URLEncoder.encode(car.getPlateNo(),"utf-8")+"</hphm>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<gwjysbbh>"+car.getStationNo()+"</gwjysbbh>");
        sb.append("<jyxm>"+ FinalData.C1 +"</jyxm>");
        sb.append("<kssj>"+getServerTime()+"</kssj>");
        sb.append("</writecondition>");
        sb.append("</root>");
        L.d("发送开始指令->"+sb.toString());
        String result =  WebServiceHelper.getInstance().SendWebservice("18C55","writeObjectOut","",sb.toString()).getMsg();
        L.d("接收开始指令->"+result);
    }

    //结果
    public static void sendResult(CarBean car, C1Bean c1) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            sb.append("<root>");
            sb.append("<writecondition>");
            sb.append("<jylsh>" + car.getTestId() + "</jylsh>");
            sb.append("<jyjgbh>" + FinalData.getStationNo() + "</jyjgbh>");
            sb.append("<jcxdh>" + car.getLineNumber() + "</jcxdh>");
            sb.append("<jycs>" + car.getTestTimes() + "</jycs>");
            sb.append("<jyxm>" + FinalData.C1 + "</jyxm>");
            sb.append("<hpzl>" + (car.getPlateType().length() > 1 ? car.getPlateType() : "0" + car.getPlateType()) + "</hpzl>");
            sb.append("<hphm>" + URLEncoder.encode(car.getPlateNo(), "utf-8") + "</hphm>");
            sb.append("<clsbdh>" + car.getVin() + "</clsbdh>");
            sb.append("<rzxxbj>" + c1.getRzxxbj() + "</rzxxbj>");
            sb.append("<rcdxbj>" + c1.getRcdxbj() + "</rcdxbj>");
            sb.append("<rxsxbj>" + c1.getRxsxbj() + "</rxsxbj>");
            sb.append("<rzdxbj>" + c1.getRzdxbj() + "</rzdxbj>");
            sb.append("<rqtbj>" + c1.getRqtbj() + "</rqtbj>");
            sb.append("<jyyjy>" + c1.getJyyjy() + "</jyyjy>");

            sb.append("<dpjcjyy>" + URLEncoder.encode(FinalData.getOperator(), "utf-8") + "</dpjcjyy>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("<dpjyysfzh>" + FinalData.getOperator_ID_Car_No() + "</dpjyysfzh>");
        sb.append("</writecondition>");
        sb.append("</root>");
        L.d("发送结果->"+sb.toString());
        String result = WebServiceHelper.getInstance().SendWebservice("18C80","writeObjectOut","",sb.toString()).getMsg();
        L.d("接收结果->"+result);
    }

    //结束指令
    public static void endTest(CarBean car) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<writecondition>");
        sb.append("<jylsh>"+car.getTestId()+"</jylsh>");
        sb.append("<jyjgbh>"+FinalData.getStationNo()+"</jyjgbh>");
        sb.append("<jcxdh>"+car.getLineNumber()+"</jcxdh>");
        sb.append("<jycs>"+car.getTestTimes()+"</jycs>");
        sb.append("<jyxm>"+FinalData.C1+"</jyxm>");
        sb.append("<hpzl>"+(car.getPlateType().length()>1 ?car.getPlateType(): "0"+car.getPlateType())+"</hpzl>");
        try {
            sb.append("<hphm>"+ URLEncoder.encode(car.getPlateNo(),"utf-8")+"</hphm>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<gwjysbbh>"+car.getStationNo()+"</gwjysbbh>");
        sb.append("<jssj>"+getServerTime()+"</jssj>");
        sb.append("</writecondition>");
        sb.append("</root>");
        L.d("发送结束指令->"+sb.toString());
        String result = WebServiceHelper.getInstance().SendWebservice("18C58","writeObjectOut","",sb.toString()).getMsg();
        L.d("接收结束指令->"+result);
    }

    //拍照指令
    public static void capture(CarBean car){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<writecondition>");
        sb.append("<jylsh>"+car.getTestId()+"</jylsh>");
        sb.append("<jyjgbh>"+ FinalData.getStationNo()+"</jyjgbh>");
        sb.append("<jycs>"+car.getTestTimes()+"</jycs>");
        try {
            sb.append("<hphm>"+ URLEncoder.encode(car.getPlateNo(),"utf-8")+"</hphm>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("<hpzl>"+(car.getPlateType().length()>1 ?car.getPlateType(): "0"+car.getPlateType())+"</hpzl>");
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<cllx>"+car.getVin()+"</cllx>");
        sb.append("<jyxm>"+FinalData.C1+"</jyxm>");
        sb.append("<jcxdh>"+car.getLineNumber()+"</jcxdh>");
        sb.append("<pzcfsj>"+date2String(getNowDate())+"</pzcfsj>");
        sb.append("<zpzl>0323</zpzl>");
        sb.append("</writecondition>");
        sb.append("</root>");
        L.d("发送拍照指令->"+sb.toString());
        String result = WebServiceHelper.getInstance().SendWebservice("18CY04","writeObjectOut","",sb.toString()).getMsg();
        L.d("接收拍照指令->"+result);
    }


    public static String getServerTime(){
        String time = "";
        String sql = "SELECT CONVERT(VARCHAR(20),GETDATE(),120) AS Time";
        SSMSHelper ssmsHelper = SSMSHelper.GetInstance();
        Map<String, String> map = ssmsHelper.searchSet(sql, null);
        if (map != null && map.size() > 0 ) {
           time = map.get("Time");
        }
        return time;
    }

}
