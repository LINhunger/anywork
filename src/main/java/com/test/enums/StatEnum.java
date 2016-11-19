package com.test.enums;


public enum StatEnum {

    /**
     * 通用板块
     */
    VALCODE_WRONG(0,"验证码错误"),
    DEFAULT_WRONG(-1,"其他错误"),
    CAUSE_TROUBLE(-2,"你不要搞事"),
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
     * 用户登出板块
     */
    USER_SIGN_OUT_SUCCESS(171,"登出成功"),
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
     * 提交作业的答案
     */
    SUBMIT_HANSWER_SUCCESS(241,"提交答案成功"),
    SUBMIT_TIME_OUT(242,"提交答案超时"),
    SUBMIT_FORMATTER_WRONG(243,"提交格式错误"),

    /**
     * 提交请求的回答
     */
    SUBMIT_QANSWER_SUCCESS(251,"提交回答成功"),
    BEST_ANSWER_EXIST(252,"已存在最佳回答"),
    QANSWER_FORMATTER_WRONG(253,"回答格式错误"),

    /**
     * 设置最佳答案
     */
    BEST_ANSWER_SET_SUCCESS(261,"设置最佳答案成功"),

    /**
     * 评分
     */
    SCORE_SUCCESS(271,"评分成功"),

    /**
     * 发布作业
     */
    RELEASE_HOMEWORK_SUCCESS(281,"发布作业成功"),

    /**
     * 发布公告
     */
    RELEASE_INFORM_SUCCESS(291,"发布公告成功"),

    /**
     * 发布请求
     */
    RELEASE_QUESTION_SUCCESS(301,"发布请求成功"),

    /**
     * markdown格式预览
     */
    PREVIEW_SUCCESS(311,"预览成功"),

    /**
     * 补充
     */
    APPLY_ALREADY_EXIST(153,"申请已经发送，请等待管理员验证"),
    APPLY_IS_PASS(153,"您已是组织成员"),
    NO_PERMISSION(153,"只有管理员和群主才具有该权限"),
    SUBMIT_FILE_SUCCESS(153,"文件上传成功"),
    SUBMIT_WRONG_FILE(154,"不予许的文件格式"),
    SUBMIT_FILE_FAULT(155,"上传文件不能超过20M"),
    FILE_SHOW_SUCCESS(156,"显示文件列表成功"),





    /**
     *
     * zggdczfr
     *
     */

    /**
     * 权限通用模块
     * 微信模块
     */
    WITHOUT_POWER(191, "用户没有相应的处理权限"),
    ERROR_DELETE(192, "不能删除负责人或管理员"),
    WEIXIN_ORGANIZATION(193, "微信用户获取组织列表成功"),
    WEIXIN_INFORM(194, "微信客户端获取公告"),
    WETXIN_QUESTION(195, "微信客户端获取作业"),
    WEIXIN_REQUEST_ERROR(196, "微信客户端获取信息失败"),
    WEIXIN_BIND_SUCCESS(197, "微信绑定成功"),
    WEIXIN_BIND_FAIL(198, "微信绑定失败"),

    /**
     * 组织申请模块
     */
    APPLY_SEND_SUCCESS(151, "组织申请发送成功"),
    APPLY_SEND_FAIL(152, "组织申请发送失败"),
    APPLY_HANDLE_SUCCESS(154, "处理申请成功"),
    APPLY_HANDLE_FAIL(155, "处理申请失败"),
    APPLY_NOHANDLR_ALL(156, "获得所有未处理的组织申请"),
    APPLY_SEND_REPEAT(157, "您已经是该组织用户了"),
    APPLY_DELETE_SUCCESS(158, "删除申请成功"),
    APPLY_DELETE_FAIL(159, "删除申请失败"),

    /**
     * 评论和回复模块
     */
    COMMENT_ALL(161, "获取所有的评论和回复"),
    COMMENT_ANNOUNCE_SUCCESS(162, "发表评论成功"),
    RECOMMENT_ANNOUNCE_SUCCESS(163, "回复评论成功"),
    COMMENT_ANNOUNCE_FAIL(164, "发表失败"),

    /**
     * 组织关系处理模块
     */
    RELATION_DELETE_SUCCESS(171, "删除用户成功"),
    RELATION_DELETE_FAIL(172, "删除用户失败"),
    RELATION_ALL(173, "获取所有未处理申请"),
    RELATION_UPDATE_SUCCESS(174, "设置权限成功"),
    RELATION_UPDATE_FAIL(175, "设置权限失败"),
    RELATION_ALL_ORGAN(176, "获取用户组织列表"),

    /**
     * 组织模块
     */
    ORGAN_CREATE_SUCCESS(181, "创建组织成功"),
    ORGAN_CREATE_FAIL(182, "创建组织失败"),
    ORGAN_SEARCH_ID(183, "通过组织id来查找组织"),
    ORGAN_SEARCH_NAME(184, "通过组织名来查找组织"),
    ORGAN_ID_ERROR(185, "组织id格式错误"),

    ALL(999,"test");

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
