package com.o0live0o.app.appearance;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CURD_IVS implements ICURD {

    private static SSMSHelper ssmsHelper = SSMSHelper.GetInstance();

    @Override
    public DbResult login( String user,String pwd ) {
        String sql = "SELECT COUNT(*) AS ct FROM EMPLOYEE_USER WHERE EMPLOYEE_ID = ?";
        List<Object> params = new ArrayList<>();
        params.add(user);
        return  ssmsHelper.exist(sql,params);
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

        String searchSql = "SELECT COUNT(*) AS ct FROM RESULT_CHASISS_MANUAL WHERE JCLSH = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql).isSucc()){
             sql = "UPDATE RESULT_CHASISS_MANUAL SET RGJYBJCX = ?,RGJYBHGX = ?,WGJCCZY=? WHERE JCLSH = ?";

        }else {
             sql = "INSERT INTO RESULT_CHASISS_MANUAL (RGJYBJCX,RGJYBHGX,WGJCCZY,JCLSH) VALUES (?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        L.d(String.valueOf(dbResult.isSucc()));

        //TODO 更新调度表状态

        return dbResult;
    }

    @Override
    public DbResult saveDC(List<ExteriorBean> list,CarBean car) {
        String c1_pd = "0";
        String c1_bhgx = "-";
        String c1_jyxm = "";

        List<ExteriorBean> failList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.FAIL)).collect(Collectors.toList());
        List<ExteriorBean> passList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.PASS)).collect(Collectors.toList());

        if (passList.size() > 0){
            c1_pd = "1";
        }

        if (failList.size() > 0){
            c1_pd = "2";
        }

        c1_bhgx = failList.stream().map(item->item.getItemId()+"-1").collect(Collectors.joining(","));

        List<Object> params = new ArrayList<>();
        params.add(c1_pd);
        params.add(FinalData.getOperator());
        params.add(c1_bhgx);
        params.add(car.getStartTime());
        params.add(car.getEndTime());
        params.add(car.getTestId());

        String searchSql = "SELECT COUNT(*) AS ct FROM RESULT_CHASISS_MANUAL WHERE JCLSH = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql).isSucc()){
            sql = "UPDATE RESULT_CHASISS_MANUAL SET DPBJ_PD = ?,DPBJCZY = ?,DGJYBHGX = ?,KSSJ = ?,JSSJ = ? WHERE JCLSH = ?";

        }else {
            sql = "INSERT INTO RESULT_CHASISS_MANUAL (DPBJ_PD,DPBJCZY,DGJYBHGX,KSSJ,JSSJ,JCLSH) VALUES (?,?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        return dbResult;
    }

    @Override
    public DbResult saveC1(List<ExteriorBean> list,CarBean car) {
        String dc_pd = "0";
        String dc_bhgx = "-";
        String dc_jyxm = "";

        List<ExteriorBean> failList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.FAIL)).collect(Collectors.toList());
        List<ExteriorBean> passList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.PASS)).collect(Collectors.toList());

        if (passList.size() > 0){
            dc_pd = "1";
        }

        if (failList.size() > 0){
            dc_pd = "2";
        }

        dc_bhgx = failList.stream().map(item->item.getItemId()+"-1").collect(Collectors.joining(","));

        List<Object> params = new ArrayList<>();
        params.add(dc_pd);
        params.add(FinalData.getOperator());
        params.add(car.getStartTime());
        params.add(car.getEndTime());
        params.add(car.getTestId());

        String searchSql = "SELECT COUNT(*) AS ct FROM RESULT_CHASISS_MANUAL WHERE JCLSH = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql).isSucc()){
            sql = "UPDATE RESULT_CHASISS_MANUAL SET DTDP_PD = ?,DTDPCZY = ?,KSSJ = ?,JSSJ = ? WHERE JCLSH = ?";

        }else {
            sql = "INSERT INTO RESULT_CHASISS_MANUAL (DTDP_PD,DTDPCZY,KSSJ,JSSJ,JCLSH) VALUES (?,?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        return dbResult;
    }
}
