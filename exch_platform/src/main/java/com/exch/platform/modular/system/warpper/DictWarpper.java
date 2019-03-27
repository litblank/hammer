
package com.exch.platform.modular.system.warpper;

import cn.hutool.core.util.StrUtil;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.modular.system.model.Dict;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.modular.system.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 字典列表的包装
 *
 */
public class DictWarpper extends BaseControllerWrapper {


    public DictWarpper(Map<String, Object> single) {
        super(single);
    }

    public DictWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public DictWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public DictWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
//        Dict dl=dictService.selectById(Integer.valueOf(map.get("pid").toString()));
//        map.put("nametype",dl.getName());
//        map.put("codetype",dl.getCode());
    }
}
