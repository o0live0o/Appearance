package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.bean.ResultBean;
import com.o0live0o.app.appearance.log.L;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Dictionary;
import java.util.Map;

public class WebServiceHelper {
//
//    public static String  Test(Map<String,String> map) throws IOException, XmlPullParserException {
//
//        String url = "http://192.168.2.211:3717/Hello.asmx";
//        String namespace = "http://tempuri.org/";
//        String method = "Add";
//
//        SoapObject request = new SoapObject(namespace,method);
//
//        for (Map.Entry<String,String> entry : map.entrySet()){
//            request.addProperty(entry.getKey(),entry.getValue());
//        }
//
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
//        envelope.bodyOut = request;
//        envelope.dotNet = true;
//
//        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
//        httpTransportSE.call(null,envelope);
//
//        SoapObject object = (SoapObject)envelope.bodyIn;
//
//        String result = object.getProperty(0).toString();
//        L.d(result);
//        return  result;
//    }

    private String url = "";
    private String namespace = "";
    private static WebServiceHelper webServiceHelper = null;
    private WebServiceHelper(){
        webServiceHelper = new WebServiceHelper();
    }

    public static WebServiceHelper getInstance(){
        return webServiceHelper;
    }

    public void init(String url,String namespace) {
        this.url = url;
        this.namespace = namespace;

    }


    public  ResultBean SendWebservice(Map<String,String> map,String method) throws IOException,XmlPullParserException{
        ResultBean resultBean = new ResultBean();
        SoapObject request = new SoapObject(this.namespace,method);

        for (Map.Entry<String,String> entry : map.entrySet()){
            request.addProperty(entry.getKey(),entry.getValue());
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        httpTransportSE.call(null,envelope);
        SoapObject object = (SoapObject)envelope.bodyIn;
        String result = object.getProperty(0).toString();
        L.d(result);
        resultBean.setSucc(true);
        resultBean.setMsg(result);
        return  resultBean;
    }


}
