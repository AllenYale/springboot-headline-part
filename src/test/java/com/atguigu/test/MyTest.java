package com.atguigu.test;

import com.atguigu.utils.Result;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * ClassName: MyTest
 * PackageName: com.atguigu.test
 * Description:
 *
 * @Author: Hanyu
 * @Date: 23/12/14 - 13:25
 * @Version: v1.0
 */
public class MyTest {
    @Test
    public void test(){
        Result<List<String>> stringResult = new Result<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("allen");
        arrayList.add("allen1");
        arrayList.add("allen2");

        stringResult.code(1000).message("test msg").setData(arrayList);

        System.out.println("stringResult = " + stringResult);


    }
}
