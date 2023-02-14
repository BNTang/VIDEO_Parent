package top.it6666.service_cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.it6666.service_cms.entity.Banner;
import top.it6666.service_cms.mapper.BannerMapper;
import top.it6666.service_cms.service.BannerService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author BNTang
 * @since 2021-05-15
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<Banner> selectAllBanner() {
        // 根据id进行降序排列，显示排列之后前两条记录
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        // last方法，拼接sql语句
        wrapper.last("limit 3");
        return baseMapper.selectList(null);
    }
}
