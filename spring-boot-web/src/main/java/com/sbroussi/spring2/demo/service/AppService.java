package com.sbroussi.spring2.demo.service;

import com.sbroussi.spring2.demo.config.JmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AppService {

    @Autowired
    @Qualifier("jms1")
    private JmsProperties jmsProperties1;

    @Autowired
    @Qualifier("jms2")
    private JmsProperties jmsProperties2;


    public void dumpBeans() {
        log.info("AppService; bean jms1: " + jmsProperties1);
        log.info("AppService; bean jms2: " + jmsProperties2);

    }


}
