package com.exch.platform.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.core.log.LogObjectHolder;
import com.exch.platform.core.util.ToolUtil;
import com.exch.platform.modular.system.entity.NoticePower;
import com.exch.platform.modular.system.model.Role;
import com.exch.platform.modular.system.model.User;
import com.exch.platform.modular.system.service.INoticePowerService;
import com.exch.platform.modular.system.service.IRoleService;
import com.exch.platform.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 通知管理控制器
 *
 * @author chenyd
 * @Date 2019-01-15 15:50:18
 */
@Controller
@RequestMapping("/noticePower")
public class NoticePowerController extends BaseController {

    private String PREFIX = "/system/noticePower/";

    @Autowired
    private INoticePowerService noticePowerService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

    /**
     * 跳转到通知管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "noticePower.html";
    }

    /**
     * 跳转到添加通知管理
     */
    @RequestMapping("/noticePower_add")
    public String noticePowerAdd() {
        return PREFIX + "noticePower_add.html";
    }

    /**
     * 跳转到修改通知管理
     */
    @RequestMapping("/noticePower_update/{noticePowerId}")
    public String noticePowerUpdate(@PathVariable Integer noticePowerId, Model model) {
        NoticePower noticePower = noticePowerService.selectById(noticePowerId);
        model.addAttribute("item",noticePower);
        LogObjectHolder.me().set(noticePower);
        return PREFIX + "noticePower_edit.html";
    }

    /**
     * 获取通知管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return noticePowerService.selectList(null);
    }

    /**
     * 新增通知管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(NoticePower noticePower) {
        noticePowerService.insert(noticePower);
        return SUCCESS_TIP;
    }

    /**
     * 删除通知管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer noticePowerId) {
        noticePowerService.deleteById(noticePowerId);
        return SUCCESS_TIP;
    }

    /**
     * 修改通知管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(NoticePower noticePower) {
        noticePowerService.updateById(noticePower);
        return SUCCESS_TIP;
    }

    /**
     * 通知管理详情
     */
    @RequestMapping(value = "/detail/{noticePowerId}")
    @ResponseBody
    public Object detail(@PathVariable("noticePowerId") Integer noticePowerId) {
        return noticePowerService.selectById(noticePowerId);
    }

    /**
     * 根据通知id获取已授权的角色
     */
//    @Permission
    @RequestMapping(value = "/rolelist")
    @ResponseBody
    public Object rolelist(String noticeId,String rolename) {
        if(ToolUtil.isOneEmpty(noticeId)){
            return new Object();
        }
        //获取所有角色
        Wrapper<Role> wuser=null;
        if(StringUtils.isNotEmpty(rolename)){
            wuser=new EntityWrapper<>();
            wuser.like("name",rolename);
        }
        List<Map<String,Object>> rolesl = this.roleService.selectMaps(wuser);
        for (Map<String,Object> role:rolesl){
            String roleid=role.get("id").toString();
            if(StringUtils.isNotEmpty(roleid)){
                Wrapper<NoticePower> wrapper=new EntityWrapper<>();
                wrapper.eq("role_id",roleid);
                wrapper.eq("notice_id",noticeId);
                int cout=noticePowerService.selectCount(wrapper);
                if(cout>0){
                    role.put("state", true);
                }
            }
        }

        return rolesl;
    }
    /**
     * 根据通知id,角色ID 获取已授权的用户
     */
//    @Permission
    @RequestMapping(value = "/userlist")
    @ResponseBody
    public Object userlist(String noticeId,String roleId,String username) {
        if(ToolUtil.isOneEmpty(noticeId,roleId)){
            return new Object();
        }
        //获取所有用户
        Wrapper<User> wuser= new EntityWrapper<>();
        wuser.notLike("status","3",SqlLike.CUSTOM);
        wuser.and();
        wuser.leftNest();
        wuser.like("roleid",roleId.toString(), SqlLike.CUSTOM);
        wuser.or();
        wuser.like("roleid",roleId.toString()+",%", SqlLike.CUSTOM);
        wuser.or();
        wuser.like("roleid","%,"+roleId.toString()+",%", SqlLike.CUSTOM);
        wuser.or();
        wuser.like("roleid","%,"+roleId.toString(), SqlLike.CUSTOM);
        wuser.rightNest();
        if(StringUtils.isNotEmpty(username)){
            wuser.and();
            wuser.leftNest();
            wuser.like("name",username);
            wuser.or();
            wuser.like("account",username);
            wuser.rightNest();
        }
        List<Map<String,Object>> userl = this.userService.selectMaps(wuser);
        for (Map<String,Object> user:userl){
            String userid=user.get("id").toString();
            if(StringUtils.isNotEmpty(userid)){
                Wrapper<NoticePower> wrapper=new EntityWrapper<>();
                wrapper.eq("role_id",roleId);
                wrapper.eq("notice_id",noticeId);
                wrapper.eq("user_id",userid);
                int cout=noticePowerService.selectCount(wrapper);
                if(cout>0){
                    user.put("state", true);
                }
            }
        }

        return userl;
    }


    /**
     * 保存通知权限
     */
    @RequestMapping(value = "/updateNoticePower")
    @ResponseBody
    public Object updateNoticePower(String noticeId,String roleId,String userIds) {
        if(ToolUtil.isOneEmpty(noticeId,roleId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        noticePowerService.updateNoticePower(noticeId,roleId,userIds);
        return new Object();
    }


}
