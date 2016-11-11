package com.test.aop;

import java.lang.annotation.*;

/**
 * Created by zggdczfr on 2016/10/2.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Authority {
    //默认注释
    AuthorityType value() default AuthorityType.Validate;
}
