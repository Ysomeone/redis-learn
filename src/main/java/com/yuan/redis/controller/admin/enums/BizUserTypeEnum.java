package com.yuan.redis.controller.admin.enums;

/**
 * 业务用户类型

 **/
public enum  BizUserTypeEnum {

    TEACHER("老师"),
    STUDENT("学生"),
    DEAN("系主任");
    private String val;

    BizUserTypeEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
