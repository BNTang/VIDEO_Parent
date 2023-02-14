package top.it6666.service_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.it6666.common_base.exception.BnTangException;
import top.it6666.common_utils.utils.ResponseResult;
import top.it6666.service_video.client.VodClient;
import top.it6666.service_video.entity.ContentVideo;
import top.it6666.service_video.entity.vo.ContentVideoInfoVO;
import top.it6666.service_video.mapper.ContentVideoMapper;
import top.it6666.service_video.service.ContentVideoService;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 作品视频 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
@Service
public class ContentVideoServiceImpl extends ServiceImpl<ContentVideoMapper, ContentVideo>
        implements ContentVideoService {

    @Resource
    private VodClient vodClient;

    @Override
    public boolean isExistContentVideoWithChapterId(String id) {
        QueryWrapper<ContentVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void saveVideoInfo(ContentVideoInfoVO contentVideoInfoVO) {
        ContentVideo contentVideo = new ContentVideo();
        BeanUtils.copyProperties(contentVideoInfoVO, contentVideo);
        this.save(contentVideo);
    }

    @Override
    public ContentVideoInfoVO getContentVideoInfoById(String id) {
        // 从video表中取数据
        ContentVideo contentVideo = this.getById(id);
        if (Objects.isNull(contentVideo)) {
            throw new BnTangException(20001, "数据不存在");
        }

        // 创建videoInfoForm对象
        ContentVideoInfoVO contentVideoInfoVO = new ContentVideoInfoVO();
        BeanUtils.copyProperties(contentVideo, contentVideoInfoVO);
        return contentVideoInfoVO;
    }

    @Override
    public void updateVideoInfo(ContentVideoInfoVO contentVideoInfoVO) {
        ContentVideo contentVideo = new ContentVideo();
        BeanUtils.copyProperties(contentVideoInfoVO, contentVideo);
        this.updateById(contentVideo);
    }

    @Override
    public boolean deleteVideoById(String id) {
        // 删除小节对应的视频
        ContentVideo contentVideo = baseMapper.selectById(id);

        // 获取小节对应的视频
        String videoSourceId = contentVideo.getVideoSourceId();

        // 判断是否有小节视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 删除视频
            // 服务之间调用 Nacos
            ResponseResult result = vodClient.deleteAliYunVideo(videoSourceId);
            if (!result.getSuccess()) {
                throw new BnTangException(20001, result.getMessage());
            }
        }
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public void deleteContentVideoByContentId(String id) {
        QueryWrapper<ContentVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_id", id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public void deleteContentVideoById(String id) {
        QueryWrapper<ContentVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_source_id", id);

        ContentVideo contentVideo = new ContentVideo();
        contentVideo.setVideoSourceId("");
        contentVideo.setVideoOriginalName("");

        baseMapper.update(contentVideo, queryWrapper);
    }
}