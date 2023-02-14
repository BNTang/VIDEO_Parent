package top.it6666.service_video.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

import top.it6666.service_video.entity.Chapter;
import top.it6666.service_video.entity.vo.ChapterVO;

/**
 * <p>
 * 作品章节 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * <b> 获取章节嵌套形式的数据结构列表 </b>
     *
     * @param contentId
     *            作品ID
     * @return 章节小节嵌套形式的数据结构
     */
    List<ChapterVO> getChapterSection(String contentId);

    /**
     * <b> 根据ID删除章节 </b>
     *
     * @param id
     *            章节ID
     */
    void deleteChapterById(String id);

    /**
     * <b> 根据作品id删除作品关联的所有章节 </b>
     *
     * @param id
     *            作品ID
     */
    void deleteChapterWithContentId(String id);

    /**
     * 查询当前作品的章节信息
     * 
     * @param contentId
     *            作品ID
     * @return 章节信息
     */
    List<ChapterVO> getChapterContentVideo(String contentId);
}
