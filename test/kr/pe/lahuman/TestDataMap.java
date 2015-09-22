package kr.pe.lahuman;

import kr.pe.lahuman.utils.DataMap;

import java.util.List;

/**
 * Created by lahuman on 2015-09-22.
 */
public class TestDataMap {

    public static void main(String[] args){

        DataMap dm = new DataMap();
        dm.put("HELLO_WOrld", "helloWorld");

        dm.put("Integer_TEst", Integer.MAX_VALUE);

        dm.put("double_test", "12345678912");

        dm.put(new TestDataMap(), "asdfasdf");

        System.out.println(dm.getInt("integerTest"));
        System.out.println(dm.getInt("doubleTest", 100));
        System.out.println(dm.getDouble("doubleTest"));
        System.out.println(dm.getString("helloWorld"));
        System.out.println(dm.getString("asdfiekasdfie"));
        System.out.println(dm.getKeys().length);
        System.out.println(dm);


    }
}
