
package com.exch.platform.core.metadata;

import com.exch.platform.core.shiro.ShiroKit;
import cn.stylefeng.roses.core.metadata.CustomMetaObjectHandler;
import com.exch.platform.core.shiro.ShiroKit;
import org.springframework.stereotype.Component;

/**
 * 字段填充器
 *
 */
@Component
public class ExchMpFieldHandler extends CustomMetaObjectHandler {

    @Override
    protected String getUserUniqueId() {
        try {

            return ShiroKit.getUser().getId().toString();

        } catch (Exception e) {

            //如果获取不到当前用户就存空id
            return "";
        }
    }
}
