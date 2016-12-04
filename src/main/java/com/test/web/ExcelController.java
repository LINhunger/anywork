package com.test.web;

import com.test.dto.RequestResult;
import com.test.enums.Level;
import com.test.enums.StatEnum;
import com.test.model.Inform;
import com.test.model.Textpaper;
import com.test.model.User;
import com.test.service.TestService;
import com.test.service.TimelineService;
import com.test.util.ExcelUtil;
import com.test.util.Logger;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;

/**
 * Created by zggdczfr on 2016/11/29.
 */
@Controller
@RequestMapping("/excel")
public class ExcelController {

    private final static Logger LOGGER = Logger.getLogger(ExcelController.class);

    @Autowired
    private TestService testService;
    @Autowired
    private TimelineService timelineService;

    /**
     * 用户上传excel模板
     * @param file excel文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/send")
    @ResponseBody
    public RequestResult<?> sendExcel(
            @RequestParam("file")MultipartFile file,
            @RequestParam("paperInfo")String text,
            HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        JSONObject jsonobject = JSONObject.fromObject(text);
        Textpaper textpaper = (Textpaper) JSONObject.toBean(jsonobject, Textpaper.class);
        textpaper.setUserName(user.getUserName());
        textpaper.setAuthorId(user.getUserId());
        try{
            if(file != null){
                System.out.println(file.getContentType());
                System.out.println(file.getName());
                System.out.println(file.getOriginalFilename());
                if (!file.getOriginalFilename().endsWith(".xlsx")){
                    //不是excel文件
                    return new RequestResult<Object>(StatEnum.SUBMIT_WRONG_FILE);
                }

                //将附带信息存入session
                request.getSession().setAttribute("excel", textpaper);

                if(!file.isEmpty()){
                    //保存文件
                    LOGGER.log(Level.DEBUG, "用户id为 {0} 上传excel模板！", user.getUserId());
                    FileUtils.copyInputStreamToFile(file.getInputStream(),
                            new File(request.getServletContext().getRealPath("/excel")
                                    ,  user.getUserId()+".xlsx"));
                }
                return new RequestResult<Object>(StatEnum.TEXTPAPER_UPLOAD_SUCCESS);
            }
            //返回保存成功的标志
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        } catch (Exception e) {
            LOGGER.log(Level.WARN, "default exception. \t {0}", e);
            return new RequestResult<Object>(StatEnum.DEFAULT_WRONG);
        }
    }

    /**
     * 用户预览excel文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/preview")
    @ResponseBody
    public RequestResult<Textpaper> previewExcel(HttpServletRequest request){
        try {
            Integer userId = ((User) request.getSession().getAttribute("user")).getUserId();
            Textpaper textpaper = (Textpaper) request.getSession().getAttribute("excel");
            String path = request.getServletContext().getRealPath("/excel"+"/"+userId+".xlsx");
            File file = new File(path);
            if (null == textpaper || !file.exists()){
                //没有相关的记录或文件不存在
                //即用户没有上传excel文件
                return null;
            }
            try {
                textpaper.setTopics(ExcelUtil.getTextpaper(new FileInputStream(path)));
            } catch (Exception e){
                //解析文件失败
                return null;
            }

            /*
            返回标志
             */

            return new RequestResult<Textpaper>(0, "", textpaper);

        } catch (Exception e){
            LOGGER.log(Level.WARN, "default exception. \t {0}", e);
            return null;
        }
    }


    /**
     * 用户发布excel文件
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit")
    @ResponseBody
    public RequestResult<?> submitExcel(HttpServletRequest request){
        try {
            User user = (User) request.getSession().getAttribute("user");
            Integer organId = (Integer) request.getSession().getAttribute("organId");
            Textpaper textpaper = (Textpaper) request.getSession().getAttribute("excel");
            String path = request.getServletContext().getRealPath("/excel"+"/"+user.getUserId()+".xlsx");
            File file = new File(path);
            if (null == textpaper || !file.exists()){
                //没有相关的记录或文件不存在
                //即用户没有上传excel文件
                return new RequestResult<Object>(StatEnum. DEFAULT_WRONG);
            }
            try {
                textpaper.setTopics(ExcelUtil.getTextpaper(new FileInputStream(path)));
            } catch (Exception e){
               e.printStackTrace();
                return new RequestResult<Object>(StatEnum. DEFAULT_WRONG);
            }

            RequestResult<?> result = testService.publishTextpaper(textpaper);
            inform(textpaper, user, organId);
            return new RequestResult<Object>(StatEnum. TEXTPAPER_SUBMIT_SUCCESS);
        } catch (Exception e){
            LOGGER.log(Level.WARN, "default exception. \t {0}", e);
            return new RequestResult<Object>(StatEnum. DEFAULT_WRONG);
        }
    }

    /**
     * 系统通知方法
     * @param textpaper
     * @param user
     * @param organId
     */
    private void inform(@RequestBody Textpaper textpaper, User user, Integer organId) {

        Inform inform = new Inform();
        inform.setOrganId(organId);
        inform.setInformTitle("系统通知");
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分 E ");
        inform.setMark(textpaper.getName()+":"+textpaper.getTextpaperTitle()+"<br/>" +
                "开始时间："+dateformat.format(textpaper.getCreateTime())+"<br/>" +
                "结束时间："+dateformat.format(textpaper.getEndingTime()));
        inform.setAuthor(user);
        timelineService.releaseInform(inform);
    }
}
