package com.exch.platform.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.exch.platform.modular.system.entity.DataSqlFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.exch.platform.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.exch.platform.modular.system.entity.Prop;
import com.exch.platform.modular.system.service.IPropService;


/**
 * 公共参数名称控制器
 *
 * @author
 * @Date 2019-01-11 14:52:39
 */
@Controller
@RequestMapping("/prop")
public class PropController extends BaseController {

    private String PREFIX = "/system/prop/";

    @Autowired
    private IPropService propService;

    /**
     * 跳转到公共参数名称首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "prop.html";
    }

    /**
     * 跳转到添加公共参数名称
     */
    @RequestMapping("/prop_add")
    public String propAdd() {
        return PREFIX + "prop_add.html";
    }

    /**
     * 跳转到修改公共参数名称
     */
    @RequestMapping("/prop_update/{propId}")
    public String propUpdate(@PathVariable Integer propId, Model model) {
        Prop prop = propService.selectById(propId);
        model.addAttribute("item",prop);
        LogObjectHolder.me().set(prop);
        return PREFIX + "prop_edit.html";
    }

    /**
     * 获取公共参数名称列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<Prop> wrapper=new EntityWrapper<>();
        wrapper.like("prop_name",condition);
        wrapper.or();
        wrapper.like("prop_desc",condition);
        wrapper.or();
        wrapper.like("prop_value",condition);
        return propService.selectList(wrapper);
    }

    /**
     * 新增公共参数名称
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Prop prop) {
        prop.setAppId("62");
        prop.setVersion(0); //设置默认值
        propService.insert(prop);
        return SUCCESS_TIP;
    }

    /**
     * 删除公共参数名称
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer propId) {
        propService.deleteById(propId);
        return SUCCESS_TIP;
    }

    /**
     * 修改公共参数名称
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Prop prop) {
        propService.updateById(prop);
        return SUCCESS_TIP;
    }

    /**
     * 公共参数名称详情
     */
    @RequestMapping(value = "/detail/{propId}")
    @ResponseBody
    public Object detail(@PathVariable("propId") Integer propId) {
        return propService.selectById(propId);
    }
}
