package com.o0live0o.app.appearance.bean;

public class NavBean {

    private String menuName;
    private String activity;
    private String checkType;

    public NavBean(String menuName,String activity,String checkType){
        this.menuName = menuName;
        this.activity = activity;
        this.checkType = checkType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }
}
