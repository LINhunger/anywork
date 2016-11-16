package com.test.service.impl;

import com.test.dao.CommentDao;
import com.test.model.Comment;
import com.test.model.Recomment;
import com.test.service.CommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by zggdczfr on 2016/11/8.
 */
@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentDao commentDao;

    public int insertComment(@Param("comment") Comment comment) {
        return commentDao.insertComment(comment);
    }

    public int insertRecomment(@Param("recomment") Recomment recomment) {
        return commentDao.insertRecomment(recomment);
    }

//    public List<Comment> selectCommentByAll(@Param("type") int type, @Param("targetId") int targetId) {
//        return commentDao.selectCommentByAll(type, targetId);
//    }
//
//    public List<Recomment> selectRecommentBy(@Param("type") int type, @Param("commentId") int commentId) {
//        return commentDao.selectRecommentBy(type, commentId);
//    }

    public List<Comment> getAllComment(@Param("type") int type, @Param("targetId") int targetId) {
        List<Comment> commentList = commentDao.selectCommentByAll(type, targetId);
        List<Recomment> recommentList = commentDao.selectRecommentBy(type+2, targetId);

        //将回复集合合并到评论集合
        for (int i=0; i<commentList.size(); i++){
            //获得评论id
            int aCommentId = commentList.get(i).getCommentId();
            List<Recomment> realReCom = new ArrayList<Recomment>();
            //遍历回复集合
            for (Recomment recomment : recommentList){
                //判断回复与评论的关系
                if (aCommentId == recomment.getaCommentId()){
                    realReCom.add(recomment);
                }
            }
            commentList.get(i).setRecomments(realReCom);
        }

        return null;
    }
}
