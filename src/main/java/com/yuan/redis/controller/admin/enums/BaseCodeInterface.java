package com.yuan.redis.controller.admin.enums;

/**
 * 自定义的错误描述枚举类需实现该接口
 **/
public interface BaseCodeInterface {

    /**
     * 错误码
     */
    int getResultCode();

    /**
     * 错误描述
     */
    String getResultMsg();

}
