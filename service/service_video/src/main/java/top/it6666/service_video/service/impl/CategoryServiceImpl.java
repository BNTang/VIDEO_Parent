package top.it6666.service_video.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.it6666.service_video.entity.Category;
import top.it6666.service_video.entity.excel.CategoryData;
import top.it6666.service_video.entity.tree.LevelCategory;
import top.it6666.service_video.listener.CategoryExcelListener;
import top.it6666.service_video.mapper.CategoryMapper;
import top.it6666.service_video.service.CategoryService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 科目分类 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * excel监听器
     */
    @Resource
    private CategoryExcelListener categoryExcelListener;

    @Override
    public void saveCategory(MultipartFile file) {
        // 读取excel
        try {
            EasyExcel.read(file.getInputStream(), CategoryData.class, categoryExcelListener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LevelCategory> getTreeCategory() {
        // 1.读取1级分类
        QueryWrapper<Category> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id", 0);
        List<Category> oneCategories = baseMapper.selectList(oneQueryWrapper);

        // 2.读取2级分类
        QueryWrapper<Category> twoQueryWrapper = new QueryWrapper<>();
        twoQueryWrapper.ne("parent_id", 0);
        List<Category> twoCategories = baseMapper.selectList(twoQueryWrapper);

        // 封装模型数据
        List<LevelCategory> resultData = new ArrayList<>();

        oneCategories.forEach(oneCategory -> {

            // 把Category转成LevelCategory
            LevelCategory oneLevelCategory = new LevelCategory();

            // 属性的复制
            // 把前一个对象当中的属性值复制到后一个对象当中相同的属性中去
            // 如果前一个对象在后一个对象当中没有找到相同的属性,就不复制
            BeanUtils.copyProperties(oneCategory, oneLevelCategory);

            // 获取当前子分类有哪些
            // 遍历所有的二级分类
            twoCategories.forEach(twoCategory -> {
                // 二级分类的parent_id=一级分类的id,就是当前一级分类,的子级
                if (twoCategory.getParentId().equals(oneCategory.getId())) {
                    LevelCategory twoLevelCategory = new LevelCategory();

                    BeanUtils.copyProperties(twoCategory, twoLevelCategory);

                    oneLevelCategory.getChildren().add(twoLevelCategory);
                }
            });

            resultData.add(oneLevelCategory);
        });

        return resultData;
    }
}