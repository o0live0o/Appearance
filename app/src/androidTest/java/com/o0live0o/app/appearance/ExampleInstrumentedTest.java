package com.o0live0o.app.appearance;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.o0live0o.app.appearance.utils.CreateXML;
import com.o0live0o.app.appearance.utils.Tools;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.URLDecoder;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.o0live0o.app.appearance", appContext.getPackageName());
    }

    @Test
    public void  testAdd(){
        assertEquals(1,1);
    }

    @Test
    public void testCreateXml(){
        CreateXML.create901();
    }

    @Test
    public void testRegexMatch(){
        String s1 = URLDecoder.decode("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><head><code>1</code><message>%E6%B2%A1%E6%9C%89%E6%8E%A5%E6%94%B6%E5%88%B0XML%E6%9F%A5%E8%AF%A2%E5%8F%82%E6%95%B0%21</message></head><body>null</body></root>");
        String s = Tools.matchField("<?xml version=\"1.0\" encoding=\"UTF-8\"?><root><head><code>1</code><message>数据保存成功</message></head><body>null</body></root>","code");

    }
}
