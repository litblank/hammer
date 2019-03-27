
package com.exch.platform.modular.system.service;

import com.exch.platform.core.common.node.MenuNode;
import com.exch.platform.core.common.node.ZTreeNode;
import com.exch.platform.modular.system.model.Menu;
import com.baomidou.mybatisplus.service.IService;
import com.exch.platform.core.common.node.MenuNode;
import com.exch.platform.core.common.node.ZTreeNode;

import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 删除菜单
     *
     */
    void delMenu(Long menuId);

    /**
     * 删除菜单包含所有子菜单
     *
     */
    void delMenuContainSubMenus(Long menuId);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(String condition, String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Long> getMenuIdsByRoleId(Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(Long menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNode> getMenusByRoleIds(List<Integer> roleIds);
}
