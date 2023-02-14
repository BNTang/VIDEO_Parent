package top.it6666.service_video.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import top.it6666.common_utils.entity.ContentWebVO;
import top.it6666.service_video.entity.Content;
import top.it6666.service_video.entity.vo.ContentPreviewVO;

/**
 * <p>
 * 作品表 Mapper 接口
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
public interface ContentMapper extends BaseMapper<Content> {

    /**
     * <b> 获取作品的预览信息 </b>
     *
     * @param id
     *            作品ID
     * @return 预览信息
     */
    ContentPreviewVO getContentPreviewVoById(String id);

    /**
     * 获取作品信息
     * 
     * @param contentId
     *            作品ID
     * @return 作品信息
     */
    ContentWebVO getContentDetailById(String contentId);
}