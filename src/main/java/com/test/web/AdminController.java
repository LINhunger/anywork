package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.model.Apply;
import com.test.model.Homework;
import com.test.model.Relation;
import com.test.model.User;
import com.test.service.AdminService;
import com.test.service.ApplyService;
import com.test.service.RelationService;
import com.test.util.TravelFileUtil;
import com.test.util.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunger on 2016/11/15.
 */
@Controller
@Authority(AuthorityType.Admin)
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplyService applyService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private AdminService adminService;
    /**
     * 查看申请列表
     * @param model
     * @return
     */
        @RequestMapping(value = "/{organId}/list",method = RequestMethod.GET)
        public String applyList(@PathVariable("organId") Integer organId, Model model, HttpServletRequest request){
            request.getSession().setAttribute("organId",organId);
            List<Apply> applys = applyService.selectAllByOrganId(organId);
            model.addAttribute("applys",applys);
            logger.info("list  invoke:");
            return  "list";
    }


    /**
     * 处理申请
     * @return
     */
    @RequestMapping(value = "/{organId}/{userId}/{applyId}/{status}/execution",method = RequestMethod.GET)
    public String applyExecution(@PathVariable("organId") Integer organId,
                                                      @PathVariable("userId") Integer userId,
                                                      @PathVariable("applyId") Integer applyId,
                                                      @PathVariable("status") Integer status
                                                        ){
        Apply apply = new Apply();
        apply.setOrganId(organId);
        apply.setApplyId(applyId);
        apply.setSender(adminService.selectUserById(userId));
        apply.setStatus(status);
        //处理申请信息
        applyService.updateApply(apply);
        if(status == 1) {
            //更新关联信息
            Relation relation = new Relation();
            relation.setRole(3);
            relation.setUser(apply.getSender());
            relation.setOrganId(apply.getOrganId());
            relationService.insertRelation(relation);
            logger.info("execution  invoke:");
        }
        return  "redirect:/admin/"+organId+"/list";
    }

    /**
     * 查看群成员列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/{organId}/menbers",method = RequestMethod.GET)
    public String menberList(@PathVariable("organId") Integer organId,Model model){
        List<Relation> relationLIst = relationService.selectAllByOrganId(organId);
        List<Relation> relations = new ArrayList<Relation>();

        //这是一个脑残式排序
        for (Relation r:relationLIst) {
            if (r.getRole() == 1){
                relations.add(r);
            }
        }
        for (Relation r:relationLIst) {
            if (r.getRole() == 2){
                relations.add(r);
            }
        }
        for (Relation r:relationLIst) {
            if (r.getRole() == 3){
                relations.add(r);
            }
        }
        model.addAttribute("relations",relations);
        logger.info("list  invoke:");
        return  "menberList";
    }


    /**
     * 设置/取消管理员
     * @return
     */
    @RequestMapping(value = "/{organId}/{userId}/{relationId}/{role}/setting",method = RequestMethod.GET)
    public String adminSetting(@PathVariable("organId") Integer organId,
                                 @PathVariable("userId") Integer userId,
                                 @PathVariable("relationId") Integer relationId,
                                 @PathVariable("role") Integer role
    ){
        User user = adminService.selectUserById(userId);
        //更新关联信息
        Relation relation = new Relation();
        relation.setUser(user);
        relation.setOrganId(organId);
        relation.setRelationId(relationId);
        if(role == 3) {
            relation.setRole(2);
        }else if(role == 2){
            relation.setRole(3);
        }else{
            relation.setRole(1);
        }
        relationService.updateRoleByRelation(relation);
        logger.info("setting  invoke:");
        return  "redirect:/admin/"+organId+"/menbers";
    }


    /**
     * 查看作业列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/{organId}/homework",method = RequestMethod.GET)
    public String fileList(@PathVariable("organId") Integer organId,
                                        HttpServletRequest request,Model model){
        String path = request.getServletContext().getRealPath("/submit" + "/" + organId);
        List<Homework> homeworks = adminService.selectHomeworkListByOrganId(organId);
        model.addAttribute("homeworks",homeworks);
        logger.info("files  invoke:");
        return  "homeworklist";
    }



    @RequestMapping(value = "/{organId}/{homeworkId}/download",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> download(HttpServletRequest request,HttpServletResponse response,
                                                            @PathVariable("organId")Integer organId,
                                                            @PathVariable("homeworkId")Integer homeworkId) throws IOException {
        System.out.println("需要批量下载的作业文件id：homeworkId:"+homeworkId);
        String path = request.getServletContext().getRealPath("/submit" + "/" + organId + "/" + homeworkId);
        ZipUtils.createZip(path,path+".zip");
        try {
            // 流拷贝
            OutputStream out = response.getOutputStream();
            InputStream in = new BufferedInputStream(new FileInputStream(
                    path+".zip"));
            int temp;
            while ((temp = in.read()) != -1) {
                out.write(temp);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
