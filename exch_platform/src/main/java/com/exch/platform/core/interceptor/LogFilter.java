package com.exch.platform.core.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.exch.platform.core.shiro.ShiroKit;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问control 记录访问路径
 *
 * @Author: chenyadong
 * @Date: 2019/2/28 16:06
 * @Version 1.0
 */
@Component
@WebFilter(urlPatterns = "/*",filterName = "logFilter")
public class LogFilter  implements Filter {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            String uri = ((HttpServletRequest) servletRequest).getRequestURI();
            if (StringUtils.isNotEmpty(uri)) {
                String[] str=uri.split("/");
                //静态资源不记录日志
                if(str.length>=3 && !str[2].equals("static")){

                    loginfo((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }


    private void loginfo(HttpServletRequest request, HttpServletResponse servletResponse) {
        HttpServletResponse response= servletResponse;
//        String username=ShiroKit.getUser()!=null?ShiroKit.getUser().getName():"";
//        String ip=ShiroKit.getSession().getHost()==null?"":ShiroKit.getSession().getHost();
        if(request!=null){
            JSONObject json=new JSONObject();
//            json.put("ip",ip);
//            json.put("user",username);
            json.put("uri",request.getRequestURI());
            json.put("parameterMap",JSONObject.toJSONString(request.getParameterMap()));
//            json.put("responseStatus",response.getStatus());
            log.info("ASK>"+json.toJSONString());
        }
    }

}
