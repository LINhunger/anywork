package com.test.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import com.test.enums.Level;
import com.test.util.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zggdczfr on 2016/9/27.
 */
@Component
public class AllExceptionResolver implements HandlerExceptionResolver{

    private static final Logger LOGGER = Logger.getLogger(AllExceptionResolver.class);

    @ResponseBody
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) {

        LOGGER.log(Level.ERROR, "访问 {0} 发生错误, 错误信息: {1}", request.getRequestURI(), e.getMessage());

        //跳转到定制化的错误页面
        ModelAndView view = new ModelAndView("/error.jsp");
        view.addObject("myerror", "错误提示信息:"+e.getMessage());
        return view;

        //返回json格式的错误信息
//        try {
//            PrintWriter writer = response.getWriter();
//            BaseResult<String, String> result = new BaseResult(false, e.getMessage());
//            writer.write(JSON.toJSONString(result));
//            writer.flush();
//        } catch (Exception ex) {
//            LOGGER.log(Level.ERROR,"Exception: {0}",e);
//        }
    }
}
