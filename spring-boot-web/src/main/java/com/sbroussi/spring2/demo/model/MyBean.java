package com.sbroussi.spring2.demo.model;


import com.sbroussi.spring2.demo.config.JmsProperties;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.PostConstruct;

@Getter
@ToString
public class MyBean {

    private String fieldName;
    private String fieldValue;
    private MySecondBean mySecondBean;

    public MyBean(JmsProperties jmsProperties) {
        fieldName = "name is " + jmsProperties.getName();
        fieldValue = "value is " + jmsProperties.getValue();
    }

    @PostConstruct
    public MySecondBean createMySecondBean() {
        mySecondBean = new MySecondBean(this);
        return mySecondBean;
    }


}
