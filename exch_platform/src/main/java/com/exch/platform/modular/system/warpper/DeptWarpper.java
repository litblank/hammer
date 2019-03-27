
package com.exch.platform.modular.system.warpper;

import com.exch.platform.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 部门列表的包装
 *
 */
public class DeptWarpper extends BaseControllerWrapper {

    public DeptWarpper(Map<String, Object> single) {
        super(single);
    }

    public DeptWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public DeptWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public DeptWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Integer pid = (Integer) map.get("pid");

        if (ToolUtil.isEmpty(pid) || pid.equals(0)) {
            map.put("pName", "--");
        } else {
            map.put("pName", ConstantFactory.me().getDeptName(pid));
        }

        String orgtype=(String) map.get("org_type");
        if(StringUtils.isNotEmpty(orgtype)){
            map.put("org_type_name",ConstantFactory.me().getDictsByName("机构类型", Integer.parseInt(orgtype)));
        }
    }
}
