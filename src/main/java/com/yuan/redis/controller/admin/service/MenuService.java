package com.yuan.redis.controller.admin.service;


import com.yuan.redis.controller.admin.pojo.Menu;
import com.yuan.redis.controller.admin.vo.MenuNodeVO;
import com.yuan.redis.controller.admin.vo.MenuVO;

import java.util.List;

/**
 *
 * @Date 2020/3/10 11:55
 * @Version 1.0
 **/
public interface MenuService {
    /**
     * 获取菜单树
     * @return
     */
    List<MenuNodeVO> findMenuTree();

    /**
     * 添加菜单
     * @param menuVO
     */
    Menu add(MenuVO menuVO);

    /**
     * 删除节点
     * @param id
     */
    void delete(Long id);

    /**
     * 编辑节点
     * @param id
     * @return
     */
    MenuVO edit(Long id);

    /**
     * 更新节点
     * @param id
     */
    void update(Long id, MenuVO menuVO);

    /**
     * 所有展开菜单的ID
     * @return
     */
    List<Long> findOpenIds();

    /**
     * 角色授权
     * @param mids
     */
    void authority(Long id, Long[] mids);
}
