package top.it6666.service_video.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import top.it6666.common_utils.entity.ContentWebVO;
import top.it6666.service_video.entity.Content;
import top.it6666.service_video.entity.vo.ContentPreviewVO;
import top.it6666.service_video.entity.vo.ContentVO;

/**
 * <p>
 * 作品表 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
public interface ContentService extends IService<Content> {

    /**
     * <p>
     * 保存基本信息
     * </p>
     *
     * @param contentVO
     *            基本信息实体VO
     * @return 添加之后生成的ID
     */
    String saveContentInfo(ContentVO contentVO);

    /**
     * <p>
     * 根据ID获取一条作品简介信息数据
     * </p>
     *
     * @param id
     *            作品ID
     * @return 作品简介信息
     */
    ContentVO getContentWithInfoId(String id);

    /**
     * <p>
     * 修改作品基本信息
     * </p>
     *
     * @param contentVO
     *            基本信息VO实体
     */
    void updateContentInfo(ContentVO contentVO);

    /**
     * <b> 获取作品的预览信息 </b>
     *
     * @param id
     *            作品ID
     * @return 预览信息
     */
    ContentPreviewVO getContentPreView(String id);

    /**
     * <b> 发布作品 </b>
     *
     * @param id
     *            作品ID
     */
    void sendContentWithId(String id);

    /**
     * <b> 分页查询作品列表方法 </b>
     *
     * @param pageParam
     *            分页信息
     * @param contentVO
     *            过滤条件信息实体
     */
    void contentPageQuery(Page<Content> pageParam, ContentVO contentVO);

    /**
     * <b> 根据作品id删除对应视频信息 </b>
     *
     * @param id
     *            作品ID
     */
    void deleteContentWithId(String id);

    /**
     * <b> 根据作者id查询这个作者的作品列表 </b>
     * 
     * @param authorId
     *            作者ID
     * @return 作品
     */
    List<Content> selectByAuthorId(String authorId);

    /**
     * 获取作品信息
     * 
     * @param contentId
     *            作品Id
     * @return 作品信息
     */
    ContentWebVO selectContentDetailById(String contentId);

    /**
     * 更新作品浏览数
     * 
     * @param id
     *            作品ID
     */
    void updatePageViewCount(String id);
}