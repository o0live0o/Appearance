package com.o0live0o.app.appearance;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.dbutils.DbResult;

import java.util.List;

public interface ICURD {

     DbResult login(String user,String pwd);

     DbResult getCarList_F1(CarBean car,String type);

     DbResult saveF1(List<ExteriorBean> list,CarBean car);

     DbResult saveDC(List<ExteriorBean> list,CarBean car);

     DbResult saveC1(List<ExteriorBean> list,CarBean car);
}
