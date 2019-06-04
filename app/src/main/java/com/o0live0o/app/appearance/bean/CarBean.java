package com.o0live0o.app.appearance.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CarBean implements Parcelable {

    private String plateNo;
    private String testId;
    private String vin;
    private String plateType;
    private String checkItem;
    private String operator;
    private String startTime;
    private String endTime;


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
    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
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

    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    public CarBean() {
    }

    protected CarBean(Parcel in) {
        plateNo = in.readString();
        testId = in.readString();
        vin = in.readString();
        plateType = in.readString();
        checkItem = in.readString();
        operator = in.readString();
        startTime = in.readString();
        endTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(plateNo);
        dest.writeString(testId);
        dest.writeString(vin);
        dest.writeString(plateType);
        dest.writeString(checkItem);
        dest.writeString(operator);
        dest.writeString(startTime);
        dest.writeString(endTime);
    }

    @Override
    public int describeContents() {
        return 0;
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


}



