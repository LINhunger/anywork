package com.test.service.impl;

import com.test.dao.RelationDao;
import com.test.model.Organization;
import com.test.model.Relation;
import com.test.service.RelationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
@Service
public class RelationServiceImpl implements RelationService {

    @Resource
    private RelationDao relationDao;

    public void insertRelation(@Param("relation") Relation relation) { relationDao.insertRelation(relation);
    }

    public int deleteRelation(@Param("relationId") int relationId) {
        return relationDao.deleteRelation(relationId);
    }

    public Relation selectOneById(@Param("relationId") int relationId) {
        return relationDao.selectOneById(relationId);
    }

    public List<Relation> selectAllByOrganId(@Param("organId") int organId) {
        return relationDao.selectAllByOrganId(organId);
    }

    public List<String> selectOpenidByOrganId(@Param("organId") int organId) {
        List<String> openidList = relationDao.selectOpenidByOrganId(organId);
        //将组织openid中的null去掉
        openidList.removeAll(Collections.singleton(null));
        return openidList;
    }

    public List<Organization> selectOrganByUserId(@Param("userId") int userId) {
        return relationDao.selectOrganByUserId(userId);
    }

    public int updateRoleByRelation(@Param("relation") Relation relation) {
        return relationDao.updateRoleByRelation(relation);
    }

    public int selectRoleByRelatio(@Param("userId") int userId, @Param("organId") int organId) {
        return relationDao.selectRoleByRelation(userId, organId);
    }

    public List<Organization> selectOrganByOpenid(@Param("openid") String openid) {
        return relationDao.selectOrganByOpenid(openid);
    }
}
