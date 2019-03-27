package com.exch.platform.modular.mq.test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: chenyadong
 *
 * 测试MQ发送数据
 *
 * @Date: 2019/2/27 16:13
 * @Version 1.0
 */
@Component
@RequestMapping("/mq")
public class SenderMqTest {

    @Autowired
    private RabbitTemplate  rabbitTemplate;

    @RequestMapping("")
    public void sender(String msg){

        rabbitTemplate.convertAndSend("ack.test", msg);
        rabbitTemplate.convertAndSend("reportExchange","notify.payment.test", msg);
    }

}