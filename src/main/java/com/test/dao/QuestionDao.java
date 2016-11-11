package com.test.dao;

import com.test.model.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface QuestionDao {

    /**
     * 插入请求对象
     * @param question 请求对象
     * @return 成功为1，失败为0
     */
    int insertQuestion(@Param("question") Question question);

    /**
     * 更新请求对象
     * @param question 请求对象
     * @return 成功为1，失败为0
     */
    int updateQuestion(@Param("question") Question question);

    /**
     * 根据id查找单个question对象
     * @param questId
     * @return 请求对象
     */
    Question selectOneById(@Param("questId") int questId);

    /**
     * 根据组织id查找全部请求集合
     * @param organId 组织id
     * @param time 时间节点
     * @return question的集合
     */
    List<Question> seclectAllByOrganId(@Param("organId") int organId,@Param("time")Date time);

    /**
     * 根据id删除请求对象
     * @param questId
     * @return 成功为1，失败为0
     */
    int deleteQuestionById(@Param("questId") int questId);

    /**
     * 设置最佳答案
     * @param questId 请求id
     * @param bestAId qAnswerId(请求答案id)
     * @return 成功为1，失败为0
     */
    int setBestAId(@Param("questId") int questId, @Param("bestAId") int bestAId);
}
