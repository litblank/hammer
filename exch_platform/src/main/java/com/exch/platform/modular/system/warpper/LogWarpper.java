
package com.exch.platform.modular.system.warpper;

import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.util.Contrast;
import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.util.Contrast;

import java.util.List;
import java.util.Map;

/**
 * 日志列表的包装类
 *
 */
public class LogWarpper extends BaseControllerWrapper {

    public LogWarpper(Map<String, Object> single) {
        super(single);
    }

    public LogWarpper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public LogWarpper(Page<Map<String, Object>> page) {
        super(page);
    }

    public LogWarpper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

    @Override
    protected void wrapTheMap(Map<String, Object> map) {
        String message = (String) map.get("message");

        Integer userid = (Integer) map.get("userid");
        map.put("userName", ConstantFactory.me().getUserNameById(userid));

        //如果信息过长,则只截取前100位字符串
        if (ToolUtil.isNotEmpty(message) && message.length() >= 100) {
            String subMessage = message.substring(0, 100) + "...";
            map.put("message", subMessage);
        }

        //如果信息中包含分割符号;;;   则分割字符串返给前台
        if (ToolUtil.isNotEmpty(message) && message.indexOf(Contrast.separator) != -1) {
            String[] msgs = message.split(Contrast.separator);
            map.put("regularMessage", msgs);
        } else {
            map.put("regularMessage", message);
        }
    }
}
