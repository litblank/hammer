package com.exch.platform.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.exch.platform.modular.system.entity.DataSqlFilter;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 拦截数据SQL 服务类
 * </p>
 *
 * @author chenyd
 * @since 2019-01-09
 */
public interface IDataSqlFilterService extends IService<DataSqlFilter> {


    /**
     *  登陆后查询用户角色对应的数据权限
     * @param roleList
     * @return
     */
    public Map<String,String> getUserDataPower(List<Integer> roleList);

}
