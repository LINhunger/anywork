package com.test.service;

import com.test.model.Organization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
public interface OrganService {

    /**
     * 创建组织
     * @param organ 组织对象
     * @return 成功为1，失败为0
     */
    int insertOrgan(@Param("organ") Organization organ);

    /**
     * 根据id查找组织
     * @param organId 组织id
     * @return 组织对象
     */
    Organization selectOneById(@Param("organId") int organId);

    /**
     * 根据组织名查找组织
     * @param organName  组织名
     * @return 组织对象
     */
    List<Organization> selectOneByName(@Param("organName") String organName);
}
