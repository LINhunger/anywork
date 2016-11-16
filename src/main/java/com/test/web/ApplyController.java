package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Apply;
import com.test.model.Relation;
import com.test.model.User;
import com.test.service.ApplyService;
import com.test.service.RelationService;
import com.test.util.Logger;
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
 * 申请处理控制器
 * Created by zggdczfr on 2016/11/8.
 */
@Controller
@Authority(AuthorityType.NoValidate)
@RequestMapping("/apply")
public class ApplyController {

    private static final Logger LOGGER = Logger.getLogger(ApplyController.class);

    @Resource
    private ApplyService applyService;

    @Resource
    private RelationService relationService;

    /**
     * 发送加入组织的申请
     * @param apply 申请对象
     * @param request 请求
     * @return
     */

    @RequestMapping("/send")
    @ResponseBody
    public RequestResult<String> sendApply(Apply apply, HttpServletRequest request){
        HttpSession session = request.getSession();
        apply.setStatus(0);
        apply.setSender((User)session.getAttribute("user"));

        if (1 != applyService.insertApply(apply)){
            //发送申请不成功
            return new RequestResult<String>(StatEnum.APPLY_SEND_FAIL);
        }
        //发送申请成功
        return new RequestResult<String>(StatEnum.APPLY_SEND_SUCCESS);
    }

    /**
     * 处理用户的组织申请
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("/handle")
    @ResponseBody
    public RequestResult<String> updateApply(@RequestBody Map map,
                                             HttpServletRequest request){
        Apply apply = new Apply();
        apply.setStatus((Integer) map.get("status"));
        apply.setApplyId((Integer) map.get("organId"));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int organId = (Integer)session.getAttribute("organId");
        int role = relationService.selectRoleByRelatio(user.getUserId(), organId);
        if (role == 1 || role ==2){
            //有权处理
            if (1 != applyService.updateApply(apply)){
                //处理失败
                return new RequestResult<String>(StatEnum.APPLY_HANDLE_FAIL);
            }
            //处理成功
            //若为同意加入组织，则将关联信息加入数据库
            if (1 == apply.getStatus()){
                Relation relation = new Relation();
                //设置用户权限
                relation.setRole(2);
                //设置关系中申请者对象
                relation.setUser(apply.getSender());
                //设置组织id
                relation.setOrganId(apply.getOrganId());
                relationService.insertRelation(relation);
            }
            return new RequestResult<String>(StatEnum.APPLY_HANDLE_SUCCESS);
        }
        //没有相应的权限
        return new RequestResult<String>(StatEnum.WITHOUT_POWER);
    }

    /**
     * 管理者获得未处理的申请
     * @param request
     * @return
     */
    @RequestMapping("/allapply")
    @ResponseBody
    public RequestResult<List<Apply>> getNoHandleApply(HttpServletRequest request){

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int organId = (Integer) session.getAttribute("organId");
        int role = relationService.selectRoleByRelatio(user.getUserId(), organId);
        if (1 == role || 2 == role){
            //有权处理
            List<Apply> applyList = applyService.selectAllByOrganId(organId);
            return new RequestResult<List<Apply>>(StatEnum.APPLY_NOHANDLR_ALL, applyList);
        }
        //没有相应的权限
        return new RequestResult<List<Apply>>(StatEnum.WITHOUT_POWER);
    }
}
