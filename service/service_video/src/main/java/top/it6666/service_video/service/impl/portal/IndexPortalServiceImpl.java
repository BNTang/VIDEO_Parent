package top.it6666.service_video.service.impl.portal;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.it6666.service_video.entity.Author;
import top.it6666.service_video.entity.Category;
import top.it6666.service_video.entity.Content;
import top.it6666.service_video.service.AuthorService;
import top.it6666.service_video.service.CategoryService;
import top.it6666.service_video.service.ContentService;
import top.it6666.service_video.service.portal.IndexPortalService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author BNTang
 */
@Service
public class IndexPortalServiceImpl implements IndexPortalService {
    @Resource
    private ContentService contentService;

    @Resource
    private AuthorService authorService;

    @Resource
    private CategoryService categoryService;

    @Override
    @Cacheable(value = "index", key = "'getContentIndexList'")
    public List<Content> getContentIndexList() {
        QueryWrapper<Content> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        return contentService.list(wrapper);
    }

    @Override
    @Cacheable(value = "index", key = "'getAuthorIndexList'")
    public List<Author> getAuthorIndexList() {
        // 查询前4条作者
        QueryWrapper<Author> wrapperAuthor = new QueryWrapper<>();
        wrapperAuthor.orderByDesc("id");
        wrapperAuthor.last("limit 4");
        return authorService.list(wrapperAuthor);
    }

    @Override
    @Cacheable(value = "index", key = "'getCategoryIndexList'")
    public List<Category> getCategoryIndexList() {
        QueryWrapper<Category> wrapperCategory = new QueryWrapper<>();
        wrapperCategory.orderByDesc("id");
        wrapperCategory.eq("parent_id", "0");
        wrapperCategory.last("limit 8");
        return categoryService.list(wrapperCategory);
    }
}