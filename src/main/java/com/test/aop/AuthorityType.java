package com.test.aop;

/**
 * 利用AOP验证用户登录状态
 * 判断登录，验证是否有权限浏览
 * Created by zggdczfr on 2016/11/6.
 */
public enum AuthorityType {

    // 登录和权限都验证 默认
    Validate,

    // 不验证
    NoValidate,

    // 只判断登录，不验证权限
    NoAuthority,

    //判断是否管理员
    Admin
}
