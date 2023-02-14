package top.it6666.service_video.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import top.it6666.common_base.exception.BnTangException;
import top.it6666.service_video.entity.Chapter;
import top.it6666.service_video.entity.ContentVideo;
import top.it6666.service_video.entity.vo.ChapterVO;
import top.it6666.service_video.entity.vo.ContentVideoVO;
import top.it6666.service_video.mapper.ChapterMapper;
import top.it6666.service_video.service.ChapterService;
import top.it6666.service_video.service.ContentVideoService;

/**
 * <p>
 * 作品章节 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-04-08
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Resource
    private ContentVideoService contentVideoService;

    @Override
    public List<ChapterVO> getChapterSection(String contentId) {
        // 1.根据课程id查询课程里面所有的章节(章节)
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("content_id", contentId);
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        // 2.根据课程id查询课程里面所有的小节(小节)
        QueryWrapper<ContentVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("content_id", contentId);
        List<ContentVideo> contentVideoList = contentVideoService.list(videoQueryWrapper);

        return chapterList.stream().map(m -> {
            ChapterVO chapterVO = new ChapterVO();

            // chapter对象值复制到ChapterVo里面
            BeanUtils.copyProperties(m, chapterVO);

            // 创建集合，用于封装章节的小节
            List<ContentVideoVO> subsectionList = new ArrayList<>();

            // 4.遍历查询小节list集合，进行封装
            contentVideoList.forEach(a1 -> {
                // 判断：小节里面chapterId和章节里面id是否一样
                if (a1.getChapterId().equals(m.getId())) {
                    // 进行封装
                    ContentVideoVO videoVo = new ContentVideoVO();
                    BeanUtils.copyProperties(a1, videoVo);

                    // 放到小节封装集合
                    subsectionList.add(videoVo);
                }
            });

            // 把封装之后小节list集合，放到章节对象里面
            chapterVO.setChildren(subsectionList);
            return chapterVO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteChapterById(String id) {
        // 根据 id 查询是否存在小节，如果有则提示用户，有子节点
        if (contentVideoService.isExistContentVideoWithChapterId(id)) {
            throw new BnTangException(20001, "该分章节下存在小节，请先删除小节!");
        }
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteChapterWithContentId(String id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_id", id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<ChapterVO> getChapterContentVideo(String contentId) {
        // 1.根据作品的id查询所有的作品章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("content_id", contentId);

        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        // 2.根据作品的id查询所有的作品小节
        QueryWrapper<ContentVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("content_id", contentId);
        List<ContentVideo> contentVideoList = contentVideoService.list(queryWrapper);

        // 3.封装数据
        List<ChapterVO> resList = new ArrayList<>();

        // 遍历所有的章节信息
        chapterList.forEach(chapter -> {
            // 把章节信息转成ChapterVO
            ChapterVO chapterVO = new ChapterVO();
            BeanUtils.copyProperties(chapter, chapterVO);

            // chapterVO封装小节数据
            List<ContentVideoVO> children = new ArrayList<>();

            contentVideoList.forEach(contentVideo -> {
                if (contentVideo.getChapterId().equals(chapter.getId())) {
                    // 是当前章节的小节
                    ContentVideoVO contentVideoVO = new ContentVideoVO();
                    BeanUtils.copyProperties(contentVideo, contentVideoVO);
                    children.add(contentVideoVO);
                }
            });

            chapterVO.setChildren(children);
            resList.add(chapterVO);
        });
        return resList;
    }
}