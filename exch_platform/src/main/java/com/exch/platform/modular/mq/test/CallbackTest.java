package com.exch.platform.modular.mq.test;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * @Author: chenyadong
 * @Date: 2019/2/28 18:40
 * @Version 1.0
 */
public class CallbackTest implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback {

    /**
     * 发送到交换机得确认机制
     *
     * 只要发送到交换机，则会表示发送成功
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        if (!ack) {
//            System.out.println("HelloSender消息发送失败" + cause );
//        } else {
//            System.out.println("HelloSender 消息发送成功 ");
//        }
    }

    /**
     * 发送到队列得确认机制
     *
     * 队列失败会调用
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("message = [" + message + "], i = [" + i + "], s = [" + s + "], s1 = [" + s1 + "], s2 = [" + s2 + "]");
    }
}
