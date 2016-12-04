package com.test.dao;

import com.test.model.Textpaper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hunger on 2016/11/25.
 */
@Repository
public interface TextpaperDao {

    /**
     * 插入试卷
     * @param textpaper 试卷对象
     * @return
     */
    Integer insertTextpaper(@Param("textpaper")Textpaper textpaper);

    /**
     * 更新题目
     * @param textpaper 试卷对象
     * @return
     */
    Integer updateTextpaper(@Param("textpaper")Textpaper textpaper);

    /**
     * 删除试卷
     * @param textpaperId 试卷id
     * @return
     */
    Integer deleteTextpaper(@Param("textpaperId")Integer textpaperId);

    /**
     * 根据试卷id查找试卷
     * @param textpaperId 试卷id
     * @return
     */
    Textpaper selectOne(@Param("textpaperId")Integer textpaperId);

    /**
     * 根据试卷标题模糊查询试卷
     * @param textpaperTitle
     * @return
     */
    List<Textpaper> selectAllByTitle(@Param("textpaperTitle")String textpaperTitle);

    /**
     * 根据试卷id查找试卷集合
     * @param organId 组织id
     * @param time 时间节点
     * @return 作业对象集合
     */
    List<Textpaper> selectAllByOrganId(@Param("organId") int organId,@Param("time")Date time);
}
