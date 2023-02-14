package top.it6666.service_video.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.it6666.common_utils.entity.ContentWebVO;
import top.it6666.service_video.entity.Content;
import top.it6666.service_video.entity.ContentDescription;
import top.it6666.service_video.entity.vo.ContentPreviewVO;
import top.it6666.service_video.entity.vo.ContentVO;
import top.it6666.service_video.mapper.ContentMapper;
import top.it6666.service_video.service.ChapterService;
import top.it6666.service_video.service.ContentDescriptionService;
import top.it6666.service_video.service.ContentService;
import top.it6666.service_video.service.ContentVideoService;

/**
 * <p>
 * 作品表 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {
    /**
     * 注入简介的业务 保存简介
     */
    @Resource
    private ContentDescriptionService contentDescriptionService;
    @Resource
    private ChapterService chapterService;
    @Resource
    private ContentVideoService contentVideoService;

    @Override
    public String saveContentInfo(ContentVO contentVO) {
        // 1.保存基本信息
        Content content = new Content();
        BeanUtils.copyProperties(contentVO, content);
        // 1.1.保存到数据当中
        baseMapper.insert(content);

        // 2.保存简介
        // 2.1.获取刚保存基本信息的id,之后再设置自己的简介id
        String id = content.getId();
        ContentDescription contentDescription = new ContentDescription();
        contentDescription.setId(id);
        contentDescription.setDescription(contentVO.getDescription());
        contentDescriptionService.save(contentDescription);
        return id;
    }

    @Override
    public ContentVO getContentWithInfoId(String id) {
        ContentVO contentVO = new ContentVO();
        // 查询基本信息
        Content content = baseMapper.selectById(id);
        BeanUtils.copyProperties(content, contentVO);

        // 查询简介信息
        ContentDescription description = contentDescriptionService.getById(id);
        contentVO.setDescription(description.getDescription());
        return contentVO;
    }

    @Override
    public void updateContentInfo(ContentVO contentVO) {
        // 1.修改基本信息
        Content content = new Content();
        BeanUtils.copyProperties(contentVO, content);
        baseMapper.updateById(content);

        // 2.修改描述信息
        ContentDescription contentDescription = new ContentDescription();
        contentDescription.setId(content.getId());
        contentDescription.setDescription(contentVO.getDescription());
        contentDescriptionService.updateById(contentDescription);
    }

    @Override
    public ContentPreviewVO getContentPreView(String id) {
        return baseMapper.getContentPreviewVoById(id);
    }

    @Override
    public void sendContentWithId(String id) {
        Content content = new Content();
        content.setId(id);
        content.setStatus("Normal");
        baseMapper.updateById(content);
    }

    @Override
    public void contentPageQuery(Page<Content> pageParam, ContentVO contentVO) {
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (contentVO == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        if (!StringUtils.isEmpty(contentVO.getTitle())) {
            queryWrapper.like("title", contentVO.getTitle());
        }

        if (!StringUtils.isEmpty(contentVO.getAuthorId())) {
            queryWrapper.eq("author_id", contentVO.getAuthorId());
        }

        if (!StringUtils.isEmpty(contentVO.getCategoryParentId())) {
            queryWrapper.eq("category_parent_id", contentVO.getCategoryParentId());
        }

        if (!StringUtils.isEmpty(contentVO.getCategoryId())) {
            queryWrapper.eq("category_id", contentVO.getCategoryId());
        }
        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public void deleteContentWithId(String id) {
        // 根据作品的id删除作品
        // 1.作品章节
        chapterService.deleteChapterWithContentId(id);

        // 2.作品小节视频
        contentVideoService.deleteContentVideoByContentId(id);

        // 3.删除信息
        baseMapper.deleteById(id);
    }

    @Override
    public List<Content> selectByAuthorId(String authorId) {
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("author_id", authorId);

        // 按照最后更新时间倒序排列
        queryWrapper.orderByDesc("gmt_modified");

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public ContentWebVO selectContentDetailById(String contentId) {
        this.updatePageViewCount(contentId);
        return baseMapper.getContentDetailById(contentId);
    }

    /**
     * 更新播放信息
     */
    @Override
    public void updatePageViewCount(String id) {
        Content content = baseMapper.selectById(id);
        content.setViewCount(content.getViewCount() + 1);
        baseMapper.updateById(content);
    }
}