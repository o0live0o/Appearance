package com.o0live0o.app.appearance.bean;


import java.sql.Date;

public class VehicleBean {
    private int checkType;  //1-定检 2-注册登记检验   //CAR_TEST_TYPE
    private String vehicleType;  //CAR_TYPE2
    private int useType;   //1-非营运 2-营运
    private int peopleNum;  //PEOLLES
    private boolean isHX;
    private boolean isDB;
    private String vehicleTypeName;
    /*
    A	非营运
    B	公路客运
    C	公交客运
    D	出租客运
    E	旅游客运
    F	货运
    G	租赁
    H	警用
    I	消防
    J	救护
    K	工程抢险
    L	营转非
    M	出租转非
    O	幼儿校车
    P	小学生校车
    Q	其他校车
    R	危化品运输
    Z	其他
    N	教练*/
    private String syxz;  //SYXZ
    /*
    * 注册登记日期*/
    private Date registerDate;  //FIRST_LOGIN_DATE

    /*
    * 生产日期*/
    private Date MakeDate;   //FACTORY_DATE

    /*
    总质量*/
    private int ZZL;   //TOTAL_WEIGHT

    /*
    P1	普通汽车
    W1	剧毒危化品车
    W2	易爆危化品车
    W9	其它危化品车
    X1	幼儿校车
    X2	小学生校车
    X9	其它校车*/
    /*车辆用途*/
    private String clyt;  //CLYT

    /*
    * 车身长*/
    private int vehicleLength;

    public String getClyt() {
        return clyt;
    }

    public void setClyt(String clyt) {
        this.clyt = clyt;
    }

    public Date getMakeDate() {
        return MakeDate;
    }

    public void setMakeDate(Date makeDate) {
        MakeDate = makeDate;
    }

    public int getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(int vehicleLength) {
        this.vehicleLength = vehicleLength;
    }

    public int getZZL() {
        return ZZL;
    }

    public void setZZL(int ZZL) {
        this.ZZL = ZZL;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getSyxz() {
        return syxz;
    }

    public void setSyxz(String syxz) {
        this.syxz = syxz;
    }

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
