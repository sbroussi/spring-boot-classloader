package com.sbroussi.spring2.demo.config;

import com.sbroussi.spring2.demo.model.MyBean;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.PostConstruct;

@Getter
@Setter
@ToString
public class JmsProperties {

    private String name;
    private String value;
    private MyBean myBean;

    @PostConstruct
    public MyBean createMyBean() {
        myBean = new MyBean(this);
        return myBean;
    }


}
