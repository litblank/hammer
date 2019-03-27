package com.exch.platform.modular.system.controller;


import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.exch.platform.core.common.annotion.Permission;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.modular.system.model.User;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
@RequestMapping("/usersession")
public class UserSessionController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static String PREFIX = "/system/user/";

    @Autowired
    private DefaultWebSessionManager sessionManager;


    @RequestMapping("")
    public String index() {
        return PREFIX + "active_user.html";
    }


//    @Permission
    @RequestMapping("/getActiveUser")
    @ResponseBody
    public Object getActiveUser() {
        Collection<Session> sessionAll=sessionManager.getSessionDAO().getActiveSessions();
        JSONArray json=new JSONArray();
        for(Session se:sessionAll){
            JSONObject js=new JSONObject();
            js.put("ip",se.getHost());
            js.put("user",se.getAttribute("username"));
            js.put("operation",se.getLastAccessTime());
            json.add(js);
        }
        return json;
    }

}
