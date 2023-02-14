/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package com.it6666.service_video;

import com.alibaba.excel.EasyExcel;
import com.it6666.service_video.entity.excel.StudentData;
import com.it6666.service_video.listener.ExcelListener;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/3 22:08
 * @description
 **/
@SpringBootTest
public class ServiceVideoApplicationTests {

    @Test
    public void writeExcel() {
        String fileName = "D:\\01.xlsx";

        List<StudentData> studentDataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            StudentData studentData = new StudentData();

            studentData.setNo(i);
            studentData.setName("BNTang" + i);

            studentDataList.add(studentData);
        }

        /*
         * 1.fileName：文件路径与名称
         * 2.StudentData.class：模型的字节码
         * 3.sheet：名称
         * 4.doWrite：要写的数据，是一个list
         */
        EasyExcel.write(fileName, StudentData.class).sheet("学生").doWrite(studentDataList);
    }

    @Test
    public void ReadExcel() {
        String fileName = "D:\\01.xlsx";

        // 每读取一行数据的时候, 就会调用监听器当中的方法
        EasyExcel.read(fileName, StudentData.class, new ExcelListener()).sheet().doRead();
    }

}