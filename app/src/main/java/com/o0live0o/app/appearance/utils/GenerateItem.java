package com.o0live0o.app.appearance.utils;

import android.util.Log;

import com.o0live0o.app.appearance.bean.CarBean;
import com.o0live0o.app.appearance.bean.VehicleBean;
import com.o0live0o.app.appearance.data.CarType;
import com.o0live0o.app.appearance.log.L;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;


/**
 * The type Generate item.
 */
public  class GenerateItem {

    private CarBean m_car;
    private List<Integer> list;

    public GenerateItem() {

    }

    public List<Integer> generate(CarBean car) {
        this.m_car = car;
        list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(16);
        list.add(17);
        list.add(18);
        list.add(19);
        list.add(20);
        list.add(21);
        wkcc();
        this.zj();
        zbzl();
        hdzrs();
        lbgd();
        hzgbthps();
        kcyjck();
        kccktd();
        hx();
        qcaqd();
        sjjgp();
        mhq();
        xsjlzz();
        csfgbs();
        clwbbzb();
        chfhzz();
        yjc();
        jjx();
        xsgn();
        fbszdzz();
        fzzzzz();
        pszdq();
        jjqdzz();
        fdjczdmhzz();
        sdjxddkg();
        fzztb();
        xcbzd();
        wxhwysc();
        czrczfzzz();

        return list;
    }

    //外廓尺寸 - 6
    private void wkcc() {
        boolean flag = false;
        if (m_car.getCheckType().equals("00")) {
            //注册登记检验
            if (!CarType.XXKC().contains(m_car.getVehicleType())) {  //TODO 带驾驶室的正三轮摩托车
                flag = true;
            }
        } else {
            //定检
            if (CarType.GC_DH().contains(m_car.getVehicleType()) || CarType.ZZXHC_DH().contains(m_car.getVehicleType())) {
                flag = true;
            }
        }
        if (flag) {
            list.add(6);
        }
    }

    //轴距 - 7
    private void zj() {
        try {
            boolean flag1 = false;
            if (CarType.ZXZYC_DH().contains(m_car.getVehicleType()) || CarType.HC_DH().contains(m_car.getVehicleType())
                    || CarType.GC_DH().contains(m_car.getVehicleType())) {
                flag1 = true;
            }
            if (flag1)
                list.add(7);
        } catch (Exception e) {
            L.d(e.getMessage());
            e.printStackTrace();
        }

    }

    //整备质量 - 8
    private void zbzl() {
        boolean flag = false;
        if (m_car.getCheckType().equals("00")) {
            if (CarType.GC_DH().contains(m_car.getVehicleType()) || CarType.HC_DH().contains(m_car.getVehicleType()) ||
                    CarType.ZXZYC_DH().contains(m_car.getVehicleType())) {
                flag = true;
            }
        }
        if (flag)
            list.add(8);
    }

    //核定载人数 - 9
    private void hdzrs() {
        boolean flag = true;
        if (flag)
            list.add(9);
    }

    //核定载质量 - 10
    private void hdzzl() {

    }

    //栏板高度 - 11
    private void lbgd() {
        boolean flag = false;
        if ((CarType.HC_DH().contains(m_car.getVehicleType()) || CarType.GC_DH().contains(m_car.getVehicleType()))
                && m_car.isDB()) {
            flag = true;
        }

        if (m_car.getVehicleType().equals("H31"))
            flag = true;
        if (flag)
            list.add(11);
    }

    //后轴钢板弹簧片数 - 12
    private void hzgbthps() {
        boolean flag = false;
        if (CarType.HC_DH().contains(m_car.getVehicleType()) || CarType.GC_DH().contains(m_car.getVehicleType()) ||
                CarType.ZXZYC_DH().contains(m_car.getVehicleType())) {
            flag = true;
        }
        if (flag)
            list.add(12);
    }

    //客车应急出口 - 13
    private void kcyjck() {
        boolean flag = false;
        if (Arrays.asList(new String[]{"B", "C", "E", "O", "P", "Q"}).contains(m_car.getSyxz())) {
            flag = true;
        }
        if (flag)
            list.add(13);
    }


    //客车乘客通道和引道 - 14
    private void kccktd() {
        boolean flag = false;
        if (Arrays.asList(new String[]{"B", "C", "E", "O", "P", "Q"}).contains(m_car.getSyxz())) {
            flag = true;
        }
        if (flag)
            list.add(14);
    }

