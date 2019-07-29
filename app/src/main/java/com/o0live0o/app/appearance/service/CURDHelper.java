package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.dbutils.DbResult;

import java.util.List;

public class CURDHelper  {

    private static ICURD mCurd = new CURD_AJ();

    public static DbResult login(String user,String pwd) {
      return  mCurd.<String>login(user,pwd,"");
    }

    public static DbResult getCarList(CarBean car,String type) {
        return mCurd.getCarList(car,type,"");
    }

    public static DbResult saveF1(List<ExteriorBean> list, CarBean car) {
        return mCurd.saveF1(list,car,"");
    }

    public static DbResult saveDC(List<ExteriorBean> list,CarBean car) {
        return mCurd.saveDC(list,car,"");
    }

    public static DbResult saveC1(List<ExteriorBean> list,CarBean car) {
        return mCurd.saveC1(list,car,"");
    }

    public static DbResult sendStatus(String s,CarBean car,String status){
        return mCurd.sendStatus(s,car,status,"");
    }


    public static DbResult onLine(CarBean car,String line){
        return mCurd.onLine(car,line);
    }
}
