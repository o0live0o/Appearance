package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CURD_IVS implements ICURD {

    private static SSMSHelper ssmsHelper = SSMSHelper.GetInstance();

    @Override
    public <T> DbResult login(String user, String pwd, T t) {
        DbResult dbResult = new DbResult();
        dbResult.setSucc(false);
        String sql = "SELECT COUNT(*) AS ct FROM EMPLOYEE_USER WHERE EMPLOYEE_ID = ?";
        sql = "SELECT EMPLOYEE_NAME FROM EMPLOYEE_USER WHERE EMPLOYEE_ID = ? ";
        List<Object> params = new ArrayList<>();
        params.add(user);
        Map<String, String> map = ssmsHelper.searchSet(sql, params);
        if (map != null && map.size() > 0 && map.containsKey("EMPLOYEE_NAME")) {
            dbResult.setSucc(true);
            dbResult.setMsg(map.get("EMPLOYEE_NAME"));
        } else {
            dbResult.setSucc(false);
            dbResult.setMsg("登录失败");
        }
        return dbResult;
    }

    @Override
    public <T> DbResult getCarList(CarBean car,String type, T t) {
        String sql = "SELECT TOP 20 HPHM AS plateNo,JCLSH AS testId,VIN AS vin,HPZL AS plateType,JYXM AS checkItem ";
        if (car != null && type.equals(FinalData.C1)){
            sql += ","+ car.getLineNo() +" AS testLine ";
        }
        sql+=" FROM VEHICLE_DISPATCH WHERE 1 = 1 AND (JCZT_STATUS = 0 or JCZT_STATUS = 1 or JCZT_STATUS = 2)  AND JYXM LIKE '%"+type+"%'";
        if (car != null) {
            if (car.getPlateNo().length() > 0) {
                sql += " AND HPHM LIKE '%"+car.getPlateNo()+"%'";
            }
            if (car.getVin().length() > 0){
                sql += " AND VIN LIKE '%"+car.getVin()+"%'";
            }
            if (type.equals(FinalData.C1) && car.getLineNo() != "全部"){
                sql += " AND JCXH = '"+ car.getLineNo() +"' ";
            }
        }
        sql += " ORDER BY ID DESC";
        DbResult result = ssmsHelper.search(sql);
        return result;
    }

    @Override
    public <T> DbResult saveF1(List<ExteriorBean> list,CarBean car, T t) {

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
        if (ssmsHelper.exist(searchSql,null)){
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
    public <T> DbResult saveC1(List<ExteriorBean> list,CarBean car, T t) {
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
        if (ssmsHelper.exist(searchSql,null)){
            sql = "UPDATE RESULT_CHASISS_MANUAL SET DPBJ_PD = ?,DPBJCZY = ?,DGJYBHGX = ?,KSSJ = ?,JSSJ = ? WHERE JCLSH = ?";

        }else {
            sql = "INSERT INTO RESULT_CHASISS_MANUAL (DPBJ_PD,DPBJCZY,DGJYBHGX,KSSJ,JSSJ,JCLSH) VALUES (?,?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);

        //保存完后更新调度表状态
        if (dbResult.isSucc()){
             sql = "UPDATE VEHICLE_DISPATCH SET ZDGWBH = ? , LED = ? WHERE JCLSH = ?";
             List params1 = new ArrayList();
             params1.add(1002);
             params1.add(car.getPlateNo()+"@"+ (c1_pd == "1" ?"合格":"不合格"));
            params1.add(car.getTestId());
            dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params1);
        }
        return dbResult;
    }

    @Override
    public <T> DbResult saveDC(List<ExteriorBean> list,CarBean car, T t) {
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
        if (ssmsHelper.exist(searchSql,null)){
            sql = "UPDATE RESULT_CHASISS_MANUAL SET DTDP_PD = ?,DTDPCZY = ?,KSSJ = ?,JSSJ = ? WHERE JCLSH = ?";

        }else {
            sql = "INSERT INTO RESULT_CHASISS_MANUAL (DTDP_PD,DTDPCZY,KSSJ,JSSJ,JCLSH) VALUES (?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        return dbResult;
    }

    @Override
    public <T> DbResult sendStatus(String str, CarBean car,String status, T t) {
        List<Object> params = new ArrayList<>();
        String sql = "UPDATE VEHICLE_DISPATCH SET LED = ? ";
        params.add(str);
        if (status.length() >0){
            sql += ",ZDGWBH = ? ";
            params.add(status);
        }
        sql += " WHERE JCLSH = ?";
        params.add(car.getTestId());
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        return dbResult;
    }
}
