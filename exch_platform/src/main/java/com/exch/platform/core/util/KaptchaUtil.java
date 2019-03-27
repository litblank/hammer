
package com.exch.platform.core.util;

import com.exch.platform.config.properties.ExchProperties;
import cn.stylefeng.roses.core.util.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(ExchProperties.class).getKaptchaOpen();
    }
}