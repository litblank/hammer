
package com.exch.platform.core.common.treebuild;

import java.util.List;

/**
 * 构造树节点的接口规范
 *
 * @author fengshuonan
 * @date 2018-07-25-下午5:59
 */
public interface Tree {

    /**
     * 获取节点id
     */
    String getNodeId();

    /**
     * 获取节点父id
     */
    String getNodeParentId();

    /**
     * 设置children
     */
    void setChildrenNodes(List childrenNodes);
}
