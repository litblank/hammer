
package com.exch.platform.modular.system.service;

import com.exch.platform.core.common.node.ZTreeNode;
import com.exch.platform.modular.system.model.Dept;
import com.baomidou.mybatisplus.service.IService;
import com.exch.platform.core.common.node.ZTreeNode;

import java.util.List;
import java.util.Map;

/**
 * 部门服务
 *
 */
public interface IDeptService extends IService<Dept> {

    /**
     * 删除部门
     */
    void deleteDept(Integer deptId);

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取所有部门列表
     */
    List<Map<String, Object>> list(String condition);
}
