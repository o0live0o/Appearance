package com.o0live0o.app.appearance;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.dbutils.DbResult;

import java.util.List;

public class CURDHelper  {

    private static ICURD mCurd = new CURD_IVS();

    public static DbResult login(String user,String pwd) {
      return  mCurd.login(user,pwd);
    }

    public static DbResult getCarList_F1(CarBean car,String type) {
        return mCurd.getCarList_F1(car,type);
    }

    public static DbResult saveF1(List<ExteriorBean> list, CarBean car) {
        return mCurd.saveF1(list,car);
    }

    public static DbResult saveDC(List<ExteriorBean> list,CarBean car) {
        return mCurd.saveDC(list,car);
    }

    public static DbResult saveC1(List<ExteriorBean> list,CarBean car) {
        return saveC1(list,car);
    }
}
