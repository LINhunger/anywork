package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dao.UserDao;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Comment;
import com.test.model.Recomment;
import com.test.model.User;
import com.test.service.CommentService;
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
 * 有关评论和回复的控制器
 * Created by zggdczfr on 2016/11/8.
 */
@Controller
@Authority(AuthorityType.NoValidate)
@RequestMapping("comment")
public class CommentController {

    private static final Logger LOGGER = Logger.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;

    @Resource
    private UserDao userDao;

    /**
     * 用户发表评论
     * @param map
     * @return
     */
    @RequestMapping("announce")
    @ResponseBody
    public RequestResult<String> commentAdd(@RequestBody Map map, HttpServletRequest request){
        Comment comment = new Comment();
            comment.setContent((String)map.get("content"));
            comment.setTargetId((Integer)map.get("targetId"));
            comment.setType((Integer)map.get("type"));
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        comment.setSender(user);
        if (1 != commentService.insertComment(comment)){
            //插不入成功
            return new RequestResult<String>(StatEnum.COMMENT_ANNOUNCE_FAIL);
        }
        return new RequestResult<String>(StatEnum.COMMENT_ANNOUNCE_SUCCESS);
    }

    /**
     * 用户回复评论
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("response")
    @ResponseBody
    public RequestResult<String> recommentAdd(@RequestBody Map map, HttpServletRequest request){
        Recomment recomment = new Recomment();
            recomment.setCommentId((Integer) map.get("commentId"));
            recomment.setType((Integer)map.get("type"));
            recomment.setContent((String)map.get("content"));
            recomment.setTargetId((Integer)map.get("targetId"));
            recomment.setReceiver(userDao.selectOneById((Integer)map.get("receiverId")));
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user"); //发送者
        recomment.setSender(user);
        if (1 != commentService.insertRecomment(recomment)) {
            //插不入成功
            return new RequestResult<String>(StatEnum.COMMENT_ANNOUNCE_FAIL);
        }
        return new RequestResult<String>(StatEnum.RECOMMENT_ANNOUNCE_SUCCESS);
    }

    /**
     * 用户获得作业/请求的评论/回复
     * 请求评论0，作业评论1，请求回复2，作业回复3
     * @param type 类型(1或2)
     * @param targetId 作业/请求id
     * @return
     */
    @RequestMapping("/allcomments/{type}/{targetId}")
    @ResponseBody
    public RequestResult<List<Comment>> getComment(@PathVariable int type,@PathVariable int targetId){
        List<Comment> commentList = commentService.getAllComment(type, targetId);

        return new RequestResult<List<Comment>>(StatEnum.COMMENT_ALL, commentList);
    }
}
