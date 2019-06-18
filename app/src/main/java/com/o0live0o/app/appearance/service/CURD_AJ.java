package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.data.FinalData;
import com.o0live0o.app.appearance.enums.CheckState;
import com.o0live0o.app.appearance.log.L;
import com.o0live0o.app.dbutils.DbResult;
import com.o0live0o.app.dbutils.SSMSHelper;

import java.sql.SQLClientInfoException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CURD_AJ implements ICURD {

    private static SSMSHelper ssmsHelper = SSMSHelper.GetInstance();

    @Override
    public <T> DbResult login(String user, String pwd, T t) {
        DbResult dbResult = new DbResult();
        dbResult.setSucc(true);
        dbResult.setMsg("张三");
        return dbResult;
    }

    @Override
    public <T> DbResult getCarList(CarBean car, String type, T t) {
        String sql = "SELECT TOP 20  plate_number AS plateNo,PID AS testId,'' AS vin,plate_type AS plateType,test_item AS checkItemROM cartest_vehicle WHERE 1 = 1 ";
        if(type != FinalData.C1) {
          sql +=  " AND (test_flag = 0 OR test_flag = 1) AND wg_flag = 0  ";
        }
        sql += " AND test_item LIKE '%" + type + "%'";
        if (car != null) {
            if (car.getPlateNo().length() > 0) {
                sql += " AND plate_number LIKE '%"+car.getPlateNo()+"%'";
            }
        }
        sql += " ORDER BY PID DESC";
        DbResult result = ssmsHelper.search(sql);
        return result;
    }

    @Override
    public <T> DbResult saveF1(List<ExteriorBean> list, CarBean car, T t) {
        //TODO 处理数据
        //外检检验项目
        String jyxm = "";
        //外检不合格项目
        String bhgx = "";
        //检验结论
        int iJL = 1;

        jyxm = list.stream().filter(item -> item.getItemState() == CheckState.FAIL || item.getItemState() == CheckState.PASS).map(item->String.valueOf(item.getItemId())).collect(Collectors.joining(","));
        bhgx = list.stream().filter(item -> item.getItemState() == CheckState.FAIL).map(item->String.valueOf(item.getItemId())).collect(Collectors.joining(","));
        List<?> fails = list.stream().filter(item -> item.getItemState() == CheckState.FAIL).collect(Collectors.toList());
        if (fails.size() > 0){
            iJL = 2;
        }

        List<Object> params = new ArrayList<>();
        params.add(car.getPlateNo());
        params.add(car.getPlateType());
        params.add(jyxm);
        params.add(bhgx);
        params.add(iJL);
        params.add(car.getTestId());

        //保存
        String searchSql = "SELECT COUNT(*) AS ct FROM PDA_F1_RESULT WHERE PID = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql,null)){
            sql = "UPDATE PDA_F1_RESULT SET HPHM = ?,HPZL = ?,JYXM = ?,BHGX = ?,JYJL = ? WHERE PID = ?";

        }else {
            sql = "INSERT INTO PDA_F1_RESULT (HPHM,HPZL,JYXM,BHGX,JYJL,PID) VALUES (?,?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        L.d(String.valueOf(dbResult.isSucc()));


        //更新调度表状态
        if (dbResult.isSucc()){
            sql = "UPDATE CARTEST_VEHICLE SET WG_FLAG = 1 WHERE PID = ?";
            List<Object> params1 = new ArrayList<>();
            params1.add(car.getTestId());
            dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params1);
        }

        return dbResult;
    }

    @Override
    public <T> DbResult saveDC(List<ExteriorBean> list, CarBean car, T t) {
        String dc_pd = "0";
        String dc_bhgx = "-";
        String dc_jyxm = "";

        List<ExteriorBean> failList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.FAIL)).collect(Collectors.toList());
        List<ExteriorBean> passList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.PASS)).collect(Collectors.toList());
        dc_jyxm = list.stream().filter(item -> item.getItemState() == CheckState.FAIL || item.getItemState() == CheckState.PASS).map(item->String.valueOf(item.getItemId())).collect(Collectors.joining(","));
        if (passList.size() > 0){
            dc_pd = "1";
        }

        if (failList.size() > 0){
            dc_pd = "2";
        }
        dc_bhgx = failList.stream().map(item->String.valueOf(item.getItemId())).collect(Collectors.joining(","));
        List<Object> params = new ArrayList<>();

        params.add(car.getPlateNo());
        params.add(car.getPlateType());
        params.add(dc_jyxm);
        params.add(dc_bhgx);
        params.add(dc_pd);
        params.add(car.getTestId());

        String searchSql = "SELECT COUNT(*) AS ct FROM PDA_DC_RESULT WHERE PID  = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql,null)){
            sql = "UPDATE PDA_DC_RESULT SET HPHM = ?,HPZL = ?,JYXM = ?,BHGX = ?,  JYJL = ? WHERE PID = ?";

        }else {
            sql = "INSERT INTO PDA_DC_RESULT (HPHM,HPZL,JYXM,BHGX,JYJL,PID) VALUES (?,?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);
        return dbResult;
    }

    @Override
    public <T> DbResult saveC1(List<ExteriorBean> list, CarBean car, T t) {
        String c1_pd = "0";
        String c1_bhgx = "-";
        String c1_jyxm = "";

        List<ExteriorBean> failList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.FAIL)).collect(Collectors.toList());
        List<ExteriorBean> passList = list.stream().filter((ExteriorBean bean)->bean.getItemState().equals(CheckState.PASS)).collect(Collectors.toList());
        c1_jyxm = list.stream().filter(item -> item.getItemState() == CheckState.FAIL || item.getItemState() == CheckState.PASS).map(item->String.valueOf(item.getItemId())).collect(Collectors.joining(","));
        if (passList.size() > 0){
            c1_pd = "1";
        }

        if (failList.size() > 0){
            c1_pd = "2";
        }

        c1_bhgx = failList.stream().map(item->item.getItemId()+"-1").collect(Collectors.joining(","));

        List<Object> params = new ArrayList<>();
        params.add(car.getPlateNo());
        params.add(car.getPlateType());
        params.add(c1_jyxm);
        params.add(c1_bhgx);
        params.add(c1_pd);
        params.add(car.getTestId());

        String searchSql = "SELECT COUNT(*) AS ct FROM PDA_C1_RESULT WHERE PID  = '"+car.getTestId()+"'";
        String sql = "";
        if (ssmsHelper.exist(searchSql,null)){
            sql = "UPDATE PDA_C1_RESULT SET HPHM = ?,HPZL = ?,JYXM = ?,BHGX = ?,JYJL = ? WHERE PID  = ?";

        }else {
            sql = "INSERT INTO PDA_C1_RESULT (HPHM,HPZL,JYXM,BHGX,JYJL,PID) VALUES (?,?,?,?,?,?)";
        }
        DbResult dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params);

        //保存完后更新调度表状态
        if (dbResult.isSucc()){
//            sql = "UPDATE VEHICLE_DISPATCH SET ZDGWBH = ? , LED = ? WHERE JCLSH = ?";
//            List params1 = new ArrayList();
//            params1.add(1002);
//            params1.add(car.getPlateNo()+"@"+ (c1_pd == "1" ?"合格":"不合格"));
//            params1.add(car.getTestId());
//            dbResult = ssmsHelper.insertAndUpdateWithPara(sql,params1);
        }
        return dbResult;
    }

    @Override
    public <T> DbResult sendStatus(String str, CarBean car, String status, T t) {
        DbResult dbResult = new DbResult();
        dbResult.setSucc(true);
        return dbResult;
    }
}
