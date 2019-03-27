
package com.exch.platform.modular.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.exch.platform.core.common.annotion.BussinessLog;
import com.exch.platform.core.common.constant.dictmap.NoticeMap;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.core.log.LogObjectHolder;
import com.exch.platform.core.shiro.ShiroKit;
import com.exch.platform.core.shiro.ShiroUser;
import com.exch.platform.modular.system.entity.NoticePower;
import com.exch.platform.modular.system.model.Notice;
import com.exch.platform.modular.system.service.INoticePowerService;
import com.exch.platform.modular.system.service.INoticeService;
import com.exch.platform.modular.system.warpper.NoticeWrapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.exch.platform.core.common.annotion.BussinessLog;
import com.exch.platform.core.common.constant.dictmap.NoticeMap;
import com.exch.platform.core.common.constant.factory.ConstantFactory;
import com.exch.platform.core.log.LogObjectHolder;
import com.exch.platform.core.shiro.ShiroKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通知控制器
 *
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {

    private String PREFIX = "/system/notice/";

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private INoticePowerService noticePowerService;

    /**
     * 跳转到通知列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "notice.html";
    }
    /**
     * 跳转到通知权限页面
     */
    @RequestMapping("/power")
    public String power() {
        return  "/system/noticePower/noticePower.html";
    }

    /**
     * 跳转到添加通知
     */
    @RequestMapping("/notice_add")
    public String noticeAdd() {
        return PREFIX + "notice_add.html";
    }

    /**
     * 跳转到修改通知
     */
    @RequestMapping("/notice_update/{noticeId}")
    public String noticeUpdate(@PathVariable Integer noticeId, Model model) {
        Notice notice = this.noticeService.selectById(noticeId);
        model.addAttribute("notice", notice);
        LogObjectHolder.me().set(notice);
        return PREFIX + "notice_edit.html";
    }

    /**
     * 跳转到首页通知
     */
    @RequestMapping("/hello")
    public String hello() {
        ShiroUser user=ShiroKit.getUser();
        String userid=user.getId().toString();
        List<Integer> roleids=user.getRoleList();

        Wrapper<NoticePower> wrapper=new EntityWrapper<>();
        wrapper.eq("user_id",userid);
        List<NoticePower>  np=noticePowerService.selectList(wrapper);
        List list=new ArrayList();
        for(NoticePower obj:np){
            list.add(obj.getNoticeId());
        }
        if (list.size()==0){
            return "/blackboard.html";
        }
        List<Notice> notices = noticeService.selectBatchIds(list);
        super.setAttr("noticeList", notices);
        return "/blackboard.html";
    }

    /**
     * 获取通知列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.noticeService.list(condition);
        return super.warpObject(new NoticeWrapper(list));
    }

    /**
     * 新增通知
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @BussinessLog(value = "新增通知", key = "title", dict = NoticeMap.class)
    public Object add(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getTitle(), notice.getContent())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        notice.setCreater(ShiroKit.getUser().getId());
        notice.setCreatetime(new Date());
        notice.insert();
        return SUCCESS_TIP;
    }

    /**
     * 删除通知
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @BussinessLog(value = "删除通知", key = "noticeId", dict = NoticeMap.class)
    public Object delete(@RequestParam Integer noticeId) {

        //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(noticeId));

        this.noticeService.deleteById(noticeId);

        return SUCCESS_TIP;
    }

    /**
     * 修改通知
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @BussinessLog(value = "修改通知", key = "title", dict = NoticeMap.class)
    public Object update(Notice notice) {
        if (ToolUtil.isOneEmpty(notice, notice.getId(), notice.getTitle(), notice.getContent())) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Notice old = this.noticeService.selectById(notice.getId());
        old.setTitle(notice.getTitle());
        old.setContent(notice.getContent());
        old.updateById();
        return SUCCESS_TIP;
    }

}
