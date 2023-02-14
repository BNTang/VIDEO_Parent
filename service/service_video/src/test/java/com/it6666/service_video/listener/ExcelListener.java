package com.it6666.service_video.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.it6666.service_video.entity.excel.StudentData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<StudentData> {
    /**
     * <p>
     * 一行一行读取数据
     * </p>
     *
     * @param studentData     读取到的一行数据模型对象
     * @param analysisContext analysisContext
     */
    @Override
    public void invoke(StudentData studentData, AnalysisContext analysisContext) {
        System.out.println("data = " + studentData);
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头: " + headMap);
    }

    /**
     * <p>
     * 所有的数据读取完毕之后会自动调用
     * </p>
     *
     * @param analysisContext analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完毕");
    }
}