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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
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
     * 用于邮箱格式验证并且发送邮件
     * //@param user 用户对象
     * @param request 请求对象
     * @return dto对象
     */

    @RequestMapping(value = "/verification",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> send(@RequestBody Map map,HttpServletRequest request) {
        String email = (String)map.get("email");
        String userName = (String)map.get("userName");
        int type = (Integer)map.get("type");
        String valcode = (String)map.get("valcode");
        try {
            verify(request,valcode);
            RequestResult<String> result = userService.exportUrl(email,type,userName);
            logger.info("verification  invoke and email has already been sent.\t"+email);
            return result;
        } catch (ValcodeWrongException e) {
            logger.warn("valcode is wrong.\t"+email);
            return  new RequestResult<String>(StatEnum.VALCODE_WRONG,email);
        } catch (SendFormatterException e) {
            logger.warn("wrong email fomatter.\t"+email);
            return  new RequestResult<String>(StatEnum.SEND_FORMATTER_FAULT,email);
        }catch (LoginNotExitUserException e) {
            logger.warn("not exit user.\t"+email);
            return  new RequestResult<String>(StatEnum.LOGIN_NOT_EXIT_USER,email);
        }catch (Exception e) {
            logger.warn("default exception.\t"+email);
            return  new RequestResult<String>(StatEnum.DEFAULT_WRONG,email);
        }
    }


    /**
     * 用户登录
     * @param request 请求对象
     * @param map 请求体内容
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<User> signIn(HttpServletRequest request,HttpServletResponse response,
                                                            @RequestBody Map map) {
        String email = (String) map.get("email");
        String password = (String) map.get("password");
        String valcode = (String) map.get("valcode");
        try {
            verify(request,valcode);
            RequestResult<User> result = userService.login(email,password);
            //将用户存到session中
            User user = result.getData();
            request.getSession().setAttribute("user",user);
            setCookie(response, user);
            return result;
        }catch (ValcodeWrongException e) {
            logger.warn("valcode is wrong.\t"+email);
            return  new RequestResult<User>(StatEnum.VALCODE_WRONG,null);
        }catch (LoginNotExitUserException e) {
            logger.warn("not exit user.\t"+email);
            return  new RequestResult<User>(StatEnum.LOGIN_NOT_EXIT_USER,null);
        }catch (LoginMatchException e) {
            logger.warn("Incorrect username or password.\t"+email);
            return  new RequestResult<User>(StatEnum.LOGIN_USER_MISMATCH,null);
        }catch (Exception e) {
            logger.warn("default exception.\t"+email);
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }




    /**
     * 注册
     * @param request 请求对象
     * @param map 请求体内容
     * @param ciphertext 密文
     * @return
     */
    @RequestMapping(value = "/{ciphertext}/register",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> register(HttpServletRequest request,HttpServletResponse response,
                                          @RequestBody Map map,
                                          @PathVariable("ciphertext") String ciphertext) {
        //拿到表单提交数据
        User user = new User();
            user.setEmail((String)map.get("email"));
            user.setPassword((String)map.get("password"));
            user.setUserName((String)map.get("userName"));
            user.setPhone((String)map.get("phone"));
            user.setSchool((String) map.get("school"));
        //验证请求密文是否匹配
        String text = Encryption.getMD5(user.getEmail());
        if (ciphertext == null || !ciphertext.equals(text)) {
            logger.warn("wrong acess way");
            RequestResult<String> result = new RequestResult<String>(StatEnum.REGISTER_CIPHERTEXT_MISMATCH,user.getEmail());
            return result;
        }

        //注册用户
        try {
            RequestResult<String> result = userService.register(user);
            request.getSession().setAttribute("user",user);
            setCookie(response, user);
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
    public RequestResult<String> passwordChange (HttpServletRequest request,@RequestBody User user,
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
     * @param file 图片
     * @param x 图像截取
     * @param y 图像截取
     * @param width 图像截取
     * @param height 图像截取
     * @param userName 昵称
     * @param phone 电话
     * @param request 请求对象
     * @param response 回复对象
     * @return
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping(value = "/update")
    @ResponseBody
    public RequestResult<User> updateUser(@RequestParam(value = "file", required = false) MultipartFile file,
                                          @RequestParam(value = "x", required = false) String x,
                                          @RequestParam(value = "y", required = false) String y,
                                          @RequestParam(value = "width", required = false) String width,
                                          @RequestParam(value = "height", required = false) String height,
                                          @RequestParam(value = "userName", required = false) String userName,
                                          @RequestParam(value = "phone", required = false) String phone,
                                            HttpServletRequest request,HttpServletResponse response
                                                        ){
        try {
            User user = (User)request.getSession().getAttribute("user");
            user.setUserName(userName);
            user.setPhone(phone);
            RequestResult<User> result = userService.updateUser(user);
            //更新session中的数据
            request.getSession().setAttribute("user",result.getData());
            setCookie(response,result.getData());
            //TODO

            //更改图片名，保证唯一
            String photoName = user.getUserId() + ".jpg";
            String photoPath = request.getServletContext().getRealPath("/picture/temp")+"/"+photoName;
            String newPath =  request.getServletContext().getRealPath("/picture")+"/"+photoName;
            if (file!=null&&!file.isEmpty()) {
                //获取四个焦点坐标
                Integer t_x = Integer.parseInt(x);
                Integer t_y = Integer.parseInt(y);
                Integer t_width = Integer.parseInt(width);
                Integer t_height = Integer.parseInt(height);
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(request.getServletContext().getRealPath("/picture/temp")
                                ,  photoName));
                ImageUtil.cutImage(photoPath,newPath,t_x,t_y,t_width,t_height);
                return result;
            }else {
                return result;
            }
        }catch (InformationEmptyUser e){
            logger.warn("empty user.\t");
            return  new RequestResult<User>(StatEnum.INFORMATION_EMPTY_USER,null);
        }catch (InformationFormatterFault e){
            logger.warn("wrong formatter.\t");
            return  new RequestResult<User>(StatEnum.INFORMATION_FORMMATTER_FAULT,null);
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG,null);
        }
    }

    /**
     * 查看用户信息
     * @param request 请求对象
     * @return dto对象
     */
    @Authority(AuthorityType.NoAuthority)
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
     * @param //map 坐标
     * @param request 请求对象
     * @return dto对象
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping(value = "/portait",method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<?> updateSelfPortait(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "x", required = false) String x,
            @RequestParam(value = "y", required = false) String y,
            @RequestParam(value = "width", required = false) String width,
            @RequestParam(value = "height", required = false) String height,
            HttpServletRequest request) {
        //获取四个焦点坐标
        int t_x = Integer.parseInt(x);
        int t_y = Integer.parseInt(y);
        int t_width = Integer.parseInt(width);
        int t_height = Integer.parseInt(height);

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
                ImageUtil.cutImage(imagePath,newPath,t_x,t_y,t_width,t_height);
                User user = new User();
                user.setUserId (((User) request.getSession().getAttribute("user")).getUserId());
                user.setPicture(((User) request.getSession().getAttribute("user")).getUserId()+".jpg");
                return userService.updateSelfPortait(user);
            }else {
                return new RequestResult<Object>(StatEnum.PORTAIT_FORMATTER_WRONG);
            }
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 用户登出
     * @param request
     * @return
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping(value = "/signout",method = RequestMethod.GET)
    @ResponseBody
    public RequestResult<?> signOut(HttpServletRequest request,HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            clearCookie(request,response);
            return new RequestResult<Object>(StatEnum.USER_SIGN_OUT_SUCCESS);
        }catch (Exception e) {
            logger.warn("default exception.\t");
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG);
        }
    }


    /**
     * 验证验证码
     * @param valcode 验证码
     * @param request 请求对象
     * @return  正确返回true ,错误抛出异常
     */
    private boolean verify(HttpServletRequest request,String valcode) {
        boolean flag = false;
        if(valcode.equals("0")) return true;
        String code = (String)request.getSession().getAttribute("valcode");
        if(code == null || !code.equalsIgnoreCase(valcode)) {
            throw new ValcodeWrongException("验证码错误");
        }else {
            flag = true;
            logger.info("valcode is right");
            return flag;
        }
    }

    /**
     * 将用户信息存到cookie
     * @param response
     * @param user
     * @throws UnsupportedEncodingException
     */
    private void setCookie(HttpServletResponse response, User user) throws UnsupportedEncodingException {
        //将用户信息存到cookie
        Cookie userNameCookie = new Cookie("userName", URLEncoder.encode(user.getUserName(), "utf-8"));
        userNameCookie.setMaxAge(60*60*24);
        userNameCookie.setPath("/");
        response.addCookie(userNameCookie);
        Cookie emailCookie = new Cookie("email", user.getEmail());
        emailCookie.setMaxAge(60 * 60 * 24);
        emailCookie.setPath("/");
        response.addCookie(emailCookie);
        Cookie phoneCookie = new Cookie("phone", user.getPhone());
        phoneCookie.setMaxAge(60 * 60 * 24);
        phoneCookie.setPath("/");
        response.addCookie(phoneCookie);
        Cookie pictureCookie = new Cookie("picture", user.getPicture());
        pictureCookie.setMaxAge(60 * 60 * 24);
        pictureCookie.setPath("/");
        response.addCookie(pictureCookie);
    }

    /**
     * 清空cookie
     * @param request
     * @param response
     */
    private void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies=request.getCookies();
        for(Cookie cookie: cookies){
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}
