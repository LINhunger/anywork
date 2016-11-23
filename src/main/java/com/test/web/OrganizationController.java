package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Inform;
import com.test.model.Organization;
import com.test.model.Relation;
import com.test.model.User;
import com.test.service.OrganService;
import com.test.service.RelationService;
import com.test.service.TimelineService;
import com.test.util.ImageUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 组织模块控制层
 * Created by zggdczfr on 2016/11/10.
 */
@Controller
@Authority(AuthorityType.NoValidate)
@RequestMapping("organ")
public class OrganizationController {

    @Resource
    private OrganService organService;

    @Resource
    private RelationService relationService;

    @Resource
    private TimelineService timelineService;

    /**
     * 创建组织，同时设置组织负责人
     * @param file 文件
     * @param x
     * @param y
     * @param width
     * @param height
     * @param organName
     * @param description
     * @param request
     * @return
     */
    @RequestMapping("/create")
    @ResponseBody
    public RequestResult<?> createOrgan(@RequestParam(value = "file", required = false) MultipartFile file,
                                              @RequestParam(value = "x", required = false) String x,
                                              @RequestParam(value = "y", required = false) String y,
                                              @RequestParam(value = "width", required = false) String width,
                                              @RequestParam(value = "height", required = false) String height,
                                              @RequestParam(value = "organName", required = false) String organName,
                                              @RequestParam(value = "description", required = false) String description,
                                              HttpServletRequest request){
        //储存组织信息
        Organization organ = new Organization();
            organ.setDescription(description);
            organ.setOrganName(organName);
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        //创建组织同时将关系插入关系表
        Relation relation = new Relation();
        organService.insertOrgan(organ);
        int organId = organ.getOrganId();
        // TODO: 2016/11/20
        Inform inform = new Inform();
        inform.setAuthor(user);
        inform.setMark("欢迎加入："+organ.getOrganName());
        inform.setInformTitle("系统通知");
        inform.setOrganId(organId);
        timelineService.releaseInform(inform);
        if(0 == organId){
            //创建失败
            return new RequestResult<Object>(StatEnum.ORGAN_CREATE_FAIL);
        }
        //将信息存入组织关系
        relation.setOrganId(organId);
        relation.setRole(1);
        relation.setUser(user);
        relationService.insertRelation(relation);
        //更改图片名，保证唯一
        String photoName = organId + ".jpg";
        String photoPath = request.getServletContext().getRealPath("/photo/temp")+"/"+photoName;
        String newPath =  request.getServletContext().getRealPath("/photo")+"/"+photoName;
        try {
            if (file!=null&&!file.isEmpty()) {
                //获取四个焦点坐标
                Integer t_x = Integer.parseInt(x);
                Integer t_y = Integer.parseInt(y);
                Integer t_width = Integer.parseInt(width);
                Integer t_height = Integer.parseInt(height);
                FileUtils.copyInputStreamToFile(file.getInputStream(),
                        new File(request.getServletContext().getRealPath("/photo/temp")
                                ,  photoName));
                ImageUtil.cutImage(photoPath,newPath,t_x,t_y,t_width,t_height);
                return new RequestResult<Object>(StatEnum.ORGAN_CREATE_SUCCESS);
            }else {
                //没有上传图片使用默认图片
                FileUtils.copyInputStreamToFile(new FileInputStream(
                                request.getServletContext().getRealPath("/photo")+"/default.jpg"
                        ),
                        new File(request.getServletContext().getRealPath("/photo")
                                ,  photoName));
                return new RequestResult<Object>(StatEnum.ORGAN_CREATE_SUCCESS);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return  new RequestResult<User>(StatEnum.DEFAULT_WRONG);
        }
    }



    /**
     * 根据组织id查找组织
     * @param request
     * @return
     */
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
    @RequestMapping("/searchName")
    @ResponseBody
    public RequestResult<List<Organization>> searchOrganByName(HttpServletRequest request){
        String organName = request.getParameter("organName");
        List<Organization> organList = organService.selectOneByName(organName);
        return new RequestResult<List<Organization>>(StatEnum.ORGAN_SEARCH_NAME, organList);
    }


}
