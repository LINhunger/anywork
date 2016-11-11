package com.test.dao;

import com.test.model.Inform;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hunger on 2016/11/5.
 */
@Repository
public interface InformDao {

    /**
     * 添加公告
     * @param inform
     * @return 成功为1，失败为0
     */
    int insertInform(@Param("inform") Inform inform);

    /**
     * 修改公告
     * @param inform
     * @return 成功为1，失败为0
     */
    int updateInform(@Param("inform")Inform inform);

    /**
     * 查找单个公告
     * @param informId
     * @return 公告对象
     */
    Inform selectOneById(@Param("informId")int informId);

    /**
     * 查找多条公告
     * @param organId 组织id
     * @param time 时间节点
     * @return 公告对象的集合
     */
    List<Inform> selectAllByOrganId(@Param("organId")int organId,@Param("time")Date time);

    /**
     * 删除公告
     * @param informId 公告id
     * @return 成功为1，失败为0
     */
    int deleteInformById(@Param("informId")int informId);
}
