package com.exch.platform.config.plugin;

import com.exch.platform.modular.mq.test.CallbackTest;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: chenyadong
 * @Date: 2019/2/27 16:09
 * @Version 1.0
 */
@Configuration
public class MqDirectConfig {
    @Bean
    public Queue testACK() {
        return new Queue("ack.test");
    }

    @Bean
    public Queue paymentNotifyQueue() {
        return new Queue("notify.payment.test");
    }

    @Bean
    public Queue paymentNotifyQueue2() {
        return new Queue("notify.payment.test.2");
    }

    @Bean
    public FanoutExchange reportExchange() {
        return new FanoutExchange("reportExchange");
    }

    @Bean
    public Binding bindingReportRefundExchange(Queue paymentNotifyQueue, FanoutExchange reportExchange) {
        return BindingBuilder.bind(paymentNotifyQueue).to(reportExchange);
    }
    @Bean
    public Binding bindingReportpaymentNotifyQueue2(Queue paymentNotifyQueue2, FanoutExchange reportExchange) {
        return BindingBuilder.bind(paymentNotifyQueue2).to(reportExchange);
    }

    @Bean
    public RabbitTemplate getRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate=new RabbitTemplate();
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setReturnCallback(new CallbackTest());
        rabbitTemplate.setConfirmCallback(new CallbackTest());
        return rabbitTemplate;
    }
}
