package top.it6666.service_video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import top.it6666.service_video.entity.Category;
import top.it6666.service_video.entity.tree.LevelCategory;

import java.util.List;

/**
 * <p>
 * 科目分类 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-04
 */
public interface CategoryService extends IService<Category> {

    /**
     * <p>
     * 上传excel
     * </p>
     *
     * @param file 上传的文件
     */
    void saveCategory(MultipartFile file);

    /**
     * <p>
     * 获取分类列表树形结构
     * </p>
     *
     * @return 树形结构分类Data
     */
    List<LevelCategory> getTreeCategory();
}
