
package com.exch.platform.modular.system.controller;

import com.exch.platform.core.common.annotion.BussinessLog;
import com.exch.platform.core.common.annotion.Permission;
import com.exch.platform.core.common.constant.Const;
import com.exch.platform.core.common.constant.factory.PageFactory;
import com.exch.platform.core.common.page.PageInfoBT;
import com.exch.platform.modular.system.model.LoginLog;
import com.exch.platform.modular.system.service.ILoginLogService;
import com.exch.platform.modular.system.warpper.LogWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import com.exch.platform.core.common.annotion.BussinessLog;
import com.exch.platform.core.common.annotion.Permission;
import com.exch.platform.core.common.constant.Const;
import com.exch.platform.core.common.constant.factory.PageFactory;
import com.exch.platform.core.common.page.PageInfoBT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 */
@Controller
@RequestMapping("/loginLog")
public class LoginLogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Autowired
    private ILoginLogService loginLogService;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "login_log.html";
    }

    /**
     * 查询登录日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName) {
        Page<LoginLog> page = new PageFactory<LoginLog>().defaultPage();
        List<Map<String, Object>> result = loginLogService.getLoginLogs(page, beginTime, endTime, logName, page.getOrderByField(), page.isAsc());
        page.setRecords(new LogWarpper(result).wrap());
        return new PageInfoBT<>(page);
    }

    /**
     * 清空日志
     */
    @BussinessLog("清空登录日志")
    @RequestMapping("/delLoginLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from sys_login_log");
        return SUCCESS_TIP;
    }
}
