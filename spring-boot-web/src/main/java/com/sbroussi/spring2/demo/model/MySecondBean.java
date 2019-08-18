package com.sbroussi.spring2.demo.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MySecondBean {

    private String fullName;


    public MySecondBean(MyBean myBean) {
        fullName = "in MySecondBean: " + myBean.getFieldName() + " = " + myBean.getFieldValue();
    }
}
