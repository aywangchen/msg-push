package com.shide.Init.service.Impl;

import com.shide.Init.service.InitService;
import com.shide.jPush.service.JpushServive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * Created by wangc on 2017/4/17.
 */
@Service("InitService")
public class InitServiceImpl implements InitService, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private JpushServive jpushServive;

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //jpushServive.sendMsgPush();
    }

}
