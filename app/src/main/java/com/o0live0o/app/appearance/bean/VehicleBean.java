package com.o0live0o.app.appearance.bean;

public class VehicleBean {
    private int checkType;  //1-定检 2-注册登记检验
    private String vehicleType;
    private int useType;   //1-非营运 2-营运
    private int peopleNum;
    private boolean isHX;
    private boolean isDB;
    private String vehicleTypeName;

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public boolean isHX() {
        return isHX;
    }

    public void setHX(boolean HX) {
        isHX = HX;
    }

    public boolean isDB() {
        return isDB;
    }

    public void setDB(boolean DB) {
        isDB = DB;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }
}
