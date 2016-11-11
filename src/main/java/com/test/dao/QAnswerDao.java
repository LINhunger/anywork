package com.test.dao;

import com.test.model.QAnswer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface QAnswerDao {


    /**
     * 插入请求回答
     * @param qAnswer
     * @return 成功为1，失败为0
     */
    int insertQAnswer(@Param("qAnswer") QAnswer qAnswer);

    /**
     * 更新请求回答
     * @param qAnswer
     * @return 成功为1，失败为0
     */
    int updateQAnswer(@Param("qAnswer")QAnswer qAnswer);

    /**
     * 根据请求id,和用户id查找请求回答
     * @param questId
     * @param authorId
     * @return 请求回答对象
     */
    QAnswer selectOneById(@Param("questId")int questId,@Param("authorId") int authorId);

    /**
     * 根据请求id查找全部请求答案
     * @param questId 请求id
     * @return 请求答案集合
     */
    List<QAnswer> selectAllByQuestId(@Param("questId")int questId);
}
