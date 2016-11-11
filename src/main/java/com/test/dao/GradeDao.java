package com.test.dao;

import com.test.model.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface GradeDao {

    /**
     * 添加星级，级联答案表修改星数，注意防止重复插入
     * @param grade
     * @return 成功为1，失败为0
     */
    int insertGrade(@Param("grade") Grade grade);


    /**
     * 根据答案id与用户id查找评分对象
     * @param answerId 答案id
     * @param userId 用户id
     * @return 评分对象
     */
    Grade selectOneById(@Param("answerId")int answerId,@Param("userId")int userId);
}
