package com.o0live0o.app.appearance.utils;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    //中文字符的正则
    public final static String REGEX_CHINESE = "[\u4e00-\u9fa5]";

    //去掉字符中的中文
    public static String RemoveChinese(String s){
        if (s != null) {
            Pattern pattern = Pattern.compile(REGEX_CHINESE);
            Matcher matcher = pattern.matcher(s);
            return matcher.replaceAll("");
        }
        return s;
    }

    public static String matchField(String src,String field){
        Pattern pattern = Pattern.compile("<"+field+">(.*?)</"+field+">");
        Matcher matcher = pattern.matcher(src);
        if (matcher.find()){
            String s = matcher.group(1).trim();
            return s;
        }
        return "";

    }






}
