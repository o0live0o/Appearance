package com.o0live0o.app.appearance.data;

import com.o0live0o.app.appearance.bean.CodeName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public  class CarType {

    //客车
    public static List<CodeName> KC() {
        List list = new ArrayList();
        list.add(new CodeName("K11", "大型普通客车"));
        list.add(new CodeName("K12", "大型双层客车"));
        list.add(new CodeName("K13", "大型卧铺客车"));
        list.add(new CodeName("K14", "大型铰接客车"));
        list.add(new CodeName("K15", "大型越野客车"));
        list.add(new CodeName("K16", "大型轿车"));
        list.add(new CodeName("K17", "大型专用客车"));
        list.add(new CodeName("K18", "大型专用校车"));
        list.add(new CodeName("K21", "中型普通客车"));
        list.add(new CodeName("K22", "中型双层客车"));
        list.add(new CodeName("K23", "中型卧铺客车"));
        list.add(new CodeName("K24", "中型铰接客车"));
        list.add(new CodeName("K25", "中型越野客车"));
        list.add(new CodeName("K26", "中型轿车"));
        list.add(new CodeName("K27", "中型专用客车"));
        list.add(new CodeName("K28", "中型专用校车"));
        list.add(new CodeName("K31", "小型普通客车"));
        list.add(new CodeName("K32", "小型越野客车"));
        list.add(new CodeName("K33", "小型轿车"));
        list.add(new CodeName("K34", "小型专用客车"));
        list.add(new CodeName("K38", "小型专用校车"));
        list.add(new CodeName("K39", "小型面包车"));
        return list;
    }

    //客车代号
    public static List<String> KC_DH() {
        return KC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    //中、大型客车
    public static List<CodeName> DXKC() {
        List list = new ArrayList();
        list.add(new CodeName("K11", "大型普通客车"));
        list.add(new CodeName("K12", "大型双层客车"));
        list.add(new CodeName("K13", "大型卧铺客车"));
        list.add(new CodeName("K14", "大型铰接客车"));
        list.add(new CodeName("K15", "大型越野客车"));
        list.add(new CodeName("K16", "大型轿车"));
        list.add(new CodeName("K17", "大型专用客车"));
        list.add(new CodeName("K18", "大型专用校车"));
        list.add(new CodeName("K21", "中型普通客车"));
        list.add(new CodeName("K22", "中型双层客车"));
        list.add(new CodeName("K23", "中型卧铺客车"));
        list.add(new CodeName("K24", "中型铰接客车"));
        list.add(new CodeName("K25", "中型越野客车"));
        list.add(new CodeName("K26", "中型轿车"));
        list.add(new CodeName("K27", "中型专用客车"));
        list.add(new CodeName("K28", "中型专用校车"));
        return list;
    }

    //中、大型客车代号
    public static List<String> DXKC_DH() {
        return DXKC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    //小、微型轿车
    public static List<CodeName> XXKC() {
        List list = new ArrayList<CodeName>();
        list.add(new CodeName("K31", "小型普通客车"));
        list.add(new CodeName("K32", "小型越野客车"));
        list.add(new CodeName("K33", "小型轿车"));
        list.add(new CodeName("K34", "小型专用客车"));
        list.add(new CodeName("K38", "小型专用校车"));
        list.add(new CodeName("K39", "小型面包车"));
        return list;
    }

    //小、微型轿车代号
    public static List<String> XXKC_DH() {
        return XXKC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    //牵引车
    public static List<CodeName> QYC() {
        List list = new ArrayList<CodeName>();
        list.add(new CodeName("Q11", "重型半挂牵引车"));
        list.add(new CodeName("Q12", "重型全挂牵引车"));
        list.add(new CodeName("Q21", "中型半挂牵引车"));
        list.add(new CodeName("Q22", "中型全挂牵引车"));
        list.add(new CodeName("Q31", "轻型半挂牵引车"));
        list.add(new CodeName("Q32", "轻型全挂牵引车"));
        return list;
    }

    //牵引车代号
    public static List<String> QYC_DH() {
        return QYC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }


    //货车
    public static List<CodeName> HC() {
        List list = new ArrayList<CodeName>();
        list.add(new CodeName("H25", "中型平板货车"));
        list.add(new CodeName("H26", "中型集装厢车"));
        list.add(new CodeName("H27", "中型自卸货车"));
        list.add(new CodeName("H28", "中型特殊结构货车"));
        list.add(new CodeName("H29", "中型仓栅式货车"));
        list.add(new CodeName("H2A", "中型车辆运输车"));
        list.add(new CodeName("H2B", "中型厢式自卸货车"));
        list.add(new CodeName("H2C", "中型罐式自卸货车"));
        list.add(new CodeName("H2D", "中型平板自卸货车"));
        list.add(new CodeName("H2E", "中型集装厢自卸货车"));
        list.add(new CodeName("H2F", "中型特殊结构自卸货车"));
        list.add(new CodeName("H2G", "中型仓栅式自卸货车"));
        list.add(new CodeName("H31", "轻型普通货车"));
        list.add(new CodeName("H32", "轻型厢式货车"));
        list.add(new CodeName("H33", "轻型封闭货车"));
        list.add(new CodeName("H34", "轻型罐式货车"));
        list.add(new CodeName("H35", "轻型平板货车"));
        list.add(new CodeName("H37", "轻型自卸货车"));
        list.add(new CodeName("H38", "轻型特殊结构货车"));
        list.add(new CodeName("H39", "轻型仓栅式货车"));
        list.add(new CodeName("H3A", "轻型车辆运输车"));
        list.add(new CodeName("H3B", "轻型厢式自卸货车"));
        list.add(new CodeName("H3C", "轻型罐式自卸货车"));
        list.add(new CodeName("H3D", "轻型平板自卸货车"));
        list.add(new CodeName("H3F", "轻型特殊结构自卸货车"));
        list.add(new CodeName("H3G", "轻型仓栅式自卸货车"));
        list.add(new CodeName("H41", "微型普通货车"));
        list.add(new CodeName("H42", "微型厢式货车"));
        list.add(new CodeName("H43", "微型封闭货车"));
        list.add(new CodeName("H44", "微型罐式货车"));
        list.add(new CodeName("H45", "微型自卸货车"));
        list.add(new CodeName("H46", "微型特殊结构货车"));
        list.add(new CodeName("H47", "微型仓栅式货车"));
        list.add(new CodeName("H4A", "微型车辆运输车"));
        list.add(new CodeName("H4B", "微型厢式自卸货车"));
        list.add(new CodeName("H4C", "微型罐式自卸货车"));
        list.add(new CodeName("H4F", "微型特殊结构自卸货车"));
        list.add(new CodeName("H4G", "微型仓栅式自卸货车"));
        list.add(new CodeName("H51", "普通低速货车"));
        list.add(new CodeName("H52", "厢式低速货车"));
        list.add(new CodeName("H53", "罐式低速货车"));
        list.add(new CodeName("H54", "自卸低速货车"));
        list.add(new CodeName("H55", "仓栅式低速货车"));
        list.add(new CodeName("H5B", "厢式自卸低速货车"));
        list.add(new CodeName("H5C", "罐式自卸低速货车"));
        list.add(new CodeName("H11", "重型普通货车"));
        list.add(new CodeName("H12", "重型厢式货车"));
        list.add(new CodeName("H13", "重型封闭货车"));
        list.add(new CodeName("H14", "重型罐式货车"));
        list.add(new CodeName("H15", "重型平板货车"));
        list.add(new CodeName("H16", "重型集装厢车"));
        list.add(new CodeName("H17", "重型自卸货车"));
        list.add(new CodeName("H18", "重型特殊结构货车"));
        list.add(new CodeName("H19", "重型仓栅式货车"));
        list.add(new CodeName("H1A", "重型车辆运输车"));
        list.add(new CodeName("H1B", "重型厢式自卸货车"));
        list.add(new CodeName("H1C", "重型罐式自卸货车"));
        list.add(new CodeName("H1D", "重型平板自卸货车"));
        list.add(new CodeName("H1E", "重型集装厢自卸货车"));
        list.add(new CodeName("H1F", "重型特殊结构自卸货车"));
        list.add(new CodeName("H1G", "重型仓栅式自卸货车"));
        list.add(new CodeName("H21", "中型普通货车"));
        list.add(new CodeName("H22", "中型厢式货车"));
        list.add(new CodeName("H23", "中型封闭货车"));
        list.add(new CodeName("H24", "中型罐式货车"));
        return list;
    }

    //货车代号
    public static List<String> HC_DH() {
        return HC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    //罐式货车代号
    public static List<String> GsHC_DH() {
        return HC().stream().filter(s -> s.getName().contains("罐")).map(s -> s.getCode()).collect(Collectors.toList());
    }

    //重中形货车代号
    public static List<String> ZZXHC_DH() {
        return HC().stream().filter(s -> s.getName().contains("中型") || s.getName().contains("重型")).map(s -> s.getCode()).collect(Collectors.toList());
    }

    //挂车
    public static List<CodeName> GC() {
        List list = new ArrayList<CodeName>();
        list.add(new CodeName("B11", "重型普通半挂车"));
        list.add(new CodeName("B12", "重型厢式半挂车"));
        list.add(new CodeName("B13", "重型罐式半挂车"));
        list.add(new CodeName("B14", "重型平板半挂车"));
        list.add(new CodeName("B15", "重型集装箱半挂车"));
        list.add(new CodeName("B16", "重型自卸半挂车"));
        list.add(new CodeName("B17", "重型特殊结构半挂车"));
        list.add(new CodeName("B18", "重型仓栅式半挂车"));
        list.add(new CodeName("B19", "重型旅居半挂车"));
        list.add(new CodeName("B1A", "重型专项作业半挂车"));
        list.add(new CodeName("B1B", "重型低平板半挂车"));
        list.add(new CodeName("B1C", "重型车辆运输半挂车"));
        list.add(new CodeName("B1D", "重型罐式自卸半挂车"));
        list.add(new CodeName("G11", "重型普通全挂车"));
        list.add(new CodeName("G12", "重型厢式全挂车"));
        list.add(new CodeName("G13", "重型罐式全挂车"));
        list.add(new CodeName("G14", "重型平板全挂车"));
        list.add(new CodeName("G15", "重型集装箱全挂车"));
        list.add(new CodeName("G16", "重型自卸全挂车"));
        list.add(new CodeName("G17", "重型仓栅式全挂车"));
        list.add(new CodeName("G18", "重型旅居全挂车"));
        list.add(new CodeName("G19", "重型专项作业全挂车"));
        list.add(new CodeName("G1A", "重型厢式自卸全挂车"));
        list.add(new CodeName("G1B", "重型罐式自卸全挂车"));
        list.add(new CodeName("G1C", "重型平板自卸全挂车"));
        list.add(new CodeName("G1D", "重型集装箱自卸全挂车"));
        list.add(new CodeName("G1E", "重型仓栅式自卸全挂车"));
        list.add(new CodeName("G1F", "重型专项作业自卸全挂车"));
        list.add(new CodeName("G21", "中型普通全挂车"));
        list.add(new CodeName("G22", "中型厢式全挂车"));
        list.add(new CodeName("G23", "中型罐式全挂车"));
        list.add(new CodeName("G24", "中型平板全挂车"));
        list.add(new CodeName("G25", "中型集装箱全挂车"));
        list.add(new CodeName("G26", "中型自卸全挂车"));
        list.add(new CodeName("G27", "中型仓栅式全挂车"));
        list.add(new CodeName("G28", "中型旅居全挂车"));
        list.add(new CodeName("G29", "中型专项作业全挂车"));
        list.add(new CodeName("G2A", "中型厢式自卸全挂车"));
        list.add(new CodeName("G2B", "中型罐式自卸全挂车"));
        list.add(new CodeName("G2C", "中型平板自卸全挂车"));
        list.add(new CodeName("G2D", "中型集装箱自卸全挂车"));
        list.add(new CodeName("G2E", "中型仓栅式自卸全挂车"));
        list.add(new CodeName("G2F", "中型专项作业自卸全挂车"));
        list.add(new CodeName("G31", "轻型普通全挂车"));
        list.add(new CodeName("G32", "轻型厢式全挂车"));
        list.add(new CodeName("G33", "轻型罐式全挂车"));
        list.add(new CodeName("G34", "轻型平板全挂车"));
        list.add(new CodeName("G35", "轻型自卸全挂车"));
        list.add(new CodeName("G36", "轻型仓栅式全挂车"));
        list.add(new CodeName("G37", "轻型旅居全挂车"));
        list.add(new CodeName("G38", "轻型专项作业全挂车"));
        list.add(new CodeName("G3A", "轻型厢式自卸全挂车"));
        list.add(new CodeName("G3B", "轻型罐式自卸全挂车"));
        list.add(new CodeName("G3C", "轻型平板自卸全挂车"));
        list.add(new CodeName("G3D", "轻型集装箱自卸全挂车"));
        list.add(new CodeName("G3E", "轻型仓栅式自卸全挂车"));
        list.add(new CodeName("G3F", "轻型专项作业自卸全挂车"));
        list.add(new CodeName("B3K", "轻型低平板自卸半挂车"));
        list.add(new CodeName("B3U", "轻型中置轴旅居挂车"));
        list.add(new CodeName("B3V", "轻型中置轴车辆运输车"));
        list.add(new CodeName("B3W", "轻型中置轴普通挂车"));
        return list;
    }

    //挂车代号
    public static List<String> GC_DH() {
        return GC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }

    //罐式挂车代号
    public static List<String> GSGC_DH() {
        return GC().stream().filter(s -> s.getName().contains("罐")).map(s -> s.getCode()).collect(Collectors.toList());
    }

    //专项作业车
    public static List<CodeName> ZXZYC() {
        List list = new ArrayList<CodeName>();
        list.add(new CodeName("Z11", "大型非载货专项作业车"));
        list.add(new CodeName("Z12", "大型载货专项作业车"));
        list.add(new CodeName("Z21", "中型非载货专项作业车"));
        list.add(new CodeName("Z22", "中型载货专项作业车"));
        list.add(new CodeName("Z31", "小型非载货专项作业车"));
        list.add(new CodeName("Z32", "小型载货专项作业车"));
        list.add(new CodeName("Z41", "微型非载货专项作业车"));
        list.add(new CodeName("Z42", "微型载货专项作业车"));
        list.add(new CodeName("Z51", "重型非载货专项作业车"));
        list.add(new CodeName("Z52", "重型载货专项作业车"));
        list.add(new CodeName("Z71", "轻型非载货专项作业车"));
        list.add(new CodeName("Z72", "轻型载货专项作业车"));
        return list;
    }

    //专项作业车代号
    public static List<String> ZXZYC_DH() {
        return ZXZYC().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }


    //摩托车
    public static List<CodeName> MOTO() {
        List list = new ArrayList<CodeName>();
        list.add(new CodeName("M11", "普通正三轮摩托车"));
        list.add(new CodeName("M12", "轻便正三轮摩托车"));
        list.add(new CodeName("M13", "正三轮载客摩托车"));
        list.add(new CodeName("M14", "正三轮载货摩托车"));
        list.add(new CodeName("M15", "侧三轮摩托车"));
        list.add(new CodeName("M21", "普通二轮摩托车"));
        list.add(new CodeName("M22", "轻便二轮摩托车"));
        list.add(new CodeName("N11", "三轮汽车"));
        return list;
    }

    //摩托车代号
    public static List<String> MOTO_DH() {
        return MOTO().stream().map(s -> s.getCode()).collect(Collectors.toList());
    }
}
