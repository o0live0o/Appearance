package com.o0live0o.app.appearance.utils;

import com.o0live0o.app.appearance.bean.C1Bean;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.data.FinalData;

public class CreateXML {

    public static String create901(){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<querycondition>");
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
        sb.append("<jycs>"+""+"</jycs>");
        sb.append("<hpzl>"+car.getPlateType()+"</hpzl>");
        sb.append("<hphm>"+car.getPlateNo()+"</hphm>");
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<gwjysbbh>"+""+"</gwjysbbh>");
        sb.append("<jyxm>"+ FinalData.C1 +"</jyxm>");
        sb.append("<kssj>"+car.getStartTime()+"</kssj>");
        sb.append("</writecondition>");
        sb.append("</root>");
        return sb.toString();
    }

    //底盘结果信息
    public static String caeate428(CarBean car, C1Bean c1){
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<root>");
        sb.append("<writecondition>");
        sb.append("<jylsh>"+car.getTestId()+"</jylsh>");
        sb.append("<jyjgbh>"+""+"</jyjgbh>");
        sb.append("<jcxdh>"+c1.getJcxdh()+"</jcxdh>");
        sb.append("<jycs>"+c1.getJycs()+"</jycs>");
        sb.append("<jyxm>"+FinalData.C1+"</jyxm>");
        sb.append("<hpzl>"+car.getPlateType()+"</hpzl>");
        sb.append("<hphm>"+car.getPlateNo()+"</hphm>");
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<rzxxbj>"+c1.getRzxxbj()+"</rzxxbj>");
        sb.append("<rcdxbj>"+c1.getRcdxbj()+"</rcdxbj>");
        sb.append("<rxsxbj>"+c1.getRxsxbj()+"</rxsxbj>");
        sb.append("<rzdxbj>"+c1.getRzdxbj()+"</rzdxbj>");
        sb.append("<rqtbj>"+c1.getRqtbj()+"</rqtbj>");
        sb.append("<jyyjy>"+c1.getJyyjy()+"</jyyjy>");
        sb.append("<dpjcjyy>"+FinalData.getOperator()+"</dpjcjyy>");
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
        sb.append("<jycs>"+""+"</jycs>");
        sb.append("<jyxm>"+FinalData.C1+"</jyxm>");
        sb.append("<hpzl>"+car.getPlateType()+"</hpzl>");
        sb.append("<hphm>"+car.getPlateNo()+"</hphm>");
        sb.append("<clsbdh>"+car.getVin()+"</clsbdh>");
        sb.append("<gwjysbbh>"+""+"</gwjysbbh>");
        sb.append("<jssj>"+car.getEndTime()+"</jssj>");
        sb.append("</writecondition>");
        sb.append("</root>");
        return sb.toString();
    }
}