    //货箱 - 15
    private void hx() {
        boolean flag = false;
        if (m_car.isHX()) {
            flag = true;
        }
        if (flag)
            list.add(15);
    }

    //汽车安全带 - 22
    private void qcaqd() {
        if (!CarType.MOTO_DH().contains(m_car.getVehicleType())) {
            list.add(22);
        }
    }

    //机动车用三角警告牌 - 23
    private void sjjgp() {
        if (!CarType.MOTO_DH().contains(m_car.getVehicleType())) {
            list.add(23);
        }
    }

    //灭火器 - 24
    private void mhq() {
        try {
            if (CarType.DXKC_DH().contains(m_car.getVehicleType()) || m_car.getSyxz().contains("R")) {
                list.add(24);
            }
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //行驶记录装置 - 25
    private void xsjlzz() {
        try {
            boolean flag = false;
            if (m_car.getSyxz().equals("B") || m_car.getSyxz().equals("E") || m_car.getSyxz().equals("R") ||
                    m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q")) {
                flag = true;
            } else if (m_car.getRegisterDate().after(new Date(2013, 1, 1))) {
                if (CarType.QYC_DH().contains(m_car.getVehicleType())) {
                    flag = true;
                } else if (CarType.HC_DH().contains(m_car.getVehicleType()) && m_car.getZzl() >= 12000) {
                    flag = true;
                }
            }
            if (flag)
                list.add(25);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //车身反光标识 - 26
    private void csfgbs() {
        try {
            if (CarType.HC_DH().contains(m_car.getVehicleType()) || CarType.GC_DH().contains(m_car.getVehicleType())) {
                list.add(26);
            }
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //车辆尾部标志板 - 27
    private void clwbbzb() {
        try {
            boolean flag = false;
            if (m_car.getMakeDate().after(new Date(2012, 9, 1))) {
                if (CarType.HC_DH().contains(m_car.getVehicleType()) && m_car.getZzl() >= 12000) {
                    flag = true;
                } else if (CarType.GC_DH().contains(m_car.getVehicleType()) && m_car.getVehicleLength() > 8) {
                    flag = true;
                }
            }
            if (flag)
                list.add(27);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //侧后防护装置 - 28
    private void chfhzz() {
        try {
            if (CarType.HC_DH().contains(m_car.getVehicleType()) || CarType.QYC_DH().contains(m_car.getVehicleType())
                    || CarType.GC_DH().contains(m_car.getVehicleType())) {
                list.add(28);
            }
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //应急锤 - 29
    private void yjc() {

    }

    //急救箱 - 30
    private void jjx() {
        try {
            if (m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q")) {
                list.add(30);
            }
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //限速功能或限速装置 - 31
    private void xsgn() {
        try {
            boolean flag = false;
            if (m_car.getCheckType().equals("00")) {
                if (m_car.getSyxz().equals("B") || m_car.getSyxz().equals("C") || m_car.getSyxz().equals("E")
                        || m_car.getSyxz().equals("R")) {
                    flag = true;
                } else if (CarType.DXKC_DH().contains(m_car.getVehicleType()) && m_car.getVehicleLength() > 6) {
                    flag = true;
                }
            }
            if (flag)
                list.add(31);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //防抱死制动装置 - 32
    private void fbszdzz() {
        try {
            boolean flag = false;
            if (m_car.getClyt().equals("W2") || m_car.getClyt().equals("W1")) {
                flag = true;
            } else if (m_car.getClyt().equals("W9") && m_car.getMakeDate().after(new Date(2012, 9, 1))) {
                flag = true;
            }

            if (!flag && (CarType.ZXZYC_DH().contains(m_car.getVehicleType()) || CarType.HC_DH().contains(m_car.getVehicleType()))
                    && m_car.getZzl() >= 12000 && m_car.getMakeDate().after(new Date(2014, 9, 1))) {
                flag = true;
            }

            if (!flag && m_car.getSyxz().equals("C") && m_car.getMakeDate().after(new Date(2013, 9, 1)) && m_car.getVehicleLength() > 9) {
                flag = true;
            }

            if (!flag && (m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q")) &&
                    m_car.getMakeDate().after(new Date(2013, 5, 1))) {
                flag = true;
            }

            if (!flag && m_car.getMakeDate().after(new Date(2012, 9, 1)) && CarType.QYC_DH().contains(m_car.getVehicleType())) {
                flag = true;
            }

            if (!flag && m_car.getMakeDate().after(new Date(2012, 9, 1)) &&
                    (m_car.getSyxz().equals("B") || m_car.getSyxz() == "E") && m_car.getVehicleLength() > 9) {
                flag = true;
            }

            if (!flag && m_car.getRegisterDate().after(new Date(2005, 2, 1))) {
                if ((m_car.getSyxz() == "B" || m_car.getSyxz() == "E") && m_car.getZzl() > 12000) {
                    flag = true;
                }
                if (CarType.GC_DH().contains(m_car.getVehicleType()) && m_car.getZzl() > 10000) {
                    flag = true;

                }
                if (CarType.QYC_DH().contains(m_car.getVehicleType()) && m_car.getZzl() > 16000) {
                    flag = true;
                }
            }

            if (flag)
                list.add(32);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //辅助制动装置 - 33
    private void fzzzzz() {
        try {
            boolean flag = false;
            if (CarType.ZXZYC_DH().contains(m_car.getVehicleType()) && m_car.getZzl() >= 12000
                    && m_car.getMakeDate().after(new Date(2014, 9, 1))) {
                flag = true;
            }

            if (m_car.getMakeDate().after(new Date(2012, 9, 1))) {
                if (CarType.DXKC_DH().contains(m_car.getVehicleType()) && m_car.getVehicleLength() > 9) {
                    flag = true;
                }
                if ((m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q")) && m_car.getVehicleLength() > 8) {
                    flag = true;
                }
            }
            if (CarType.HC_DH().contains(m_car.getVehicleType()) && m_car.getZzl() >= 12000) {
                flag = true;
            }
            if (m_car.getSyxz().equals("R")) {
                flag = true;
            }
            if (flag)
                list.add(33);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //盘式制动器 - 34
    private void pszdq() {
        try {
            boolean flag = false;
            if (m_car.getMakeDate().after(new Date(2012, 9, 1)) && m_car.getSyxz().equals("C")) {
                flag = true;
            }
            if ((m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q"))
                    && m_car.getMakeDate().after(new Date(2013, 5, 1))) {
                flag = true;
            }
            if (m_car.getSyxz().equals("R") && m_car.getMakeDate().after(new Date(2012, 9, 1))) {
                flag = true;
            }

            if (flag)
                list.add(34);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //紧急切断装置 - 35
    private void jjqdzz() {
        try {
            boolean flag = false;
            if (m_car.getSyxz().equals("R") && (CarType.GSGC_DH().contains(m_car.getVehicleType()) || CarType.GsHC_DH().contains(m_car.getVehicleType()))) {
                flag = true;
            }
            if (flag)
                list.add(35);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //发动机舱自动灭火装置 - 36
    private void fdjczdmhzz() {
        try {
            boolean flag = false;
            if (m_car.getMakeDate().after(new Date(2013, 3, 1)) &&
                    (m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q"))) {
                flag = true;
            }
            if (flag)
                list.add(36);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //手动机械断电开关 - 37
    private void sdjxddkg() {
        try {
            boolean flag = false;
            if (CarType.DXKC_DH().contains(m_car.getVehicleType()) && m_car.getMakeDate().after(new Date(2013, 3, 1)) &&
                    m_car.getVehicleLength() >= 6) {
                flag = true;
            }
            if (flag)
                list.add(37);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //副制动踏板 - 38
    private void fzztb() {
        try {
            boolean flag = false;
            if (m_car.getSyxz().equals("N")) {
                flag = true;
            }
            if (flag)
                list.add(38);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //校车标志灯和校车停车指示标志牌 - 39
    private void xcbzd() {
        try {
            boolean flag = false;
            if ((m_car.getSyxz().equals("O") || m_car.getSyxz().equals("P") || m_car.getSyxz().equals("Q"))) {
                flag = true;
            }
            if (flag)
                list.add(39);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //危险货物运输车标志 - 40
    private void wxhwysc() {
        try {
            boolean flag = false;
            if (m_car.getSyxz().equals("R")) {
                flag = true;
            }
            if (flag)
                list.add(40);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }

    //肢体残疾人操纵辅助装置 - 41
    private void czrczfzzz() {
        try {
            boolean flag = false;
            if (flag)
                list.add(41);
        } catch (Exception e) {
            L.d(e.getMessage());
        }
    }
}
