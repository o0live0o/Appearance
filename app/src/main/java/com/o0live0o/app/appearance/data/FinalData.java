package com.o0live0o.app.appearance.data;

public class FinalData {

    public final static String F1 = "F1";
    public final static String DC = "DC";
    public final static String C1 = "C1";


    private static String operator = "";

    public static String getOperator() {
        return operator;
    }

    public static void setOperator(String operator) {
        FinalData.operator = operator;
    }
}
