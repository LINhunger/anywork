package com.test.service.impl;

import com.test.dao.OrganDao;
import com.test.model.Organization;
import com.test.service.OrganService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
@Service
public class OrganServiceImpl implements OrganService {

    @Resource
    private OrganDao organDao;

    public int insertOrgan(@Param("organ") Organization organ) {
        return organDao.insertOrgan(organ);
    }

    public Organization selectOneById(@Param("organId") int organId) {
        return organDao.selectOneById(organId);
    }

    public List<Organization> selectOneByName(@Param("organName") String organName) {
        return organDao.selectOneByName(organName);
    }
}
