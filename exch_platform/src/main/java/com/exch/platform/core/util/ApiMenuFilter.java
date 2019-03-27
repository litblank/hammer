
package com.exch.platform.core.util;

import cn.stylefeng.roses.core.util.SpringContextHolder;
import com.exch.platform.config.properties.ExchProperties;
import com.exch.platform.core.common.constant.Const;
import com.exch.platform.core.common.node.MenuNode;

import java.util.ArrayList;
import java.util.List;

/**
 * api接口文档显示过滤
 *
 */
public class ApiMenuFilter extends MenuNode {

    public static List<MenuNode> build(List<MenuNode> nodes) {

        //如果关闭了接口文档,则不显示接口文档菜单
        ExchProperties exchProperties = SpringContextHolder.getBean(ExchProperties.class);
        if (!exchProperties.getSwaggerOpen()) {
            List<MenuNode> menuNodesCopy = new ArrayList<>();
            for (MenuNode menuNode : nodes) {
                if (Const.API_MENU_NAME.equals(menuNode.getName())) {
                    continue;
                } else {
                    menuNodesCopy.add(menuNode);
                }
            }
            nodes = menuNodesCopy;
        }

        return nodes;
    }
}
