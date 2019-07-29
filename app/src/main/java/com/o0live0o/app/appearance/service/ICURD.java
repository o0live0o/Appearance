package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.dbutils.DbResult;

import java.util.List;

public interface ICURD {

     <T>DbResult login(String user,String pwd,T t);

     <T>DbResult getCarList(CarBean car,String type,T t);

     <T>DbResult saveF1(List<ExteriorBean> list,CarBean car,T t);

     <T>DbResult saveDC(List<ExteriorBean> list,CarBean car,T t);

     <T>DbResult saveC1(List<ExteriorBean> list,CarBean car,T t);

     <T>DbResult sendStatus(String str,CarBean car,String status,T t);

     <T>DbResult onLine(CarBean car,T t);

     <T>DbResult insertOrUpdate(CarBean car,T t);
}
