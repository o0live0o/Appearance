package com.o0live0o.app.appearance.utils;

import com.o0live0o.app.appearance.bean.C1Bean;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.data.FinalData;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;

public class CreateXML {

    public static String create901(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<querycondition>");
        sb.append("<babh>"+FinalData.getStationNo()+"</babh>");
        sb.append("</querycondition>");
        sb.append("</root>");
        return sb.toString();
    }

    //项目开始
    public static String create211(CarBean car){
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
        sb.append("<kssj>"+car.getStartTime()+"</kssj>");
        sb.append("</writecondition>");
        sb.append("</root>");
        return sb.toString();
    }

    //底盘结果信息
    public static String caeate428(CarBean car, C1Bean c1){
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<writecondition>");
        sb.append("<jylsh>"+car.getTestId()+"</jylsh>");
        sb.append("<jyjgbh>"+FinalData.getStationNo()+"</jyjgbh>");
        sb.append("<jcxdh>"+car.getLineNumber()+"</jcxdh>");
        sb.append("<jycs>"+car.getTestTimes()+"</jycs>");
        sb.append("<jyxm>"+FinalData.C1+"</jyxm>");
        sb.append("<hpzl>"+(car.getPlateType().length()>1 ?car.getPlateType(): "0"+car.getPlateType())+"</hpzl>");
        sb.append("<hphm>"+ URLEncoder.encode(car.getPlateNo(),"utf-8")+"</hphm>");
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<rzxxbj>"+c1.getRzxxbj()+"</rzxxbj>");
        sb.append("<rcdxbj>"+c1.getRcdxbj()+"</rcdxbj>");
        sb.append("<rxsxbj>"+c1.getRxsxbj()+"</rxsxbj>");
        sb.append("<rzdxbj>"+c1.getRzdxbj()+"</rzdxbj>");
        sb.append("<rqtbj>"+c1.getRqtbj()+"</rqtbj>");
        sb.append("<jyyjy>"+c1.getJyyjy()+"</jyyjy>");
            sb.append("<dpjcjyy>"+ URLEncoder.encode(FinalData.getOperator(),"utf-8")+"</dpjcjyy>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("<dpjyysfzh>"+FinalData.getOperator_ID_Car_No()+"</dpjyysfzh>");
        sb.append("</writecondition>");
        sb.append("</root>");
        return sb.toString();
    }

    //项目结束
    public static String create212(CarBean car){
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
        sb.append("<jssj>"+car.getEndTime()+"</jssj>");
        sb.append("</writecondition>");
        sb.append("</root>");
        return sb.toString();
    }

    /*
     * 创建调用拍照和录像接口的xml
     * pssj : 拍摄时间
     * type : 操作类别  00:拍照 01:录像开始 02:录像结束
     * zpzl ：种类
     */
    public static String create803(CarBean car,String pssj,String type,String zpzl){
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
        sb.append("<pssj>"+pssj+"</pssj>");
        sb.append("<type>"+type+"</type>");
        sb.append("<zpzl>"+zpzl+"</zpzl>");
        sb.append("</writecondition>");
        sb.append("</root>");
        return sb.toString();
    }
}
