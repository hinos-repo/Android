package com.example.mvvmsample;

import org.junit.Test;
import org.w3c.dom.Node;

public class ExampleJavaTest
{
    static class TestData
    {
        int index;
        String name;

        public TestData(int index, String name)
        {
            this.index = index;
            this.name = name;
        }
    }

    @Test
    public void myTest()
    {

        int nData1 = 1;
        int nData2 = 1;

        System.out.println(nData1 == nData2);

        TestData data1 = new TestData(1, "1");
        TestData data2 = new TestData(1, "1");


        System.out.println(data1 == data2);

        String strData1 = "ab";
        String strData2 = "ab";


        System.out.println(strData1 == strData2);
        System.out.println(strData1.hashCode());
        System.out.println(strData2.hashCode());


        String strIData1 = new String("ab");
        String strIData2 = new String("ab");

        System.out.println(strIData1.hashCode());
        System.out.println(strIData2.hashCode());
        System.out.println(strIData1 == strIData2);
    }
}
