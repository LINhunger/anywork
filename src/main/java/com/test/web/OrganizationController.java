package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Organization;
import com.test.model.Relation;
import com.test.model.User;
import com.test.service.OrganService;
import com.test.service.RelationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.List;

/**
 * 组织模块控制层
 * Created by zggdczfr on 2016/11/10.
 */
@Controller
@RequestMapping("organ")
public class OrganizationController {

    @Resource
    private OrganService organService;

    @Resource
    private RelationService relationService;

    /**
     * 创建组织，同时设置组织负责人
     * @param organ 组织对象
     * @param request
     * @return
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping("/create")
    @ResponseBody
    public RequestResult<String> createOrgan(Organization organ, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //创建组织同时将关系插入关系表
        Relation relation = new Relation();
        int organId = organService.insertOrgan(organ);
        if(0 != organId){
            //创建失败
            return new RequestResult<String>(StatEnum.ORGAN_CREATE_FAIL);
        }
        //将信息存入组织关系
        relation.setOrganId(organId);
        relation.setRole(1);
        relation.setUser(user);
        relationService.insertRelation(relation);
        return new RequestResult<String>(StatEnum.ORGAN_CREATE_SUCCESS);
    }

    /**
     * 根据组织id查找组织
     * @param request
     * @return
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping("/searchId")
    @ResponseBody
    public RequestResult<Organization> searchOrganById(HttpServletRequest request){
        String strOrganId = request.getParameter("organId");
        int organId = 0;
        try {
            organId = Integer.parseInt(strOrganId);
        } catch (Exception e){
            return new RequestResult<Organization>(StatEnum.ORGAN_ID_ERROR);
        }
        Organization organ = organService.selectOneById(organId);
        return new RequestResult<Organization>(StatEnum.ORGAN_SEARCH_ID, organ);
    }

    /**
     * 根据组织名查找组织集合
     * @param request
     * @return
     */
    @Authority(AuthorityType.NoAuthority)
    @RequestMapping("/searchName")
    @ResponseBody
    public RequestResult<List<Organization>> searchOrganByName(HttpServletRequest request){
        String organName = request.getParameter("organName");
        List<Organization> organList = organService.selectOneByName(organName);
        return new RequestResult<List<Organization>>(StatEnum.ORGAN_SEARCH_NAME, organList);
    }
}
