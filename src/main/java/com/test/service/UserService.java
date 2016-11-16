package com.test.service;

import com.test.dao.OrganDao;
import com.test.dao.RelationDao;
import com.test.dao.UserDao;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.exception.user.*;
import com.test.model.Organization;
import com.test.model.Relation;
import com.test.model.User;
import com.test.util.Encryption;
import com.test.util.SendCommonPostMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hunger on 2016/11/9.
 */
@Service
public class UserService {


    @Autowired
    private UserDao userDao;
    @Autowired
    private RelationDao relationDao;

    /**
     *  根据email地址发送邮件
     * @param email
     * @return  包装了返回信息的dto对象
     */
    public RequestResult<String> exportUrl(String email,int type,String userName) {

        if(email == null || !email.matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}")){
            //抛出异常，邮箱格式错误
            throw new SendFormatterException("邮箱格式错误");

        } else {
            if (type == 2 &&userDao.selectOneByEmail(email)==null) {
                throw new LoginNotExitUserException("不存在的用户");
            }
            int result = SendCommonPostMail.Send(email,type,userName);
            if (result != 200) {
                throw new UserException("邮件发送失败");
            }else {
                return  new RequestResult(StatEnum.SEND_SUCCESS,email);
            }
        }
    }



    /**
     * 注册用户
     * @param user 用户对象
     * @return 包含用户名的dto对象
     */
    public RequestResult<String> register(User user) {
        if (user == null) {
            throw new RegisterEmptyUserException("空用户对象");
        }
        else if(
                !user.getEmail().matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") ||
                        !user.getUserName().matches("[a-z0-9A-Z\\u4e00-\\u9fa5]{1,15}") ||
                        !user.getPassword().matches("\\w{6,15}")
                ) {
            throw new RegisterFormatterFaultException("注册信息格式错误");
        }else {
            //加密密码项
            user.setPassword(Encryption.getMD5(user.getPassword()));
            userDao.insertUser(user);
            return new RequestResult<String>(StatEnum.REGISTER_SUCESS,user.getEmail());
        }

    }

    /**
     * 修改密码方法
     * @param user 包含邮箱以及新密码
     * @return
     */
    public RequestResult<String> passwordChange(User user){
        if (user == null) {
            throw new PasswordEmptyUserException("空用户对象");
        }
        else if(
                !user.getEmail().matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") ||
                        !user.getPassword().matches("\\w{6,15}")
                ) {
            throw new PasswordFormatterFaultException("修改密码格式错误");
        }
        else{
            User dbUser = userDao.selectOneByEmail(user.getEmail());
            //加密密码项
            dbUser.setPassword(Encryption.getMD5(user.getPassword()));
            userDao.updateUser(dbUser);
            return new RequestResult<String>(StatEnum.PASSWORD_CHANGE_SUCCESS,dbUser.getEmail());
        }
    }


    /**
     * 用户登录
     * @param user 用户名和密码的集合
     * @return 成功dto,失败抛出异常
     */
    /**
     * 用户登录
     * @param email 用户邮箱
     * @param password 密码
     * @return
     */
    public RequestResult<User> login(String email , String password) {
        if (email == null|| password==null) {
            throw new LoginMatchException("空用户对象");
        }
        User dbUser = userDao.selectOneByEmail(email);
        if (dbUser == null) {
            //不存在的用户
            throw new LoginNotExitUserException("不存在的用户");
        }else if (!dbUser.getPassword().equals(
                Encryption.getMD5(password)
        )){
            //用户名或密码错误
            throw new LoginMatchException("错误的用户名或密码");
        }else {
            //登录成功
            //检索用户的所有组织
           List<Organization> relations = relationDao.selectOrganByUserId(dbUser.getUserId());
            Set<Organization> organs = new HashSet<Organization>(relations);
            dbUser.setOrgans(organs);
            return new RequestResult<User>(StatEnum.LOGIN_SUCCESS,dbUser);
        }
    }

    /**
     * 修改用户信息方法
     * @param user 用户对象
     * @return dto对象
     */
    public RequestResult<User> updateUser(User user) {

        if (user == null) {
            throw new InformationEmptyUser("空用户对象");
        }
        else if(
                !user.getEmail().matches("\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}") ||
                        !user.getUserName().matches("[a-z0-9A-Z\\u4e00-\\u9fa5]{1,15}") //TODO
                ) {
            throw new InformationFormatterFault("修改信息格式错误");
        }else {
            //更新用户对象
            user.setPassword("");
            userDao.updateUser(user);
            //查找更新后的对象
            User dbUser = userDao.selectOneById(user.getUserId());
            //检索用户的所有组织
            List<Organization> relations = relationDao.selectOrganByUserId(dbUser.getUserId());
            Set<Organization> organs = new HashSet<Organization>(relations);
            dbUser.setOrgans(organs);
            return new RequestResult<User>(StatEnum.INFORMATION_CHANGE_SUCCESS,dbUser);
        }
    }

    /**
     * 上传头像
     * @param user
     * @return
     */
    public RequestResult<?> updateSelfPortait(User user) {
        userDao.updateUser(user);
        return new RequestResult<Object>(StatEnum.PORTAIT_UPLOAD_SUCCESS);
    }
}
