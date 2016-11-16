package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Organization;
import com.test.model.Relation;
import com.test.model.User;
import com.test.service.RelationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 组织关系控制器
 * Created by zggdczfr on 2016/11/8.
 */
@Controller
@Authority(AuthorityType.NoValidate)
@RequestMapping("/relation")
public class RelationController {

    @Resource
    private RelationService relationService;

    /**
     * 负责人、管理员删除用户关系
     * @param relationId
     * @param request
     * @return
     */
    @RequestMapping("/delete/{relationId}")
    @ResponseBody
    public RequestResult<String> deleteNumber(@PathVariable int relationId, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int organId = (Integer) session.getAttribute("organId");
        int role = relationService.selectRoleByRelatio(user.getUserId(), organId);
        Relation relation = relationService.selectOneById(relationId);
        if (1 == role || 2 == role){
            //有权处理
            if (3 != relation.getRole()){
                //不能删除组织负责人、管理员
                return new RequestResult<String>(StatEnum.ERROR_DELETE);
            }
            if (1 != relationService.deleteRelation(relation.getRelationId())){
                //删除失败
                return new RequestResult<String>(StatEnum.RELATION_DELETE_FAIL);
            }
            //删除成功
            return new RequestResult<String>(StatEnum.RELATION_DELETE_SUCCESS);
        }
        return new RequestResult<String>(StatEnum.WITHOUT_POWER);
    }

    /**
     * 管理员、负责人获得组织所有的用户
     * @param request
     * @return
     */
    @RequestMapping("/relations")
    @ResponseBody
    public RequestResult<List<Relation>> allNumber(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int organId = (Integer) session.getAttribute("organId");
        int role = relationService.selectRoleByRelatio(user.getUserId(), organId);
        if (1 == role || 2 == role){
            //有权查看所有用户
            List<Relation> relationList = relationService.selectAllByOrganId(organId);
            return new RequestResult<List<Relation>>(StatEnum.RELATION_ALL_ORGAN, relationList);
        }
        //不是管理员
        return new RequestResult<List<Relation>>(StatEnum.WITHOUT_POWER);
    }

    /**
     * 负责人设置/撤销管理员
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/permission")
    @ResponseBody
    public RequestResult<String> updateRelation(@RequestBody Map map, HttpServletRequest request){
        Relation relation = new Relation();
            relation.setRelationId((Integer) map.get("relationId"));
            relation.setRole((Integer) map.get("role"));
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int organId = (Integer)session.getAttribute("organId");
        int role = relationService.selectRoleByRelatio(user.getUserId(), organId);
        //不可设置负责人
        if (1 == relation.getRole() || relation.getRole()>=4){
            return new RequestResult<String>(StatEnum.WITHOUT_POWER);
        }
        if (1 == role){
            if (relation.getRole() == 1 || relation.getRole() == 3){
                if (1 == relationService.updateRoleByRelation(relation)){
                    return new RequestResult<String>(StatEnum.RELATION_UPDATE_SUCCESS);
                }
            }
            return new RequestResult<String>(StatEnum.RELATION_UPDATE_FAIL);
        }
        return new RequestResult<String>(StatEnum.WITHOUT_POWER);
    }

    /**
     * 用户根据用户id获得组织列表
     * @param request
     * @return 组织集合
     */
    @RequestMapping("/allorgan")
    @ResponseBody
    public RequestResult<List<Organization>> getOrganByUserId(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        List<Organization> organizationList = relationService.selectOrganByUserId(user.getUserId());
        return new RequestResult<List<Organization>>(StatEnum.RELATION_ALL_ORGAN,
                organizationList);
    }


}
