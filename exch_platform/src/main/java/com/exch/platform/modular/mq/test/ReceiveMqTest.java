package com.exch.platform.modular.mq.test;

import com.exch.platform.core.log.Log;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: chenyadong
 * @Date: 2019/2/27 16:11
 * @Version 1.0
 *
 * 测试MQ接收数据
 */
@Component
public class ReceiveMqTest {

    @RabbitHandler
    @RabbitListener(queues = "notify.payment.test")
    public void receive(Channel channel,Message message) {
        Log.info("1 receive message::"+message.getBody().toString());
        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "notify.payment.test.2")
    public void receive2(Channel channel,Message message) {
        Log.info("2 receive message::"+message.getBody().toString());
        try {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitHandler
    @RabbitListener(queues = "ack.test")
    public void acktest(Channel channel,Message message) {
        Log.info("ack receive message::" + message.getBody().toString());
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}