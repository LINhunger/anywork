package com.test.web.start;

import com.test.message.menuModel.MenuMain;
import com.test.util.HttpPostUploadUtil;
import net.sf.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目启动初始化servlet，调用配置文件
 * Created by zggdczfr on 2016/10/23.
 */
public class InterfaceUrlInitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void init(ServletConfig config) throws ServletException{
        //调用类的初始化方法
        InterfaceUrlInti.init();
        try {
            MenuMain.main(null);
            //将图片上传
//            HttpPostUploadUtil upload = new HttpPostUploadUtil();
//            String filepath = "D:\\inform.jpg";
//            Map<String, String> fileMap = new HashMap<String, String>();
//            fileMap.put("userfile", filepath);
//            Map<String, String> textMap = new HashMap<String, String>();
//            textMap.put("name", "test");
//            String mediaidrs = upload.formUpload(textMap, fileMap);
//            System.out.println("mediaidrs = "+mediaidrs);
//            String mediaid = JSONObject.fromObject(mediaidrs).getString("media_id");
//            System.out.println("media_id = "+mediaid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
