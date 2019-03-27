package com.exch.platform.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.core.shiro.ShiroKit;
import com.exch.platform.core.util.ToolUtil;
import com.exch.platform.modular.system.service.IDataMethodFilterService;
import io.swagger.models.auth.In;
import org.abego.treelayout.internal.util.java.lang.string.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.exch.platform.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.exch.platform.modular.system.entity.DataSqlFilter;
import com.exch.platform.modular.system.service.IDataSqlFilterService;

import java.util.*;

/**
 * 拦截数据SQL控制器
 *
 * @author fengshuonan
 * @Date 2019-01-09 17:08:44
 */
@Controller
@RequestMapping("/dataSqlFilter")
public class DataSqlFilterController extends BaseController {

    private String PREFIX = "/system/dataSqlFilter/";

    @Autowired
    private IDataSqlFilterService dataSqlFilterService;

    @Autowired
    private IDataMethodFilterService dataMethodFilterService;

    /**
     * 跳转到拦截数据SQL首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "roleMethodSql.html";
    }

    /**
     * 跳转到添加拦截数据SQL
     */
    @RequestMapping("/dataSqlFilter_add")
    public String dataSqlFilterAdd(String roleId,String methodId,Model model) {
        model.addAttribute("roleId",roleId);
        model.addAttribute("methodId",methodId);
        return PREFIX + "dataSqlFilter_add.html";
    }

    /**
     * 跳转到修改拦截数据SQL
     */
    @RequestMapping("/dataSqlFilter_update/{dataSqlFilterId}")
    public String dataSqlFilterUpdate(@PathVariable Integer dataSqlFilterId, Model model) {
        DataSqlFilter dataSqlFilter = dataSqlFilterService.selectById(dataSqlFilterId);
        model.addAttribute("item",dataSqlFilter);
        LogObjectHolder.me().set(dataSqlFilter);
        return PREFIX + "dataSqlFilter_edit.html";
    }

    /**
     * 获取拦截数据SQL列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String methodId) {
        if(StringUtils.isNotEmpty(methodId)){
            Wrapper<DataSqlFilter> warpper=new EntityWrapper<>();
            warpper.eq("method_id",methodId);
            return dataSqlFilterService.selectList(warpper);
        }else{
            return new ArrayList<>();
        }

    }

    /**
     * 新增拦截数据SQL
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DataSqlFilter dataSqlFilter) {
        dataSqlFilter.setCreaterId(ShiroKit.getUser().getId());
        dataSqlFilter.setCreaterDate(new Date());
        dataSqlFilterService.insert(dataSqlFilter);
        return SUCCESS_TIP;
    }

    /**
     * 删除拦截数据SQL
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer dataSqlFilterId) {
        dataSqlFilterService.deleteById(dataSqlFilterId);
        return SUCCESS_TIP;
    }

    /**
     * 修改拦截数据SQL
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DataSqlFilter dataSqlFilter) {
        dataSqlFilter.setModifyId(ShiroKit.getUser().getId());
        dataSqlFilter.setModifyDate(new Date());
        dataSqlFilter.setFilterSql(dataSqlFilter.getFilterSql().replaceAll("& #39;","'"));
        dataSqlFilterService.updateById(dataSqlFilter);
        return SUCCESS_TIP;
    }

    /**
     * 拦截数据SQL详情
     */
    @RequestMapping(value = "/detail/{dataSqlFilterId}")
    @ResponseBody
    public Object detail(@PathVariable("dataSqlFilterId") Integer dataSqlFilterId) {
        return dataSqlFilterService.selectById(dataSqlFilterId);
    }

    /**
     * 修改角色对应得SQL
     * @param roleId 角色ID
     * @param sqlId 改变后得SQLID
     * @param methodId 方法名ID
     * @return
     */
    @RequestMapping(value = "/updateRoleSQL")
    @ResponseBody
    public Object updateRoleSQL(String roleId,String sqlId,String methodId) {
        if(ToolUtil.isOneEmpty(roleId,methodId)){
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Wrapper<DataSqlFilter> wrappersql=new EntityWrapper<>();
        wrappersql.eq("method_id",methodId);
        List<DataSqlFilter>  sqlList=dataSqlFilterService.selectList(wrappersql);
        for(DataSqlFilter obj:sqlList){
            String o_roleid=obj.getRoleId();
            if(StringUtils.isNotEmpty(o_roleid)){
                boolean flag=false;
                String[] o_rolel=o_roleid.split(",");
                String n_rolel="";
                for(String roleone:o_rolel){
                    if(roleone.equals(roleId)){
                        flag=true;
                    }else{
                        n_rolel+=roleone+",";
                    }
                }
                if(flag){
                    obj.setRoleId(n_rolel);
                    dataSqlFilterService.updateById(obj);
                }
            }

            if(sqlId.equals(obj.getId()+"")){
                String n_rol=StringUtils.isEmpty(obj.getRoleId())?"":obj.getRoleId();
                n_rol+=roleId+",";

                String parm="";
                String[] sorl=n_rol.split(",");
                int[] intl=new int[sorl.length];
                for(int i=0;i<sorl.length;i++){
                    intl[i]=Integer.parseInt(sorl[i]);
                }
                Arrays.sort(intl);

                for(int i:intl){
                    parm+=i+",";
                }

                obj.setRoleId(parm);
                dataSqlFilterService.updateById(obj);
            }
        }
        return SUCCESS_TIP;
    }

}
