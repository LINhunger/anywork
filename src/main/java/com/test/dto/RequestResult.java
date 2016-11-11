package com.test.dto;

import com.test.enums.StatEnum;


public class RequestResult<T> {
    private  int state;
    private String stateInfo;
    private T data;

    public RequestResult(StatEnum statEnum, T data) {
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.data = data;
    }

    public RequestResult(StatEnum statEnum) {
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
