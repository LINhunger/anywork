package com.test.dao;

import com.test.model.HAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface HAnswerDao {

    /**
     * 插入作业答案
     * @param hAnswer
     * @return 成功为1，失败为0
     */
    int insertHAnswer(@Param("hAnswer") HAnswer hAnswer);


    /**
     * 更新作业答案
     * @param hAnswer
     * @return 成功为1，失败为0
     */
    int updateHAnswer(@Param("hAnswer")HAnswer hAnswer);

    /**
     * 根据作业id,和用户id查找作业答案
     * @param homeworkId 作业id
     * @param authorId 用户id
     * @return 作业答案对象
     */
    HAnswer selectOneById(@Param("homeworkId")int homeworkId , @Param("authorId")int authorId);

    /**
     * 根据作业id查找全部作业答案
     * @param homeworkId 作业答案对象
     * @return  作业答案对象集合
     */
    List<HAnswer> selectAllByHomeworkId(@Param("homeworkId")int homeworkId);

}
