package com.test.web;

import com.test.aop.Authority;
import com.test.aop.AuthorityType;
import com.test.dto.RequestResult;
import com.test.enums.StatEnum;
import com.test.model.Comment;
import com.test.model.Recomment;
import com.test.model.User;
import com.test.service.CommentService;
import com.test.util.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 有关评论和回复的控制器
 * Created by zggdczfr on 2016/11/8.
 */
@Controller
@RequestMapping("comment")
public class CommentController {

    private static final Logger LOGGER = Logger.getLogger(CommentController.class);

    @Resource
    private CommentService commentService;


    /**
     * 用户发表评论
     * @param comment 评论对象
     * @return
     */
    @Authority(AuthorityType.Validate)
    @RequestMapping("announce")
    @ResponseBody
    public RequestResult<String> commentAdd(Comment comment, HttpServletRequest request){
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
     * @param recomment
     * @param request
     * @return
     */
    @Authority(AuthorityType.Validate)
    @RequestMapping("response")
    @ResponseBody
    public RequestResult<String> recommentAdd(Recomment recomment, HttpServletRequest request){
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
    @Authority(AuthorityType.Validate)
    @RequestMapping("/allcomments/{type}/{targetId}")
    @ResponseBody
    public RequestResult<List<Comment>> getComment(@PathVariable int type,@PathVariable int targetId){
        List<Comment> commentList = commentService.getAllComment(type, targetId);

        return new RequestResult<List<Comment>>(StatEnum.COMMENT_ALL, commentList);
    }
}
