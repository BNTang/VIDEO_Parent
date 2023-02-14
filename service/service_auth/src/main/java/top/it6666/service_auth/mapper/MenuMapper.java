package top.it6666.service_auth.mapper;

import top.it6666.service_auth.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单权限 Mapper 接口
 * </p>
 *
 * @author BNTang
 * @since 2021-04-21
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询所有的菜单
     *
     * @return 菜单信息
     */
    List<String> selectAllMenuValue();

    /**
     * 根据用户ID查询用户菜单权限
     *
     * @param id 用户ID
     * @return 菜单权限
     */
    List<String> selectMenuValueByUserId(String id);

    /**
     * 根据用户id获取菜单权限
     *
     * @param id 用户id
     * @return 菜单权限信息数据
     */
    List<Menu> selectMenuByUserId(String id);
}
