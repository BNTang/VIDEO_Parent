package top.it6666.service_video.service;

import top.it6666.service_video.entity.ContentVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import top.it6666.service_video.entity.vo.ContentVideoInfoVO;

/**
 * <p>
 * 作品视频 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
public interface ContentVideoService extends IService<ContentVideo> {

    /**
     * <b>
     * 根据章节ID查询是否存在小节
     * </b>
     *
     * @param id 章节ID
     * @return 是否存在小节
     */
    boolean isExistContentVideoWithChapterId(String id);

    /**
     * <b>
     * 新增小节
     * </b>
     *
     * @param contentVideoInfoVO 小节信息VO
     */
    void saveVideoInfo(ContentVideoInfoVO contentVideoInfoVO);

    /**
     * <b>
     * 根据ID查询小节
     * </b>
     *
     * @param id 小节ID
     * @return 小节信息
     */
    ContentVideoInfoVO getContentVideoInfoById(String id);

    /**
     * <b>
     * 更新小节
     * </b>
     *
     * @param contentVideoInfoVO 小节信息VO
     */
    void updateVideoInfo(ContentVideoInfoVO contentVideoInfoVO);

    /**
     * <b>
     * 根据ID删除小节
     * </b>
     *
     * @param id 小节ID
     * @return 是否删除成功(true ： 删除成功 ， false ： 删除失败)
     */
    boolean deleteVideoById(String id);

    /**
     * <b>
     * 根据作品id删除对应的小节视频
     * </b>
     *
     * @param id 作品id
     */
    void deleteContentVideoByContentId(String id);

    /**
     * <b>
     * 根据视频ID删除小节视频
     * </b>
     *
     * @param id 视频ID
     */
    void deleteContentVideoById(String id);
}