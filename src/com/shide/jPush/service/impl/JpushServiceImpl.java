package com.shide.jPush.service.impl;

import com.alibaba.fastjson.JSON;
import com.shide.common.service.ConsumerService;
import com.shide.jPush.bean.MsgDTO;
import com.shide.jPush.service.JpushServive;
import com.shide.jPush.util.MessagePush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * Created by wangc on 2017/4/17.
 */
public class JpushServiceImpl implements JpushServive {

    public void sendMsgPush(String msg) {
        try {
            MessagePush messagePush = new MessagePush(1);
            MsgDTO msgDTO = JSON.parseObject(msg, MsgDTO.class);
            messagePush.sendMsgPush(msgDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
