/*
 * Copyright (c) 2020-2030 it6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/4 2:22
 * @description 分类excel上传的模型类
 **/
@Data
public class CategoryData {
    @ExcelProperty(index = 0)
    private String oneCategoryData;

    @ExcelProperty(index = 1)
    private String twoCategoryData;
}