package com.shide.common.service;

import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * Created by wangc on 2017/4/17.
 */
public interface ConsumerService {
    /**
     * 接收消息
     */
    public TextMessage receive(Destination destination);
}
