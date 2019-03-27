package com.exch.platform.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.exch.platform.modular.system.entity.NoticePower;
import com.exch.platform.modular.system.mapper.NoticePowerMapper;
import com.exch.platform.modular.system.service.INoticePowerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 通知管理 服务实现类
 * </p>
 *
 * @author chenyd
 * @since 2019-01-15
 */
@Service
public class NoticePowerServiceImpl extends ServiceImpl<NoticePowerMapper, NoticePower> implements INoticePowerService {

    @Override
    @Transactional
    public boolean updateNoticePower(String noticeId, String roleId, String userIds) {
        //删除通知和角色
        deleteByNoRo(noticeId,roleId);

        //增加通知，角色，用户
        saveNoRoUs(noticeId,roleId,userIds);

        return false;
    }

    private void saveNoRoUs(String noticeId, String roleId, String userIds) {
        String[] strl=userIds.split(",");
        List<String> strlist=Arrays.asList(strl);
        List<NoticePower> nol=new ArrayList<>();
        for(String userid:strlist){
            if(StringUtils.isNotEmpty(userid)){
                NoticePower np=new NoticePower();
                np.setRoleId(Long.parseLong(roleId));
                np.setNoticeId(Long.parseLong(noticeId));
                np.setUserId(Long.parseLong(userid));
                nol.add(np);
            }
        }
        if(nol.size()>0){
            this.insertBatch(nol,nol.size());
        }
    }

    protected void deleteByNoRo(String noticeId, String roleId) {
        Wrapper<NoticePower> wrapper=new EntityWrapper<>();
        wrapper.eq("notice_id",noticeId);
        wrapper.eq("role_id",roleId);
        this.delete(wrapper);
    }
}
