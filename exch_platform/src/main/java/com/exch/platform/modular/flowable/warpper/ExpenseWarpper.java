package com.exch.platform.modular.flowable.warpper;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.constant.state.ExpenseState;

import java.util.List;
import java.util.Map;

/**
 * 报销列表的包装
 *
 */
public class ExpenseWarpper extends BaseControllerWrapper {


    public ExpenseWarpper(Map<String, Object> single) {
        super(single);
    }

    public ExpenseWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public ExpenseWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public ExpenseWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        Integer state = (Integer) map.get("state");
        map.put("stateName", ExpenseState.valueOf(state));
    }
}
