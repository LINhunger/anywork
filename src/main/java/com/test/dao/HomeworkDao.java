package com.test.dao;

import com.test.model.Homework;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface HomeworkDao {

    /**
     * 插入作业对象
     * @param homework
     * @return 成功为1，失败为0
     */
    int insertHomework(@Param("homework") Homework homework);

    /**
     * 更新作业对象
     * @param homework
     * @return 成功为1，失败为0
     */
    int updateHomework(@Param("homework")Homework homework);

    /**
     * 根据id查找作业
     * @param homeworkId
     * @return 作业对象
     */
    Homework selectOneById(@Param("homeworkId") int homeworkId);

    /**
     * 根据组织id查找作业对象集合
     * @param organId 组织id
     * @param time 时间节点
     * @return 作业对象集合
     */
    List<Homework> selectAllByOrganId(@Param("organId") int organId,@Param("time")Date time);

    /**
     * 根据作业id,删除作业
     * @param homeworkId
     * @return 成功为1，失败为0
     */
    int deleteHomeworkById(@Param("homeworkId")int homeworkId);
}
