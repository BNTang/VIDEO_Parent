package top.it6666.service_video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import top.it6666.service_video.entity.Author;
import top.it6666.service_video.entity.vo.AuthorQueryVO;
import top.it6666.service_video.mapper.AuthorMapper;
import top.it6666.service_video.service.AuthorService;

import java.util.Objects;

/**
 * <p>
 * 创作者 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-03-27
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Override
    public void pageQuery(Page<Author> pageInfo, AuthorQueryVO authorQueryVO) {
        QueryWrapper<Author> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByAsc("sort");
        if (Objects.isNull(authorQueryVO)) {
            // 没有条件
            baseMapper.selectPage(pageInfo, queryWrapper);
            return;
        }

        // 有条件，判断条件是否为空
        if (!StringUtils.isEmpty(authorQueryVO.getName())) {
            // 模糊查询 name
            queryWrapper.like("name", authorQueryVO.getName());
        }

        if (!Objects.isNull(authorQueryVO.getLevel())) {
            queryWrapper.eq("level", authorQueryVO.getLevel());
        }

        // 查询创建的时间大于等于开始时间
        if (!StringUtils.isEmpty(authorQueryVO.getBegin())) {
            queryWrapper.ge("gmt_create", authorQueryVO.getBegin());
        }

        // 查询创建的时间在小于等于结束时间
        if (!StringUtils.isEmpty(authorQueryVO.getEnd())) {
            queryWrapper.le("gmt_create", authorQueryVO.getEnd());
        }
        baseMapper.selectPage(pageInfo, queryWrapper);
    }
}
