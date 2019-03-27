package com.exch.platform.multi.service.impl;

import com.exch.platform.core.common.constant.DatasourceEnum;
import com.exch.platform.multi.entity.Test;
import com.exch.platform.multi.mapper.TestMapper;
import com.exch.platform.multi.service.TestService;
import cn.stylefeng.roses.core.mutidatasource.annotion.DataSource;
import com.exch.platform.core.common.constant.DatasourceEnum;
import com.exch.platform.multi.entity.Test;
import com.exch.platform.multi.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_BIZ)
    @Transactional
    public void testBiz() {
        Test test = new Test();
        test.setBbb("bizTest");
        testMapper.insert(test);
    }

    @Override
    @DataSource(name = DatasourceEnum.DATA_SOURCE_GUNS)
    @Transactional
    public void testGuns() {
        Test test = new Test();
        test.setBbb("gunsTest");
        testMapper.insert(test);
    }
}
