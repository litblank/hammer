
package com.exch.platform.modular.system.warpper;

import com.exch.platform.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 角色列表的包装类
 *
 */
public class RoleWarpper extends BaseControllerWrapper {

    public RoleWarpper(Map<String, Object> single) {
        super(single);
    }

    public RoleWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public RoleWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public RoleWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("pName", ConstantFactory.me().getSingleRoleName((Integer) map.get("pid")));
        map.put("deptName", ConstantFactory.me().getDeptName((Integer) map.get("deptid")));
    }

}
