package com.o0live0o.app.appearance.utils;

import android.util.Base64;

import com.o0live0o.app.appearance.log.L;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESTool {

    private  byte[] desKey = { 0x01, 0x03, 0x04, (byte) 0xa1, 0x00, 0x21, 0x33, 0x33 };
    private  byte[] desIV = { (byte) 0xa1, 0x04, 0x03, 0x01, 0x00, 0x21, 0x33, 0x33 };

    //默认密钥向量
    private  byte[] Keys = { (byte) 0xEF, (byte) 0xAB, 0x56, 0x78, (byte) 0x90, 0x34, (byte) 0xCD, 0x12 };
    private  String IV = "2009198162";
    private  AlgorithmParameterSpec iv = null;// 加密算法的参数接口
    private  Key key;
    public  DESTool(){
        try
        {
            byte[] rgbKey = "20091981".getBytes("UTF-8");
            DESKeySpec keySpec = new DESKeySpec(rgbKey);
            iv = new IvParameterSpec(Keys);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂
            key = keyFactory.generateSecret(keySpec);// 得到密钥对象
        }
        catch(Exception e) {
            L.d(e.getMessage());
        }
    }

    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        byte[] temp = Base64.encode(pasByte, Base64.DEFAULT);
        return  new String(temp);
    }


    public String getEnc(String inputString) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String outputString = "";
        try {
            byteMing = inputString.getBytes("UTF-8");
            byteMi = this.getEncCode(byteMing);
            byte[] temp = Base64.encode(byteMi, Base64.DEFAULT);
            outputString = new String(temp);
        } catch (Exception e) {

        } finally {

            byteMing = null;

            byteMi = null;
        }
        return outputString;
    }

    private byte[] getEncCode(byte[] bt) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            // 得到Cipher实例
            cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byteFina = cipher.doFinal(bt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }
}
