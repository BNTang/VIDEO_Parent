package top.it6666.service_cms.service;

import top.it6666.service_cms.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author BNTang
 * @since 2021-05-15
 */
public interface BannerService extends IService<Banner> {

    /**
     * 查询所有banner
     *
     * @return banner信息
     */
    List<Banner> selectAllBanner();
}
