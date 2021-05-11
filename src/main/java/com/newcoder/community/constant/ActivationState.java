package com.newcoder.community.constant;

public interface ActivationState {
    /**
     *  激活成功
     */
    int ACTIVATION_SUCCESS = 0;
    /**
     *  重复激活
     */
    int ACTIVATION_REPEAT = 1;
    /**
     *  激活失败
     */
    int ACTIVATION_FAILURE = 3;
}
