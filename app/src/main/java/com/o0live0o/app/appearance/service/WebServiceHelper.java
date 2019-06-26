package com.o0live0o.app.appearance.service;

import com.o0live0o.app.appearance.log.L;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Dictionary;
import java.util.Map;

public class WebServiceHelper {

    public static String  Test(Map<String,String> map) throws IOException, XmlPullParserException {

        String url = "http://192.168.2.211:3717/Hello.asmx";
        String namespace = "http://tempuri.org/";
        String method = "Add";

        SoapObject request = new SoapObject(namespace,method);

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
        return  result;
    }

}
