package com.o0live0o.app.appearance;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.util.ArrayList;
import java.util.List;

public class CURD_IVS implements ICURD {

    private static SSMSHelper ssmsHelper = SSMSHelper.GetInstance();

    @Override
    public DbResult login( String user,String pwd ) {
        return  null;
    }

    @Override
    public DbResult getCarList_F1(CarBean car,String type) {
        String sql = "SELECT TOP 20 HPHM AS plateNo,JCLSH AS testId," +
                "VIN AS vin,HPZL AS plateType,JYXM AS checkItem FROM VEHICLE_DISPATCH WHERE 1 = 1 AND JYXM LIKE '%"+type+"%'";
        if (car != null) {
            if (car.getPlateNo().length() > 0) {
                sql += " AND HPHM LIKE '%"+car.getPlateNo()+"%'";
            }
            if (car.getVin().length() > 0){
                sql += " AND VIN LIKE '%"+car.getVin()+"%'";
            }
        }
        sql += " ORDER BY ID DESC";
        DbResult result = ssmsHelper.search(sql);
        return result;
    }

    @Override
    public DbResult saveF1(List<ExteriorBean> list,CarBean car) {

        //TODO 处理数据
        //外检检验项目
        String jyxm = "";
        //外检不合格项目
        String bhgx = "";
        for (ExteriorBean bean:list
             ) {
            if (bean.getItemState() != CheckState.NOJUDGE) {
                if (jyxm == "") {
                    jyxm = bean.getItemId()+"-1";
                } else {
                    jyxm += "," + bean.getItemId()+"-1";
                }
            }

            if (bean.getItemState() == CheckState.FAIL){
                if (bhgx == "") {
                    bhgx = bean.getItemId()+"-1";
                } else {
                    bhgx += "," + bean.getItemId()+"-1";
                }
            }
        }

        List<Object> params = new ArrayList<>();
        params.add(jyxm);
        params.add(bhgx);
        params.add(FinalData.getOperator());
        params.add(car.getTestId());

        //TODO 判断数据是否存在
        String searchSql = "SELECT COUNT(*) AS ct FROM RESULT_CHASISS_MANUAL WHERE JCLSH = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql).isSucc()){
            //TODO 更新数据
             sql = "UPDATE RESULT_CHASISS_MANUAL SET RGJYBJCX = ?,RGJYBHGX = ?,WGJCCZY=? WHERE JCLSH = ?";

        }else {
            //TODO 插入数据
             sql = "INSERT INTO RESULT_CHASISS_MANUAL (RGJYBJCX,RGJYBHGX,WGJCCZY,JCLSH) VALUES (?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        L.d(String.valueOf(dbResult.isSucc()));

        //TODO 更新调度表状态

        return dbResult;
    }

    @Override
    public DbResult saveDC(List<ExteriorBean> list,CarBean car) {

        for (ExteriorBean bean:list
             ) {

        }
        return null;
    }

    @Override
    public DbResult saveC1(List<ExteriorBean> list,CarBean car) {
        return null;
    }
}
