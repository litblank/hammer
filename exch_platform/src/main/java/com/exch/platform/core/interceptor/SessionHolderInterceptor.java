
package com.exch.platform.core.interceptor;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.HttpSessionContext;
import com.alibaba.fastjson.JSONObject;
import com.exch.platform.core.shiro.ShiroKit;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 静态调用session的拦截器
 *
 * 前置日志拦截记录
 */
@Aspect
@Component
public class SessionHolderInterceptor extends BaseController {

    private Logger log=LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.exch.platform.*..controller.*.*(..))")
    public void cutService() {
    }

    /**
     * 静态调用session的拦截器
     *
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {
        String error="";
        HttpSessionContext.put(super.getHttpServletRequest().getSession());
        try {
            return point.proceed();
        } catch (Exception e){
            throw e;
        } finally {
            HttpSessionContext.remove();
            HttpServletRequest request=getHttpServletRequest();
            HttpServletResponse response=getHttpServletResponse();
            String username=ShiroKit.getUser()!=null?ShiroKit.getUser().getName():"";
            String ip=ShiroKit.getSession().getHost()==null?"":ShiroKit.getSession().getHost();
            if(request!=null){
                log.info(String.format("^|^%s^|^%s^|^%s^|^%s^|^%s^|^%s",
                        ip, username,
                        request.getRequestURI(), JSONObject.toJSONString(request.getParameterMap()),
                        response.getStatus(),error));
            }
        }

    }
}
