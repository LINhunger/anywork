package com.test.dao;


import com.test.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface UserDao {

    /**
     * 插入用户
     * @param user 用户对象
     * @return 成功为1，错误为0
     */
    int insertUser(@Param("user") User user);

    /**
     * 更新用户
     * @param user 用户对象
     * @return 成功为1，错误为0
     */
    int updateUser(@Param("user")User user);

    /**
     * 根据id查找用户
     * @param userId 用户id
     * @return 用户对象
     */
    User selectOneById(@Param("userId") int userId);

    /**
     * 根据邮箱查找用户
     * @param email 用户邮箱
     * @return 用户对象
     */
    User selectOneByEmail(@Param("email") String email);

    /**
     * 根据用户id,更新微信绑定
     * @param userId 用户id
     * @param openid 微信凭证
     * @return 成功为1，失败为0
     */
    int updateOpenidById(@Param("userId") int userId,@Param("openid") String openid);
}
