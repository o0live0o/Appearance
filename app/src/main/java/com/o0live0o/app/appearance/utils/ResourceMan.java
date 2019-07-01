package com.o0live0o.app.appearance.utils;

import android.content.Context;
import android.content.res.Resources;

import java.lang.reflect.Field;

//根据字符串获取资源id
public class ResourceMan {
      public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getResId(String name, Context context){
        Resources r = context.getResources();
        int id = r.getIdentifier(name,"drawable","");
        return id;
    }
}
