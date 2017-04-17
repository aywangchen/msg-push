package com.shide.common.service;


import javax.jms.Destination;

/**
 * Created by wangc on 2017/4/17.
 */
public interface ProducerService {
    /**
     * 向指定队列发送消息
     */
    public void sendMessage(Destination destination, final String msg);

    /**
     * 向默认队列发送消息
     */
    public void sendMessage(final String msg);
}
