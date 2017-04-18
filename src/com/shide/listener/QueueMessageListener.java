package com.shide.listener;

import com.shide.jPush.service.JpushServive;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by wangc on 2017/4/17.
 */
public class QueueMessageListener implements MessageListener {

    private JpushServive jpushServive;

    Logger logger = Logger.getLogger(QueueMessageListener.class);

    public void setJpushServive(JpushServive jpushServive) {
        this.jpushServive = jpushServive;
    }

    //当收到消息后，自动调用该方法
    public void onMessage(Message message) {

        TextMessage tm = (TextMessage) message;
        try {
            /*System.out.println("QueueMessageListener监听到了文本消息：\t"
                    + tm.getText());*/
            logger.info("QueueMessageListener监听到了文本消息：\t"
                    + tm.getText());
            //do something ...
            jpushServive.sendMsgPush(tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
