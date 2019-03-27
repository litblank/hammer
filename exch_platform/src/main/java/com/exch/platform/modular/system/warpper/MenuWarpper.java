
package com.exch.platform.modular.system.warpper;

import com.exch.platform.core.common.constant.factory.ConstantFactory;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 菜单列表的包装类
 *
 */
public class MenuWarpper extends BaseControllerWrapper {

    public MenuWarpper(Map<String, Object> single) {
        super(single);
    }

    public MenuWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public MenuWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public MenuWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getMenuStatusName((Integer) map.get("status")));
        map.put("isMenuName", YesOrNotEnum.valueOf((Integer) map.get("ismenu")));
    }

}
