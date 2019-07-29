package com.o0live0o.app.appearance.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class CarBean implements Parcelable {

    //号牌号码
    private String plateNo;
    //检测流水号
    private String testId;
    //车架号
    private String vin;

    private String lineNumber;

    private String c1Number;

    //号牌种类
    private String plateType;
    //检测项目
    private String checkItem;
    //操作员
    private String operator;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //检测类别 01-定检  00-非定检
    private String checkType;
    //车辆种类
    private String vehicleType;
    //使用性质
    /*
    A	非营运     B   	公路客运    C公交客运   D   出租客运    E   旅游客运    F  货运
    G   租赁       H    警用        I消防J救护  K   工程抢险    L   营转非      M  出租转非
    O   幼儿校车   P    小学生校车   Q其他校车   R   危化品运输   Z   其他       N  教练*/
    private String syxz;
    //注册登记日期
    private Date registerDate;
    //生产日期
    private Date MakeDate;
    //总质量
    private int zzl;
    //车辆用途
    /*
    P1	普通汽车    W1	剧毒危化品车  W2	易爆危化品车  W9	其它危化品车
    X1	幼儿校车    X2	小学生校车    X9	其它校车*/
    /*车辆用途*/
    private String clyt;
    //车身长
    private int vehicleLength;
    //是否有货箱
    @SerializedName("isHX")
    private boolean isHX;
    //是否有挡板
    @SerializedName("isDB")
    private boolean isDB;

    //检测次数
    private String testTimes;

    //工位号
    private String stationNo;

    public CarBean(){}

    protected CarBean(Parcel in) {
        plateNo = in.readString();
        testId = in.readString();
        vin = in.readString();
        lineNumber = in.readString();
        c1Number = in.readString();
        plateType = in.readString();
        checkItem = in.readString();
        operator = in.readString();
        startTime = in.readString();
        endTime = in.readString();
        checkType = in.readString();
        vehicleType = in.readString();
        syxz = in.readString();
        zzl = in.readInt();
        clyt = in.readString();
        vehicleLength = in.readInt();
        isHX = in.readByte() != 0;
        isDB = in.readByte() != 0;
        testTimes = in.readString();
        stationNo =in.readString();
    }

    public static final Creator<CarBean> CREATOR = new Creator<CarBean>() {
        @Override
        public CarBean createFromParcel(Parcel in) {
            return new CarBean(in);
        }

        @Override
        public CarBean[] newArray(int size) {
            return new CarBean[size];
        }
    };

    public String getTestTimes() {
        return testTimes;
    }

    public void setTestTimes(String testTimes) {
        this.testTimes = testTimes;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getC1Number() {
        return c1Number;
    }

    public void setC1Number(String c1Number) {
        this.c1Number = c1Number;
    }

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getSyxz() {
        return syxz;
    }

    public void setSyxz(String syxz) {
        this.syxz = syxz;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getMakeDate() {
        return MakeDate;
    }

    public void setMakeDate(Date makeDate) {
        MakeDate = makeDate;
    }

    public int getZzl() {
        return zzl;
    }

    public void setZzl(int zzl) {
        this.zzl = zzl;
    }

    public String getClyt() {
        return clyt;
    }

    public void setClyt(String clyt) {
        this.clyt = clyt;
    }

    public int getVehicleLength() {
        return vehicleLength;
    }

    public void setVehicleLength(int vehicleLength) {
        this.vehicleLength = vehicleLength;
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

    @Override
    public int describeContents() {
        return 0;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plateNo);
        dest.writeString(testId);
        dest.writeString(vin);
        dest.writeString(lineNumber);
        dest.writeString(c1Number);
        dest.writeString(plateType);
        dest.writeString(checkItem);
        dest.writeString(operator);
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(checkType);
        dest.writeString(vehicleType);
        dest.writeString(syxz);
        dest.writeInt(zzl);
        dest.writeString(clyt);
        dest.writeInt(vehicleLength);
        dest.writeByte((byte) (isHX ? 1 : 0));
        dest.writeByte((byte) (isDB ? 1 : 0));
        dest.writeString(testTimes);
        dest.writeString(stationNo);
    }
}



