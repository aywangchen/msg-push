package com.shide.common.service.impl;

import com.shide.common.service.ConsumerService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Created by wangc on 2017/4/17.
 */
@Service("consumerService")
public class ConsumerServiceImpl implements ConsumerService{

    @Resource(name="jmsTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 接收消息
     */
    public TextMessage receive(Destination destination) {
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t"
                    + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }

        return tm;

    }
}
