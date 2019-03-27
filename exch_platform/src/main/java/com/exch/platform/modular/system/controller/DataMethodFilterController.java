package com.exch.platform.modular.system.controller;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.core.shiro.ShiroKit;
import com.exch.platform.modular.system.entity.DataSqlFilter;
import com.exch.platform.modular.system.service.IDataSqlFilterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.exch.platform.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.exch.platform.modular.system.entity.DataMethodFilter;
import com.exch.platform.modular.system.service.IDataMethodFilterService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * l拦截数据方法控制器
 *
 * @author chenyd
 * @Date 2019-01-09 17:05:52
 */
@Controller
@RequestMapping("/dataMethodFilter")
public class DataMethodFilterController extends BaseController {

    private String PREFIX = "/system/dataMethodFilter/";

    @Autowired
    private IDataMethodFilterService dataMethodFilterService;

    @Autowired
    private IDataSqlFilterService dataSqlFilterService;

    /**
     * 跳转到l拦截数据方法首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dataMethodAndSql.html";
    }

    /**
     * 跳转到添加l拦截数据方法
     */
    @RequestMapping("/dataMethodFilter_add")
    public String dataMethodFilterAdd() {
        return PREFIX + "dataMethodFilter_add.html";
    }

    /**
     * 跳转到修改l拦截数据方法
     */
    @RequestMapping("/dataMethodFilter_update/{dataMethodFilterId}")
    public String dataMethodFilterUpdate(@PathVariable Integer dataMethodFilterId, Model model) {
        DataMethodFilter dataMethodFilter = dataMethodFilterService.selectById(dataMethodFilterId);
        model.addAttribute("item",dataMethodFilter);
        LogObjectHolder.me().set(dataMethodFilter);
        return PREFIX + "dataMethodFilter_edit.html";
    }

    /**
     * 获取l拦截数据方法列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<DataMethodFilter> wrapper=new EntityWrapper<>();
        wrapper.like("method_name",condition);
        wrapper.or();
        wrapper.like("method",condition);
        return dataMethodFilterService.selectList(wrapper);
    }

    /**
     * 新增l拦截数据方法
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(DataMethodFilter dataMethodFilter) {
        dataMethodFilter.setCreaterId(ShiroKit.getUser().getId());
        dataMethodFilter.setCreaterDate(new Date());
        dataMethodFilterService.insert(dataMethodFilter);
        return SUCCESS_TIP;
    }

    /**
     * 删除l拦截数据方法
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer dataMethodFilterId) {
        dataMethodFilterService.deleteById(dataMethodFilterId);
        return SUCCESS_TIP;
    }

    /**
     * 修改l拦截数据方法
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(DataMethodFilter dataMethodFilter) {
        dataMethodFilter.setModifyId(ShiroKit.getUser().getId());
        dataMethodFilter.setModifyDate(new Date());
        dataMethodFilterService.updateById(dataMethodFilter);
        return SUCCESS_TIP;
    }

    /**
     * l拦截数据方法详情
     */
    @RequestMapping(value = "/detail/{dataMethodFilterId}")
    @ResponseBody
    public Object detail(@PathVariable("dataMethodFilterId") Integer dataMethodFilterId) {
        return dataMethodFilterService.selectById(dataMethodFilterId);
    }


    /**
     * 根据角色获取方法和SQL列表
     */
    @RequestMapping(value = "/methodAndSqllist")
    @ResponseBody
    public Object methodAndSqllist(String roleId) {
        if(StringUtils.isEmpty(roleId)){
            return new Object();
        }
        Wrapper<DataMethodFilter> wrapper=new EntityWrapper<>();
        List<Map<String, Object>>  listobj=dataMethodFilterService.selectMaps(wrapper);

        for(Map methodmap:listobj){
            Wrapper<DataSqlFilter> warpper=new EntityWrapper<>();
            warpper.eq("method_id",methodmap.get("id"));
            List<Map<String, Object>>  objl=dataSqlFilterService.selectMaps(warpper);
            for(Map<String, Object> map : objl){
                String roleids=map.get("roleId")+"";
                if(StringUtils.isNotEmpty(roleids)){
                    String[] roleli=roleids.split(",");
                    for(String ro: roleli){
                        if(ro.equals(roleId)){
                            map.put("select","true");
                            break;
                        }
                    }
                }
                methodmap.put("sqllist", JSON.toJSON(objl));
            }
        }

        return listobj;
    }


}
