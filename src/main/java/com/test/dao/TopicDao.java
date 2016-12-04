package com.test.dao;

import com.test.model.Topic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hunger on 2016/11/25.
 */
@Repository
public interface TopicDao {

    /**
     * 插入题目
     * @param topic 题目对象
     * @return
     */
    Integer insertTopic(@Param("topic")Topic topic);

    /**
     * 更新题目
     * @param topic 题目对象
     * @return
     */
    Integer updateTopic(@Param("topic")Topic topic);

    /**
     * 删除题目
     * @param topicId 题目id
     * @return
     */
    Integer deleteTopic(@Param("textpaperId")Integer textpaperId);

    /**
     * 根据题目id查找题目
     * @param topicId 题目id
     * @return
     */
    Topic selectOne(@Param("topicId")Integer topicId);

    /**
     * 根据试卷id查找整份试卷
     * @param textpaperId
     * @return
     */
    List<Topic> selectAll(@Param("textpaperId")Integer textpaperId);
}
