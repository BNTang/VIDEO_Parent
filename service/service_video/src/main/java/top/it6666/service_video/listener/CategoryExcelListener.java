/*
 * Copyright (c) 2020-2030 IT6666.Co.Ltd. All Rights Reserved.
 */
package top.it6666.service_video.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.it6666.service_video.entity.Category;
import top.it6666.service_video.entity.excel.CategoryData;
import top.it6666.service_video.service.CategoryService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author BNTang
 * @version S2.3.2Dev
 * @program video_parent
 * @date Created in 2021/4/4 2:25
 * @description 分类管理上传excel监听器
 **/
@Component
@Slf4j
public class CategoryExcelListener extends AnalysisEventListener<CategoryData> {
    @Resource
    private CategoryService categoryService;

    @Override
    public void invoke(CategoryData categoryData, AnalysisContext analysisContext) {
        // 写入到数据库当中
        if (!Objects.isNull(categoryData)) {
            // 如果一级分类不存在,就保存到数据库当中
            Category oneCategory = this.isExistOneCategory(categoryData);

            if (Objects.isNull(oneCategory)) {
                oneCategory = new Category();
                oneCategory.setTitle(categoryData.getOneCategoryData());
                oneCategory.setParentId("0");

                categoryService.save(oneCategory);
            }

            // 保存2级分类,先判断2级分类是否已经存在
            Category twoCategory = this.isExistTwoCategory(categoryData, oneCategory.getId());

            if (Objects.isNull(twoCategory)) {
                twoCategory = new Category();
                twoCategory.setTitle(categoryData.getTwoCategoryData());
                twoCategory.setParentId(oneCategory.getId());

                categoryService.save(twoCategory);
            }
        }
    }

    /**
     * <p>
     * 判断1级分类是否已经存在
     * </p>
     */
    private Category isExistOneCategory(CategoryData categoryData) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", categoryData.getOneCategoryData());
        queryWrapper.eq("parent_id", "0");

        return categoryService.getOne(queryWrapper);
    }

    /**
     * <p>
     * 判断2级分类是否已经存在
     * </p>
     */
    private Category isExistTwoCategory(CategoryData categoryData, String pid) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", categoryData.getTwoCategoryData());
        queryWrapper.eq("parent_id", pid);

        return categoryService.getOne(queryWrapper);
    }

    /**
     * <p>
     * 所有的数据读取完毕之后会自动调用
     * </p>
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("上传分类 excel 解析完毕！");
    }
}