package top.it6666.service_video.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.it6666.service_video.entity.Author;
import top.it6666.service_video.entity.vo.AuthorQueryVO;

/**
 * <p>
 * 创作者 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-03-27
 */
public interface AuthorService extends IService<Author> {
    /**
     * 带条件查询的分页
     *
     * @param pageInfo    分页信息
     * @param authorQueryVO 查询条件
     */
    void pageQuery(Page<Author> pageInfo, AuthorQueryVO authorQueryVO);
}
