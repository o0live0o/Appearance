package com.o0live0o.app.appearance.data;

import com.o0live0o.app.appearance.bean.ExteriorBean;
import com.o0live0o.app.appearance.bean.NavBean;
import com.o0live0o.app.appearance.enums.CheckState;

import java.util.ArrayList;
import java.util.List;

public class ExteriorList {

    public static List<ExteriorBean> getExteriorList(){
        List<ExteriorBean> list = new ArrayList<>();
        list.add(new ExteriorBean(1,"1.号牌号码/车辆类型"));
        list.add(new ExteriorBean(2,"2.车辆品牌/型号"));
        list.add(new ExteriorBean(3,"3.车辆识别代号(或整车出厂编号)"));
        list.add(new ExteriorBean(4,"4.发动机号码(或电动机号码)"));
        list.add(new ExteriorBean(5,"5.车身颜色和外形"));
        list.add(new ExteriorBean(6,"6.外廓尺寸"));
        list.add(new ExteriorBean(7,"7.轴距"));
        list.add(new ExteriorBean(8,"8.整备质量"));
        list.add(new ExteriorBean(9,"9.核定载人数"));
        list.add(new ExteriorBean(10,"10.核定载质量"));
        list.add(new ExteriorBean(11,"11.栏板高度"));
        list.add(new ExteriorBean(12,"12.后轴钢板弹簧片数"));
        list.add(new ExteriorBean(13,"13.客车应急出口"));
        list.add(new ExteriorBean(14,"14.客车乘客通道和引道"));
        list.add(new ExteriorBean(15,"15.货厢"));
        list.add(new ExteriorBean(16,"16.车身外观"));
        list.add(new ExteriorBean(17,"17.外观标识、标注和标牌"));
        list.add(new ExteriorBean(18,"18.外部照明和信号灯具"));
        list.add(new ExteriorBean(19,"19.轮胎"));
        list.add(new ExteriorBean(20,"20.号牌及号牌安装"));
        list.add(new ExteriorBean(21,"21.加装/改装灯具"));
        list.add(new ExteriorBean(22,"22.汽车安全带"));
        list.add(new ExteriorBean(23,"23.机动车用三角警告牌"));
        list.add(new ExteriorBean(24,"24.灭火器"));
        list.add(new ExteriorBean(25,"25.行驶记录装置"));
        list.add(new ExteriorBean(26,"26.车身反光标识"));
        list.add(new ExteriorBean(27,"27.车辆尾部标志板"));
        list.add(new ExteriorBean(28,"28.侧后防护装置"));
        list.add(new ExteriorBean(29,"29.应急锤"));
        list.add(new ExteriorBean(30,"30.急救箱"));
        list.add(new ExteriorBean(31,"31.限速功能或限速装置"));
        list.add(new ExteriorBean(32,"32.防抱死制动装置"));
        list.add(new ExteriorBean(33,"33.辅助制动装置"));
        list.add(new ExteriorBean(34,"34.盘式制动器"));
        list.add(new ExteriorBean(35,"35.紧急切断装置"));
        list.add(new ExteriorBean(36,"36.发动机舱自动灭火装置"));
        list.add(new ExteriorBean(37,"37.手动机械断电开关"));
        list.add(new ExteriorBean(38,"38.副制动踏板"));
        list.add(new ExteriorBean(39,"39.校车标志灯和校车停车指示标志牌"));
        list.add(new ExteriorBean(40,"40.危险货物运输车标志"));
        list.add(new ExteriorBean(41,"41.肢体残疾人操纵辅助装置"));
        return list;
    }

    public static List<ExteriorBean> getDCList(){
        List<ExteriorBean> list = new ArrayList<>();
        list.add(new ExteriorBean(42,"42.转向系",CheckState.PASS));
        list.add(new ExteriorBean(43,"43.传动系",CheckState.PASS));
        list.add(new ExteriorBean(44,"44.制动系",CheckState.PASS));
        list.add(new ExteriorBean(45,"45.仪表和指示器",CheckState.PASS));
        return list;
    }
    public static List<ExteriorBean> getC1List(){
        List<ExteriorBean> list = new ArrayList<>();
        list.add(new ExteriorBean(46,"46.转向系部件",CheckState.PASS));
        list.add(new ExteriorBean(47,"47.传动系部件",CheckState.PASS));
        list.add(new ExteriorBean(48,"48.行驶系部件",CheckState.PASS));
        list.add(new ExteriorBean(49,"49.制动系部件",CheckState.PASS));
        list.add(new ExteriorBean(50,"50.其它部件",CheckState.PASS));
        return list;
    }

    public static List<NavBean> getNavList(){
        List<NavBean> list = new ArrayList<>();
        if (FinalData.isCheckF1())
        list.add(new NavBean("外观检查","MainActivity",FinalData.F1));
        if (FinalData.isCheckDC())
        list.add(new NavBean("动态底盘检查","MainActivity",FinalData.DC));
        if (FinalData.isCheckC1())
        list.add(new NavBean("底盘检查","MainActivity",FinalData.C1));

        list.add(new NavBean("日志管理","LogActivity",""));
        list.add(new NavBean("车辆调度","DispatchActivity",""));
        return list;
    }

    public static List<Integer> getCheckItem(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(16);
        list.add(18);
        list.add(19);
        list.add(20);
        return list;
    }

    public static List<Integer> getItem(){
        List<Integer> list = new ArrayList<>();
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
        return list;
    }

}
