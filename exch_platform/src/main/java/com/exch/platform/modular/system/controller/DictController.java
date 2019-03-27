
package com.exch.platform.modular.system.controller;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.exch.platform.core.common.annotion.BussinessLog;
import com.exch.platform.core.common.annotion.Permission;
import com.exch.platform.core.common.constant.Const;
import com.exch.platform.core.common.constant.dictmap.DictMap;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.core.log.LogObjectHolder;
import com.exch.platform.modular.system.model.Dict;
import com.exch.platform.modular.system.service.IDictService;
import com.exch.platform.modular.system.warpper.DictWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.exch.platform.core.common.annotion.BussinessLog;
import com.exch.platform.core.common.annotion.Permission;
import com.exch.platform.core.common.constant.Const;
import com.exch.platform.core.common.constant.dictmap.DictMap;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.log.LogObjectHolder;
import com.exch.platform.modular.system.warpper.DictWarpper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典控制器
 *
 */
@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {

    private String PREFIX = "/system/dict/";

    @Autowired
    private IDictService dictService;

    /**
     * 跳转到字典管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "dict.html";
    }

    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add")
    public String deptAdd() {
        return PREFIX + "dict_add.html";
    }
    /**
     * 跳转到详情字典
     */
    @RequestMapping("/dict_detail/{dictId}")
    public String dictDetail(@PathVariable Integer dictId, Model model) {
        Dict dict = dictService.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictService.selectList(new EntityWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_detail.html";
    }
    /**
     * 跳转到添加字典
     */
    @RequestMapping("/dict_add_dict/{dictId}")
    public String dictAddType(@PathVariable Integer dictId, Model model) {
        Dict dict = dictService.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictService.selectList(new EntityWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_add_dict.html";
    }

    /**
     * 跳转到修改字典
     */
    @Permission(Const.ADMIN_NAME)
    @RequestMapping("/dict_edit/{dictId}")
    public String deptUpdate(@PathVariable Integer dictId, Model model) {
        Dict dict = dictService.selectById(dictId);
        model.addAttribute("dict", dict);
        List<Dict> subDicts = dictService.selectList(new EntityWrapper<Dict>().eq("pid", dictId));
        model.addAttribute("subDicts", subDicts);
        LogObjectHolder.me().set(dict);
        return PREFIX + "dict_edit.html";
    }

    /**
     * 新增字典
     *
     * @param dictValues 格式例如   "1:启用;2:禁用;3:冻结"
     */
    @BussinessLog(value = "添加字典记录", key = "dictName,dictValues", dict = DictMap.class)
    @RequestMapping(value = "/add")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object add(String dictCode, String dictTips, String dictName, String dictValues) {
        if (ToolUtil.isOneEmpty(dictCode, dictName, dictValues)) {
            throw new ServiceException(BizExceptionEnum.ERROR_DICT_EMPTY);
        }
        this.dictService.addDict(dictCode, dictName, dictTips, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 获取所有字典列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.dictService.list(condition);
        return super.warpObject(new DictWarpper(list));
    }

    /**
     * 获取数据字典类别
     */
    @RequestMapping(value = "/listType")
//    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object listType(String codeType,String nameType,String tipsTpye) {
        Wrapper<Dict> wrapper=new EntityWrapper<>();
        wrapper.eq("pid","0");
        if(StringUtils.isNotEmpty(codeType)){
            wrapper.like("code",codeType);
        }
        if(StringUtils.isNotEmpty(nameType)){
            wrapper.like("name",nameType);
        }
        if(StringUtils.isNotEmpty(tipsTpye)){
            wrapper.like("tips",tipsTpye);
        }
        List<Map<String, Object>> list = this.dictService.selectMaps(wrapper);
        return super.warpObject(new DictWarpper(list));
    }

    /**
     * 获取数据字典列表
     */
    @RequestMapping(value = "/listDict")
//    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object listDict(String tips_dic,String pid_dic,String name_dic,String code_dic) {

        HashMap parm=new HashMap<String,String>();
        if(StringUtils.isNotEmpty(tips_dic)){
            parm.put("tips_dic",tips_dic);
        }
        if(StringUtils.isNotEmpty(pid_dic)){
            parm.put("pid_dic",pid_dic);
        }
        if(StringUtils.isNotEmpty(name_dic)){
            parm.put("name_dic",name_dic);
        }
        if(StringUtils.isNotEmpty(code_dic)){
            parm.put("code_dic",code_dic);
        }
        List<Map<String, Object>> list = this.dictService.listDict(parm);
        return super.warpObject(new DictWarpper(list));
    }



    /**
     * 字典详情
     */
    @RequestMapping(value = "/detail/{dictId}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable("dictId") Integer dictId) {
        return dictService.selectById(dictId);
    }

    /**
     * 修改字典
     */
    @BussinessLog(value = "修改字典", key = "dictName,dictValues", dict = DictMap.class)
    @RequestMapping(value = "/update")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object update(Integer dictId, String dictCode, String dictName, String dictTips, String dictValues) {
        if (ToolUtil.isOneEmpty(dictId, dictCode, dictName, dictValues)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        dictService.editDict(dictId, dictCode, dictName, dictTips, dictValues);
        return SUCCESS_TIP;
    }

    /**
     * 删除字典记录
     */
    @BussinessLog(value = "删除字典记录", key = "dictId", dict = DictMap.class)
    @RequestMapping(value = "/delete")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delete(@RequestParam Integer dictId) {

        //缓存被删除的名称
        LogObjectHolder.me().set(ConstantFactory.me().getDictName(dictId));

        this.dictService.delteDict(dictId);
        return SUCCESS_TIP;
    }

}
