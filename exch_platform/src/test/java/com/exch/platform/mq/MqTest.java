package com.exch.platform.mq;

import com.exch.platform.base.BaseJunit;
import com.exch.platform.modular.mq.test.SenderMqTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: chenyadong
 * @Date: 2019/2/27 16:14
 * @Version 1.0
 */
public class MqTest extends BaseJunit {

    @Autowired
    private SenderMqTest sender;

    @Test
    public void test_sender() {
        sender.sender("支付订单号："+System.currentTimeMillis());
    }
}
