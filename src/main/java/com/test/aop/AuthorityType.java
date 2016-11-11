package com.test.aop;

/**
 * 利用AOP验证用户登录状态
 *
 * Created by zggdczfr on 2016/10/2.
 */
public enum AuthorityType {

    // 登录和权限都验证 默认
    Validate,

    // 不验证
    NoValidate,

    // 不验证权限
    NoAuthority;
}
