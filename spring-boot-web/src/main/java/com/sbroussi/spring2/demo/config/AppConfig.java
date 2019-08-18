package com.sbroussi.spring2.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "jms1")
    @ConfigurationProperties(prefix = "jms1")
    public JmsProperties jmsProperties1() {
        return new JmsProperties();
    }


    @Bean(name = "jms2")
    @ConfigurationProperties(prefix = "jms2")
    public JmsProperties jmsProperties2() {
        return new JmsProperties();
    }

}
