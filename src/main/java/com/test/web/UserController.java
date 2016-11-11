package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.exception.user.*;
import com.test.model.User;
import com.test.service.UserService;
import com.test.util.Encryption;
import com.test.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

/**
 * Created by hunger on 2016/11/9.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    /**
     * 注册表单路径(暂时忽略)
     * @param request 请求对象
     * @param ciphertext 根据邮箱加密后的字符串，与邮箱唯一匹配
     * @return
     */
    @RequestMapping(value = "/{ciphertext}/form",method = RequestMethod.GET)
    public ModelAndView form(HttpServletRequest request, @PathVariable("ciphertext") String ciphertext) {
        String text = Encryption.getMD5(request.getParameter("email"));
        if (ciphertext == null || !ciphertext.equals(text)) {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:/index.jsp");
            return  mav;
        }else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("register");
            mav.addObject("email",request.getParameter("email"));
            mav.addObject("ciphertext",ciphertext);
            logger.info("form  invoke:");
            return mav;
        }

    }

    /**
     * 用于邮箱格式验证并且发送邮件
     * //@param user 用户对象
     * @param request 请求对象
     * @return dto对象
     */
    @RequestMapping(value = "/verification",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> send(@RequestBody Map map, HttpServletRequest request) {
        User user = (User) map.get("user");
        try {
            verify(request);
            int type = Integer.parseInt(request.getParameter("type"));
            RequestResult<String> result = userService.exportUrl(user.getEmail(),type);
            logger.info("verification  invoke and email has already been sent.\t"+user.getEmail());
            return result;
        } catch (ValcodeWrongException e) {
            logger.warn("valcode is wrong.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.VALCODE_WRONG,user.getEmail());
        } catch (SendFormatterException e) {
            logger.warn("wrong email fomatter.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.SEND_FORMATTER_FAULT,user.getEmail());
        }catch (Exception e) {
            logger.warn("default exception.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.DEFAULT_WRONG,user.getEmail());
        }
    }



    /**
     * 用户登录
     * @param request 请求对象
     * @param user 包装了用户名和密码的用户对象
     * @return dto
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<User> signIn(HttpServletRequest request,User user) {

        try {
            verify(request);
            RequestResult<User> result = userService.login(user);
            //将用户存到session中
            request.getSession().setAttribute("user",result.getData());
            //TODO
            return result;
        }catch (ValcodeWrongException e) {
            logger.warn("valcode is wrong.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.VALCODE_WRONG,null);
        }catch (LoginNotExitUserException e) {
            logger.warn("not exit user.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.LOGIN_NOT_EXIT_USER,null);
        }catch (LoginMatchException e) {
            logger.warn("Incorrect username or password.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.LOGIN_USER_MISMATCH,null);
        }catch (Exception e) {
            logger.warn("default exception.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }

    /**
     * 注册
     * @param request 请求对象
     * @param user 用户对象
     * @return dto对象
     */
    @Authority(AuthorityType.Validate)
    @RequestMapping(value = "/{ciphertext}/register",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> register(HttpServletRequest request,User user,
                                          @PathVariable("ciphertext") String ciphertext) {
        //验证请求密文是否匹配
        String text = Encryption.getMD5(user.getEmail());
        if (ciphertext == null || !ciphertext.equals(text)) {
            logger.warn("wrong acess way");
            RequestResult<String> result = new RequestResult<String>(StatEnum.REGISTER_CIPHERTEXT_MISMATCH,user.getEmail());
            return result;
        }
        //注册用户
        try {
            verify(request);//验证验证码
            RequestResult<String> result = userService.register(user);
            return result;
        } catch (ValcodeWrongException e) {
            logger.warn("valcode is wrong.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.VALCODE_WRONG,user.getEmail());
        }catch (RegisterEmptyUserException e) {
            logger.warn("empty user.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.REGISTER_EMPTY_USER,user.getEmail());
        }catch (RegisterFormatterFaultException e) {
            logger.warn("wrong formatter.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.REGISTER_FAMMTER_FAULT,user.getEmail());
        }catch (DataIntegrityViolationException e) {//主键重复异常
            logger.warn("user is already exist.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.REGISTER_ALREADY_EXIST,user.getEmail());
        }catch (Exception e) {
            logger.warn("default exception.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.DEFAULT_WRONG,user.getEmail());
        }
    }

    /**
     * 处理修改密码表单
     * @param request 请求对象
     * @param user 用户对象包含邮箱和新密码
     * @param ciphertext 密文
     * @return dto对象
     */
    @RequestMapping(value = "/{ciphertext}/change",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> passwordChange (HttpServletRequest request,User user,
                                                 @PathVariable("ciphertext") String ciphertext) {
        //验证请求密文是否匹配
        String text = Encryption.getMD5(user.getEmail());
        if (ciphertext == null || !ciphertext.equals(text)) {
            logger.warn("wrong acess way");
            RequestResult<String> result = new RequestResult<String>(StatEnum.PASSWORD_CIPHERTEXT_MISMATCH,user.getEmail());
            return result;
        }
        //修改密码
        try{
            RequestResult<String> result = userService.passwordChange(user);
            return result;
        }catch (PasswordEmptyUserException e){
            logger.warn("empty user.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.PASSWORD_EMPTY_USER,user.getEmail());
        }catch (PasswordFormatterFaultException e){
            logger.warn("wrong formatter.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.PASSWORD_FAMMTER_FAULT,user.getEmail());
        }catch (Exception e) {
            logger.warn("default exception.\t"+user.getEmail());
            return  new RequestResult<String>(StatEnum.DEFAULT_WRONG,user.getEmail());
        }


    }

    /**
     * 修改用户信息
     * @param request 请求对象
     * @param user 用户对象
     * @return dto对象
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<User> updateUser(HttpServletRequest request,User user){
        try {
            //虽然感觉没什么用
            user.setUserId(((User)request.getSession().getAttribute("user")).getUserId());
            RequestResult<User> result = userService.updateUser(user);
            //更新session中的数据
            request.getSession().setAttribute("user",result.getData());
            return  result;
        }catch (InformationEmptyUser e){
            logger.warn("empty user.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.INFORMATION_EMPTY_USER,null);
        }catch (InformationFormatterFault e){
            logger.warn("wrong formatter.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.INFORMATION_FORMMATTER_FAULT,null);
        }catch (Exception e) {
            logger.warn("default exception.\t"+user.getEmail());
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }

    /**
     * 查看用户信息
     * @param request 请求对象
     * @return dto对象
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<User> showUserInfo(HttpServletRequest request) {
        try {
            User user = (User) request.getSession().getAttribute("user");
            RequestResult<User> result = new RequestResult<User>(StatEnum.INFO_SHOW_SUCCESS,user);
            return result;
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }


    /**
     * 图片上传以及图片裁剪
     * @param file 图片文件对象
     * @param map 坐标
     * @param request 请求对象
     * @return dto对象
     */
    @RequestMapping(value = "/portait",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> updateSelfPortait(@RequestParam("file") MultipartFile file,
                                              @RequestBody Map map,
                                              HttpServletRequest request) {
        //获取四个焦点坐标
        int x = (Integer) map.get("x");
        int y = (Integer) map.get("y");
        int width =(Integer)map.get("width");
        int height = (Integer)map.get("height");
        //更改图片名，保证唯一
        String newName = ((User)request.getSession().getAttribute("user")).getUserId()+".jpg";
        String imagePath = request.getServletContext().getRealPath("/picture/temp")+"/"+newName;
        String newPath =  request.getServletContext().getRealPath("/picture")+"/"+newName;
        try {
            if (!file.isEmpty()) {
                logger.info("Process file:{}", file.getOriginalFilename());
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(request.getServletContext().getRealPath("/picture/temp")
                                ,  newName));
                ImageUtil.cutImage(imagePath,newPath,x,y,width,height);
                return new RequestResult<Object>(StatEnum.PORTAIT_UPLOAD_SUCCESS);
            }else {
                return new RequestResult<Object>(StatEnum.PORTAIT_FORMATTER_WRONG);
            }
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 验证验证码
     * @param request 请求对象
     * @return  正确返回true ,错误抛出异常
     */
    private boolean verify(HttpServletRequest request) {
        boolean flag = false;
        String valcode = (String)request.getSession().getAttribute("valcode");
        if(valcode == null || !valcode.equalsIgnoreCase(
                request.getParameter("valcode")
        )) {
            throw new ValcodeWrongException("验证码错误");
        }else {
            flag = true;
            logger.info("valcode is right");
            return flag;
        }
    }

}
