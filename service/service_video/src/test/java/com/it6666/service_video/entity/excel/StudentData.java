package com.it6666.service_video.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class StudentData {
    @ExcelProperty(value = "学号", index = 0)
    private Integer no;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
}