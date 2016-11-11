package com.test.aop;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.test.enums.Level;
import com.test.util.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;

/**
 * Created by zggdczfr on 2016/10/2.
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = Logger.getLogger(AuthorityAnnotationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception{
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            try {
                if (clazz != null && method != null){
                    boolean isClazzAnnotation = clazz.isAnnotationPresent(Authority.class);
                    boolean isMethodAnnotation = method.isAnnotationPresent(Authority.class);
                    Authority authority = null;
                    // 如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
                    if (isMethodAnnotation) {
                        authority = method.getAnnotation(Authority.class);
                    } else if (isClazzAnnotation) {
                        authority = clazz.getAnnotation(Authority.class);
                    }

                    int code = -1;
                    String message = "";
                    if (authority != null){
                        if (AuthorityType.NoValidate == authority.value()){
                                // 标记为不验证,放行
                                System.out.println("========================");
                                System.out.println("标记为不验证,放行");

                                return true;
                        } else if (AuthorityType.NoAuthority == authority.value()){
                            // 不验证权限，验证是否登录

                            System.out.println("========================");
                            System.out.println("不验证权限，验证是否登录");
                            return true;
                        } else {
                            // 验证登录及权限

                            System.out.println("=======================");
                            System.out.println("验证登录及权限");
                            HttpSession session = request.getSession();
                            if (null == session.getAttribute("userid")){
                                //抛出自定义异常
                            }
                            return true;
                        }
                    }
                }
            } catch (Exception e){
                LOGGER.log(Level.ERROR, "验证方法报错！ 错误：{0}", e);
            }
        }
        return false;
    }
}
