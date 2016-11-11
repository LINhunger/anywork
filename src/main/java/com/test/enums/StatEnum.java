package com.test.enums;


public enum StatEnum {

    /**
     * 通用板块
     */
    VALCODE_WRONG(0,"验证码错误"),
    DEFAULT_WRONG(-1,"其他错误"),
    /**
     * 邮箱验证板块
     * */
    SEND_SUCCESS(101,"邮件发送成功"),
    SEND_FORMATTER_FAULT(102,"邮箱格式错误"),
    SEND_FAULT(103,"邮件发送失败"),

    /**
     * 注册板块
     */
    REGISTER_SUCESS(111,"注册成功"),
    REGISTER_EMPTY_USER(112,"空用户对象"),
    REGISTER_FAMMTER_FAULT(113,"注册信息格式错误"),
    REGISTER_CIPHERTEXT_MISMATCH(114,"密文信息不匹配"),
    REGISTER_ALREADY_EXIST(115,"已存在的用户"),


    /**
     * 登录板块
     */
    LOGIN_SUCCESS(121,"登录成功"),
    LOGIN_NOT_EXIT_USER(122,"不存在的用户"),
    LOGIN_USER_MISMATCH(123,"用户名或密码错误"),

    /**
     * 忘记密码板块
     */
    PASSWORD_CHANGE_SUCCESS(131,"修改密码成功"),
    PASSWORD_EMPTY_USER(132,"空用户对象"),
    PASSWORD_CIPHERTEXT_MISMATCH(133,"密文信息不匹配"),
    PASSWORD_FAMMTER_FAULT(134,"修改密码格式错误"),

    /**
     * 修改用户信息板块
     */
    INFORMATION_CHANGE_SUCCESS(141,"修改信息成功"),
    INFORMATION_EMPTY_USER(142,"空用户对象"),
    INFORMATION_FORMMATTER_FAULT(143,"修改信息格式错误"),

    /**
     * 上传图片板块
     */
    PORTAIT_UPLOAD_SUCCESS(151,"头像上传成功"),
    PORTAIT_FORMATTER_WRONG(152,"图片格式错误"),

    /**
     * 查看用户信息板块
     */
    INFO_SHOW_SUCCESS(161,"查看信息成功"),

    /**
     * 时间轴板块
     */
    TIMELINE_GET_SUCCESS(201,"获取时间轴成功"),

    /**
     * 查看作业板块
     */
    HOMEWORK_GET_SUCCESS(211,"获取作业成功"),

    /**
     * 查看公告板块
     */
    INFORM_GET_SUCCESS(221,"获取公告成功"),

    /**
     * 查看请求板块
     */
    QUESTION_GET_SUCCESS(231,"获取请求成功"),

    /**
     * 提交作业
     */
    SUBMIT_HOMEWORK_SUCCESS(241,"提交作业成功"),
    SUBMIT_TIME_OUT(242,"提交作业超时"),
    SUBMIT_FORMATTER_WRONG(243,"提交格式错误"),

    /**
     * 提交请求
     */
    SUBMIT_QUESTION_SUCCESS(251,"提交请求成功"),
    BEST_ANSWER_EXIST(252,"已存在最佳答案");

    private  int state;
    private  String stateInfo;

    StatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
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
    public  static  StatEnum statOf(int index) {
        for (StatEnum state : values()) {
            if (state.getState() == index) {
                return  state;
            }
        }
        return  null;
    }
}
