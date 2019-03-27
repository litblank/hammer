package com.exch.platform.modular.system.service.impl;

import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.exch.platform.core.common.exception.BizExceptionEnum;
import com.exch.platform.core.util.ToolUtil;
import com.exch.platform.modular.system.entity.DataMethodFilter;
import com.exch.platform.modular.system.entity.DataSqlFilter;
import com.exch.platform.modular.system.mapper.DataSqlFilterMapper;
import com.exch.platform.modular.system.service.IDataMethodFilterService;
import com.exch.platform.modular.system.service.IDataSqlFilterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 拦截数据SQL 服务实现类
 * </p>
 *
 * @author chenyd
 * @since 2019-01-09
 */
@Service
public class DataSqlFilterServiceImpl extends ServiceImpl<DataSqlFilterMapper, DataSqlFilter> implements IDataSqlFilterService {

    @Autowired
    private IDataMethodFilterService dataMethodFilterService;

    @Override
    public Map<String,String> getUserDataPower(List<Integer> roleList) {
        Map<String,String> dataPower=new HashMap<>();
        try{
            if(roleList.size()>0){

                EntityWrapper<DataSqlFilter> wrapper = new EntityWrapper<>();
                wrapper.eq("is_delete","0");
                wrapper.and();
                wrapper.leftNest();
                int i=0;
                for(Integer roleId: roleList){
                    wrapper.like("role_id",roleId.toString()+",%", SqlLike.CUSTOM);
                    wrapper.or();
                    wrapper.like("role_id","%,"+roleId.toString()+",%", SqlLike.CUSTOM);
                    i++;
                    if(i!=roleList.size()){
                        wrapper.or();
                    }
                }
                wrapper.rightNest();
                List<DataSqlFilter> sqlFilters=this.selectList(wrapper);

                for(DataSqlFilter obj:sqlFilters){
                    if(obj.getMethodId()!=null && StringUtils.isNotEmpty(obj.getFilterSql())){
                        DataMethodFilter methodF=dataMethodFilterService.selectById(obj.getMethodId());
                        if(methodF!=null && StringUtils.isNotEmpty(methodF.getMethod())){
                            dataPower.put(methodF.getMethod(),obj.getFilterSql());
                        }
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return dataPower;
    }
}
