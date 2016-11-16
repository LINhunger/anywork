package com.test.message;

/**
 * 用于消息回复
 * Created by zggdczfr on 2016/11/5.
 */
public class Message {

    private int code;
    private String msg;
    private Object data;

    /**
     * 构造器
     */
    public Message(){}

    public Message(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Message(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * get && set 方法
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 返回成功消息
     */
    public static Message success(){
        return new Message(Code.SUCCESS, "操作成功！");
    }

    public static Message success(String content){
        return new  Message(Code.SUCCESS, content);
    }

    public static Message success(Object data){
        return new Message(Code.SUCCESS, "操作成功！", data);
    }

    public static Message success(String content, Object data) {
        return new Message(Code.SUCCESS, content, data);
    }

    /**
     * 返回失败消息
     */
    public static Message error() {
        return new Message(Code.FAIL, "操作失败");
    }

    public static Message error(String content){
        return new Message(Code.FAIL, content);
    }

    public static Message error(String content, Object data){
        return new Message(Code.FAIL, content, data);
    }

    /**
     * 其他返回
     */
    public static Message all_Message(int code, String content, Object data){
        return new Message(code, content, data);
    }

}
