
package com.exch.platform.core.common.constant.dictmap.factory;

import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.common.constant.factory.IConstantFactory;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.exch.platform.core.common.constant.factory.ConstantFactory;

import java.lang.reflect.Method;

/**
 * 字典字段的包装器(从ConstantFactory中获取包装值)
 *
 */
public class DictFieldWarpperFactory {

    public static Object createFieldWarpper(Object parameter, String methodName) {
        IConstantFactory constantFactory = ConstantFactory.me();
        try {
            Method method = IConstantFactory.class.getMethod(methodName, parameter.getClass());
            return method.invoke(constantFactory, parameter);
        } catch (Exception e) {
            try {
                Method method = IConstantFactory.class.getMethod(methodName, Integer.class);
                return method.invoke(constantFactory, Integer.parseInt(parameter.toString()));
            } catch (Exception e1) {
                throw new ServiceException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
            }
        }
    }

}
