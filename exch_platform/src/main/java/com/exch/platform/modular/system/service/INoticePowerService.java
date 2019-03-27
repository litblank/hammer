package com.exch.platform.modular.system.service;

import com.exch.platform.modular.system.entity.NoticePower;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 通知管理 服务类
 * </p>
 *
 * @author chenyd
 * @since 2019-01-15
 */
public interface INoticePowerService extends IService<NoticePower> {

    boolean updateNoticePower(String noticeId, String roleId, String userIds);
}
