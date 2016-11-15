package com.test.aop;

import java.lang.annotation.*;

/**
 * Created by zggdczfr on 2016/11/6.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Authority {
    //默认注释
    AuthorityType value() default AuthorityType.NoValidate;
}
