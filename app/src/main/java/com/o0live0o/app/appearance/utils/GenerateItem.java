package com.o0live0o.app.appearance.utils;

import com.o0live0o.app.appearance.bean.VehicleBean;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class GenerateItem {

    private VehicleBean vehicle;
    private List<Integer> list;

    public GenerateItem(VehicleBean vehicle) {
        this.vehicle = vehicle;
        list = new ArrayList<>();
    }

    //外廓尺寸 - 6
    private void wkcc() {
        if (vehicle.getCheckType() == 0) {
            if (vehicle.getVehicleType().contains("K") && vehicle.getUseType() == 1) {
                return;
            }
            list.add(6);
        } else {
            if (vehicle.getVehicleType().contains("G") || vehicle.getVehicleType().contains("B")
                    || (vehicle.getVehicleType().contains("H") && (vehicle.getVehicleTypeName().contains("重") ||
                    vehicle.getVehicleTypeName().contains(("中"))))) {
                list.add(6);
            }
        }
    }

    //轴距 - 7
    private void zj() {
        List<String> arr = asList("H", "B", "Z", "G");
        if (arr.contains(vehicle.getVehicleType().substring(0, 1))) {
            list.add(7);
        }
    }

    //整备质量 - 8
    private void zbzl() {
        if (vehicle.getCheckType() == 2) {
            List<String> arr = asList("H", "B", "Z", "G","M");
            if (arr.contains(vehicle.getVehicleType().substring(0, 1))) {
                list.add(8);
            }
        }
    }

    //核定载人数 - 9
    private void hdzrs(){
        List<String> arr = asList("H", "K", "Z","M");
        if (arr.contains(vehicle.getVehicleType().substring(0, 1))) {
            list.add(9);
        }
    }

    //栏板高度 - 10
    private void lbgd(){
        List<String> arr = asList("H", "B", "G");
        if(vehicle.isDB() && arr.contains(vehicle.getVehicleType().substring(0, 1))){
            list.add(10);
        }
    }

    //后轴钢板弹簧片数 - 11
    private void hzgbthps(){
        List<String> arr = asList("H", "B", "Z", "G");
        if (arr.contains(vehicle.getVehicleType().substring(0, 1))) {
            list.add(11);
        }
    }

    //客车应急出口 - 12
    private void kcyjck(){
        List<String> arr = asList("K");
        if (arr.contains(vehicle.getVehicleType().substring(0, 1)) && vehicle.getPeopleNum() > 9) {
            list.add(12);
        }
    }





}
